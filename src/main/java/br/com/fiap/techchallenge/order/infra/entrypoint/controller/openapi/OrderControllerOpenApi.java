package br.com.fiap.techchallenge.order.infra.entrypoint.controller.openapi;

import br.com.fiap.techchallenge.order.infra.entrypoint.controller.dto.CreateOrderRequestDTO;
import br.com.fiap.techchallenge.order.infra.entrypoint.controller.dto.CreateOrderResponseDTO;
import br.com.fiap.techchallenge.order.infra.entrypoint.controller.dto.OrderStatusResponseDTO;
import br.com.fiap.techchallenge.order.infra.entrypoint.controller.dto.PaidRequestDTO;
import br.com.fiap.techchallenge.order.infra.entrypoint.controller.handler.ErrorsValidateData;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Hidden;

import java.util.UUID;

@Tag(name = "Order")
public interface OrderControllerOpenApi {


	@Operation(summary = "Register a Order")
	@ApiResponse(responseCode = "201", description = "Created Response",
			content = @Content(mediaType = "application/json", schema = @Schema(ref = "CreateOrderResponseDTO")))
	@ApiResponse(responseCode = "400", description = "Bad Request Response",
			content = @Content(mediaType = "application/json",
					array = @ArraySchema(schema = @Schema(implementation = ErrorsValidateData.class))))
	@ApiResponse(responseCode = "500", description = "Internal Server Error Response",
			content = @Content(mediaType = "application/json", schema = @Schema(ref = "ProblemDto")))
	ResponseEntity<CreateOrderResponseDTO> create(CreateOrderRequestDTO orderRequest) throws JsonProcessingException;

	@Operation(summary = "Get the status of an order by its ID")
	@ApiResponse(responseCode = "200", description = "OK Response",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)))
	@ApiResponse(responseCode = "404", description = "Not Found Response",
			content = @Content(mediaType = "application/json", schema = @Schema(ref = "ProblemDto")))
	@ApiResponse(responseCode = "500", description = "Internal Server Error Response",
			content = @Content(mediaType = "application/json", schema = @Schema(ref = "ProblemDto")))
	ResponseEntity<Boolean> isOrderPaid(UUID id);

	@Operation(summary = "Get the status of an order by its ID")
	@ApiResponse(responseCode = "200", description = "OK Response",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderStatusResponseDTO.class)))
	@ApiResponse(responseCode = "404", description = "Not Found Response",
			content = @Content(mediaType = "application/json", schema = @Schema(ref = "ProblemDto")))
	@ApiResponse(responseCode = "500", description = "Internal Server Error Response",
			content = @Content(mediaType = "application/json", schema = @Schema(ref = "ProblemDto")))
	ResponseEntity<OrderStatusResponseDTO> findOrderStatus(final UUID id);

	@Hidden
	ResponseEntity<Void> paidOrder(final UUID id, PaidRequestDTO dto) throws JsonProcessingException;


}
