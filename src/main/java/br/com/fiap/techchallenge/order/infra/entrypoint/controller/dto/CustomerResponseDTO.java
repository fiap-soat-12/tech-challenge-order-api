package br.com.fiap.techchallenge.order.infra.entrypoint.controller.dto;

import br.com.fiap.techchallenge.order.domain.models.Customer;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record CustomerResponseDTO(@Schema(example = "ab69e046-fb5a-4a79-98d6-363efdf20e11") UUID id,
		@Schema(example = "Fiaperson") String name, @Schema(example = "42256425002") String document,
		@Schema(example = "fiaperson@soat.com") String email) {
	public CustomerResponseDTO(Customer customer) {
		this(customer.getId(), customer.getName(), customer.getDocument(), customer.getEmail());
	}
}
