package br.com.fiap.techchallenge.order.infra.entrypoint.consumer.dto;

import br.com.fiap.techchallenge.order.domain.models.enums.ProductCategoryEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductCreateDTO(@NotBlank String name,
                               @NotNull ProductCategoryEnum category,
                               @NotNull @Positive BigDecimal price,
                               @NotBlank String description) {
}