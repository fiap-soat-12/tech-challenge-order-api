package br.com.fiap.techchallenge.order.infra.entrypoint.consumer.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record ProductDeleteDTO(@NotNull UUID id) {
}