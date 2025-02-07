package br.com.fiap.techchallenge.order.infra.gateway.producer.payment.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentDTO(UUID orderId, BigDecimal totalAmount) {
}
