package br.com.fiap.techchallenge.order.infra.entrypoint.controller.dto;

import br.com.fiap.techchallenge.order.domain.models.Order;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record CreateOrderResponseDTO(@Schema(example = "ab69e046-fb5a-4a79-98d6-363efdf20e11") UUID orderId,
		@Schema(example = "1") Integer sequence) {
	public CreateOrderResponseDTO(Order order) {
		this(order.getId(), order.getSequence());
	}
}
