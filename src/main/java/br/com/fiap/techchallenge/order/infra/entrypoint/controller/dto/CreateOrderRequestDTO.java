package br.com.fiap.techchallenge.order.infra.entrypoint.controller.dto;


import br.com.fiap.techchallenge.order.infra.entrypoint.controller.validator.NullOrValidUUID;
import br.com.fiap.techchallenge.order.infra.entrypoint.controller.validator.ValidUUID;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateOrderRequestDTO(
		@Schema(example = "cd5b8e43-5d8f-4e92-9f7d-6b9d7e4f7e9c") @NullOrValidUUID String customerId,
		@NotEmpty @Valid List<OrderProducts> products) {

	public record OrderProducts(

			@Schema(example = "7091cbdc-2d24-4faf-aadd-995a7bcc6b5b") @ValidUUID @NotNull String id,
			@Schema(example = "Without Onions please") String observation) {

	}
}
