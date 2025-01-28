package br.com.fiap.techchallenge.order.application.usecase.order.impl;

import br.com.fiap.techchallenge.order.application.exceptions.DoesNotExistException;
import br.com.fiap.techchallenge.order.application.exceptions.GenerateQrCodeException;
import br.com.fiap.techchallenge.order.application.persistence.CustomerPersistence;
import br.com.fiap.techchallenge.order.application.persistence.OrderPersistence;
import br.com.fiap.techchallenge.order.application.persistence.ProductPersistence;
import br.com.fiap.techchallenge.order.application.usecase.order.CreateOrderUseCase;
import br.com.fiap.techchallenge.order.application.usecase.order.dto.CreateOrderDTO;
import br.com.fiap.techchallenge.order.domain.models.Customer;
import br.com.fiap.techchallenge.order.domain.models.Order;
import br.com.fiap.techchallenge.order.domain.models.OrderProduct;
import br.com.fiap.techchallenge.order.domain.models.Product;
import br.com.fiap.techchallenge.order.infra.entrypoint.consumer.PaymentAcceptConsumer;
import br.com.fiap.techchallenge.order.infra.entrypoint.consumer.dto.PaymentAcceptDTO;
import br.com.fiap.techchallenge.order.infra.gateway.producer.payment.PaymentProducer;
import br.com.fiap.techchallenge.order.infra.gateway.producer.payment.dto.PaymentDTO;
import br.com.fiap.techchallenge.order.infra.gateway.producer.payment.dto.PaymentItemDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

public class CreateOrderUseCaseImpl implements CreateOrderUseCase {

	private final OrderPersistence persistence;

	private final ProductPersistence productPersistence;

	private final CustomerPersistence customerPersistence;

	private final PaymentProducer paymentProducer;

	private final PaymentAcceptConsumer paymentAcceptConsumer;

	public CreateOrderUseCaseImpl(OrderPersistence persistence, ProductPersistence productPersistence,
                                  CustomerPersistence customerPersistence, PaymentProducer paymentProducer, PaymentAcceptConsumer paymentAcceptConsumer) {
		this.persistence = persistence;
		this.productPersistence = productPersistence;
		this.customerPersistence = customerPersistence;
		this.paymentProducer = paymentProducer;
        this.paymentAcceptConsumer = paymentAcceptConsumer;
    }

	@Override
	public Order create(CreateOrderDTO input) {
		Customer customer = null;

		if (input.customerId() != null) {
			customer = this.getCustomer(input.customerId());
		}

		var orderProducts = this.getOrderProducts(input);
		var calculatedAmount = this.reduceAmount(orderProducts);
		var externalPaymentId = UUID.randomUUID();

		var order = persistence.create(new Order(calculatedAmount, orderProducts, customer, externalPaymentId.toString()));

		var pixDto = this.createPayment(input, calculatedAmount, externalPaymentId);
		paymentProducer.sendToPayment(pixDto);

		return this.getQrCode(order);
	}

	private Order getQrCode(Order order) {
		CompletableFuture<PaymentAcceptDTO> futureResponse = new CompletableFuture<>();

		paymentAcceptConsumer.registerResponseHandler(order.getId(), futureResponse);

		try {
			var response = futureResponse.get(60, TimeUnit.SECONDS);
			order.setQrCode(response.qrCode());
			return persistence.update(order);

		} catch (TimeoutException | ExecutionException e) {
			throw new GenerateQrCodeException("Failed to generate QrCode, please try again");
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return order;
		}
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

	private PaymentDTO createPayment(CreateOrderDTO input, BigDecimal totalAmount, UUID externalPaymentId) {
		List<UUID> ids = input.products().stream().map(CreateOrderDTO.OrderProducts::id).toList();
		List<Product> products = this.findProductListOrThrowException(ids);

		return new PaymentDTO(products.stream()
			.map(product -> new PaymentItemDTO(product.getCategory().toString(), product.getName(),
					product.getDescription(), product.getPrice())).toList(), totalAmount, externalPaymentId);
	}

}
