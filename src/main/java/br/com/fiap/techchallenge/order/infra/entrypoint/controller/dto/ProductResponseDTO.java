package br.com.fiap.techchallenge.order.infra.entrypoint.controller.dto;

import br.com.fiap.techchallenge.order.domain.models.enums.ProductCategoryEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponseDTO(@Schema(example = "ab69e046-fb5a-4a79-98d6-363efdf20e11") UUID id,
								 @Schema(example = "Cheese Hamburger") String name,
								 @Schema(example = "MAIN_COURSE") ProductCategoryEnum category, @Schema(example = "25.90") BigDecimal price,
								 @Schema(example = "Hamburger with Cheese") String description) {
	public ProductResponseDTO(br.com.fiap.techchallenge.order.domain.models.Product product) {
		this(product.getId(), product.getName(), product.getCategory(), product.getPrice(), product.getDescription());
	}
}