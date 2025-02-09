package br.com.fiap.techchallenge.order.infra.entrypoint.controller.dto;

import java.util.UUID;

public record PaidRequestDTO(UUID orderId, Boolean isPaid) {
}
