package br.com.fiap.techchallenge.order.infra.entrypoint.consumer.dto;

import java.util.UUID;

public record OrderEvolveDTO(UUID orderId, Boolean isPaid) {
}
