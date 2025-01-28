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

	private final boolean isPaid;

	private final String paymentId;

	private String qr;

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
		this.qr = details.getQr();
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

	public Order(Order orderFound, boolean isPaid) {
		this.id = orderFound.getId();
		this.amount = orderFound.getAmount();
		this.sequence = orderFound.getSequence();
		this.status = orderFound.getStatus();
		this.isPaid = isPaid;
		this.products = orderFound.getProducts();
		this.customer = orderFound.getCustomer();
		this.paymentId = orderFound.getPaymentId();
		this.qr = orderFound.getQr();
		this.createdAt = orderFound.getCreatedAt();
		this.updatedAt = orderFound.getUpdatedAt();
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

	public boolean isPaid() {
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

	public String getQr() {
		return qr;
	}

	public void setQrCode(String qrCode) {
		this.qr = qrCode;
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
}
