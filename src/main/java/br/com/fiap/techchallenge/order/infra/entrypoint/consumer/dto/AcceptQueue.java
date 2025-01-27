package br.com.fiap.techchallenge.order.infra.entrypoint.consumer.dto;

public record AcceptQueue(String consumer, Boolean isOk) {
}
