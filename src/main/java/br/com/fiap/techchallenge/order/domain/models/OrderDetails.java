package br.com.fiap.techchallenge.order.domain.models;

import br.com.fiap.techchallenge.order.domain.models.enums.OrderStatusEnum;

import java.util.List;

public class OrderDetails {

    private final Integer sequence;
    private final OrderStatusEnum status;
    private final boolean isPaid;
    private final List<OrderProduct> products;
    private final Customer customer;
    private final String paymentId;

    public OrderDetails(Integer sequence, OrderStatusEnum status, boolean isPaid,
                        List<OrderProduct> products, Customer customer, String paymentId) {
        this.sequence = sequence;
        this.status = status;
        this.isPaid = isPaid;
        this.products = products;
        this.customer = customer;
        this.paymentId = paymentId;
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

    public List<OrderProduct> getProducts() {
        return products;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getPaymentId() {
        return paymentId;
    }
}
