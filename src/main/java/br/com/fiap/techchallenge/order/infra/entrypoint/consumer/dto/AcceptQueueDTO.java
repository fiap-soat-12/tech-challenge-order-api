package br.com.fiap.techchallenge.order.infra.entrypoint.consumer.dto;

public record AcceptQueueDTO(String consumer, Boolean isOk) {
}
