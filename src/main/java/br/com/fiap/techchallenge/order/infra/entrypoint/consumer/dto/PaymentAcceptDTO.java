package br.com.fiap.techchallenge.order.infra.entrypoint.consumer.dto;

import java.util.UUID;

public record PaymentAcceptDTO(UUID orderId, String qrCode) {
}
