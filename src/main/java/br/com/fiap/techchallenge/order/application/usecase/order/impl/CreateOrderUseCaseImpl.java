package br.com.fiap.techchallenge.order.application.usecase.order.impl;

import br.com.fiap.techchallenge.order.application.exceptions.DoesNotExistException;
import br.com.fiap.techchallenge.order.application.persistence.CustomerPersistence;
import br.com.fiap.techchallenge.order.application.persistence.OrderPersistence;
import br.com.fiap.techchallenge.order.application.persistence.ProductPersistence;
import br.com.fiap.techchallenge.order.application.usecase.order.CreateOrderUseCase;
import br.com.fiap.techchallenge.order.application.usecase.order.dto.CreateOrderDTO;
import br.com.fiap.techchallenge.order.domain.models.Customer;
import br.com.fiap.techchallenge.order.domain.models.Order;
import br.com.fiap.techchallenge.order.domain.models.OrderProduct;
import br.com.fiap.techchallenge.order.domain.models.Product;
import br.com.fiap.techchallenge.order.infra.gateway.producer.payment.PaymentProducer;
import br.com.fiap.techchallenge.order.infra.gateway.producer.payment.dto.PaymentDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class CreateOrderUseCaseImpl implements CreateOrderUseCase {

	private final OrderPersistence persistence;

	private final ProductPersistence productPersistence;

	private final CustomerPersistence customerPersistence;

	private final PaymentProducer paymentProducer;


	public CreateOrderUseCaseImpl(OrderPersistence persistence, ProductPersistence productPersistence,
                                  CustomerPersistence customerPersistence, PaymentProducer paymentProducer) {
		this.persistence = persistence;
		this.productPersistence = productPersistence;
		this.customerPersistence = customerPersistence;
		this.paymentProducer = paymentProducer;
    }

	@Override
	public Order create(CreateOrderDTO input) throws JsonProcessingException {
		Customer customer = null;

		if (input.customerId() != null) {
			customer = this.getCustomer(input.customerId());
		}

		var orderProducts = this.getOrderProducts(input);
		var calculatedAmount = this.reduceAmount(orderProducts);
		var externalPaymentId = UUID.randomUUID();

		var order = persistence.create(new Order(calculatedAmount, orderProducts, customer, externalPaymentId.toString()));

		var paymentDto = this.createPayment(order, calculatedAmount);
		paymentProducer.sendToPayment(paymentDto);

		return order;
	}

	private Customer getCustomer(UUID customerId) {
		return customerPersistence.findById(customerId)
			.orElseThrow(() -> new DoesNotExistException("Customer not found with ID: " + customerId));
	}

	private BigDecimal reduceAmount(List<OrderProduct> products) {
		return products.stream().map(OrderProduct::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private List<OrderProduct> getOrderProducts(CreateOrderDTO order) {
		var productIdList = this.getListOfProductIds(order);
		var productsMap = this.getProductMap(findProductListOrThrowException(productIdList));

		return order.products().stream().map(orderProduct -> {
			BigDecimal price = productsMap.get(orderProduct.id()).getPrice();
			return OrderProduct.create(price, orderProduct.observation(), orderProduct.id());
		}).toList();
	}

	private List<Product> findProductListOrThrowException(List<UUID> productIdList) {
		return productIdList.stream()
			.map(uuid -> productPersistence.findById(uuid)
				.orElseThrow(() -> new DoesNotExistException("Product not found with ID: " + uuid)))
			.toList();
	}

	private Map<UUID, Product> getProductMap(List<Product> products) {
		return products.stream()
			.collect(Collectors.toMap(Product::getId, product -> product, (existing, replacement) -> existing));
	}

	private List<UUID> getListOfProductIds(CreateOrderDTO input) {
		return input.products().stream().map(CreateOrderDTO.OrderProducts::id).toList();
	}

	private PaymentDTO createPayment(Order order, BigDecimal totalAmount) {
		return new PaymentDTO(order.getId(), totalAmount);
	}
}
