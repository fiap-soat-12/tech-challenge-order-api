package br.com.fiap.techchallenge.order.infra.entrypoint.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record CustomerRequestDTO(@Schema(example = "Fiaperson") @NotBlank String name,
		@Schema(example = "42256425002") @NotBlank @CPF String document,
		@Schema(example = "fiaperson@soat.com") @NotBlank @Email String email) {
}
