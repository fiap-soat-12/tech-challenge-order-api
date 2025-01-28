package br.com.fiap.techchallenge.order.infra.entrypoint.consumer.dto;

public record PaymentResponseDTO(Long id,
                                 String paymentDataId,
                                 String externalReference,
                                 String status) {
}
