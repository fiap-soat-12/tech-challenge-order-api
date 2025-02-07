package br.com.fiap.techchallenge.order.domain.models;

import br.com.fiap.techchallenge.order.domain.models.enums.OrderStatusEnum;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order {

	private UUID id;

	private final BigDecimal amount;

	private Integer sequence;

	private OrderStatusEnum status;

	private Boolean isPaid;

	private final String paymentId;

	private List<OrderProduct> products;

	private final Customer customer;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	public Order(UUID id, BigDecimal amount, OrderDetails details, OrderTimestamps timestamps){
		this.id = id;
		this.amount = amount;
		this.sequence = details.getSequence();
		this.status = details.getStatus();
		this.isPaid = details.isPaid();
		this.products = details.getProducts();
		this.customer = details.getCustomer();
		this.paymentId = details.getPaymentId();
		this.createdAt = timestamps.getCreatedAt();
		this.updatedAt = timestamps.getUpdatedAt();
	}

	public Order(BigDecimal amount, List<OrderProduct> products, Customer customer, String paymentId){
		this.amount = amount;
		this.status = OrderStatusEnum.RECEIVED;
		this.isPaid = false;
		this.products = products;
		this.customer = customer;
		this.paymentId = paymentId;
	}

	public UUID getId() {
		return id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public Integer getSequence() {
		return sequence;
	}

	public OrderStatusEnum getStatus() {
		return status;
	}

	public void setPaid(Boolean isPaid) {
		this.isPaid = isPaid;
	}

	public Boolean getIsPaid(){
		return isPaid;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public List<OrderProduct> getProducts() {
		return products;
	}

	public Customer getCustomer() {
		return customer;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void removeProducts() {
		this.products = new ArrayList<>();
	}

	public void setStatus(OrderStatusEnum status) {
		this.status = status;
	}

	public void setStatusReady() {
		this.status = OrderStatusEnum.READY;
	}

	public void setStatusFinished() {
		this.status = OrderStatusEnum.FINISHED;
	}

	public void prepareOrder(Boolean isPaid) {
		this.status = OrderStatusEnum.PREPARING;
		this.isPaid = isPaid;
	}

	public void cancelOrder(Boolean isPaid) {
		this.status = OrderStatusEnum.FINISHED;
		this.isPaid = isPaid;
	}
}
