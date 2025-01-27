package br.com.fiap.techchallenge.order.infra.entrypoint.controller.openapi;

import br.com.fiap.techchallenge.order.domain.models.enums.ProductCategoryEnum;
import br.com.fiap.techchallenge.order.infra.entrypoint.controller.dto.ProductPageResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Product")
public interface ProductControllerOpenApi {

	@Operation(summary = "Find a Product By Category")
	@ApiResponse(responseCode = "200", description = "Ok Response",
			content = @Content(mediaType = "application/json", schema = @Schema(ref = "ProductPageResponseDTO")))
	@ApiResponse(responseCode = "404", description = "Not Found Response",
			content = @Content(mediaType = "application/json", schema = @Schema(ref = "ProblemDto")))
	@ApiResponse(responseCode = "500", description = "Internal Server Error Response",
			content = @Content(mediaType = "application/json", schema = @Schema(ref = "ProblemDto")))
	ResponseEntity<ProductPageResponseDTO> findProductsByCategory(ProductCategoryEnum category, int page, int size);

}
