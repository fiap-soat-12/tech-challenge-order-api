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

	public Order(UUID id, BigDecimal amount, Integer sequence, OrderStatusEnum status, boolean isPaid,
			List<OrderProduct> products, Customer customer, String paymentId, String qr, LocalDateTime createdAt,
			LocalDateTime updatedAt) {
		this.id = id;
		this.amount = amount;
		this.sequence = sequence;
		this.status = status;
		this.isPaid = isPaid;
		this.products = products;
		this.customer = customer;
		this.paymentId = paymentId;
		this.qr = qr;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
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
		 this(orderFound.getId(), orderFound.getAmount(), orderFound.getSequence(),
				orderFound.getStatus(), isPaid, orderFound.getProducts(), orderFound.getCustomer(),
				orderFound.getPaymentId(), orderFound.getQr(), orderFound.getCreatedAt(), orderFound.getUpdatedAt());
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
