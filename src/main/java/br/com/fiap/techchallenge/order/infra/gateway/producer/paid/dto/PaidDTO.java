package br.com.fiap.techchallenge.order.infra.gateway.producer.paid.dto;

import java.util.UUID;

public record PaidDTO(UUID orderId, Boolean isPaid) {
}
