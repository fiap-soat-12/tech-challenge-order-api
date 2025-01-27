package br.com.fiap.techchallenge.order.infra.entrypoint.consumer.dto;

import br.com.fiap.techchallenge.order.domain.models.enums.ProductCategoryEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductUpdateDTO(@NotNull UUID id,
                               String name,
                               ProductCategoryEnum category,
                               @Positive BigDecimal price,
                               String description) {
}