package br.com.fiap.techchallenge.order.infra.config.openapi;

import br.com.fiap.techchallenge.order.infra.entrypoint.controller.dto.*;
import br.com.fiap.techchallenge.order.infra.entrypoint.controller.handler.ProblemDTO;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SpringDocConfigurations {

	@Value("${springdoc.api-docs.server.url:}")
	private String apiDocsUrl;

	@Bean
	public OpenAPI customOpenAPI() {
		OpenAPI openAPI = new OpenAPI().info(new Info().title("Tech Challenge FIAP - Order API").version("v1").description("""
				Microservice Order for Tech Challenge of Master's Degree in Software Architecture 
				Developed by:
				 - Alexandre Miranda - RM357321
				 - Diego Ceccon - RM357437
				 - Jéssica Rodrigues - RM357218
				 - Rodrigo Sartori - RM358002
				 - Wilton Souza - RM357991
				""").contact(new Contact().name("SOAT 8 Group").email("soat-group@gmail.com")))
			.components(new Components().schemas(this.generateSchemas()));

		if (apiDocsUrl != null && !apiDocsUrl.isEmpty()) {
			openAPI.addServersItem(new Server().url(apiDocsUrl));
		}

		return openAPI;
	}

	@SuppressWarnings("rawtypes")
	private Map<String, Schema> generateSchemas() {
		final Map<String, Schema> schemaMap = new HashMap<>();

		Map<String, Schema> problemSchema = ModelConverters.getInstance().read(ProblemDTO.class);
		Map<String, Schema> customerResponseDto = ModelConverters.getInstance().read(CustomerResponseDTO.class);
		Map<String, Schema> productPageResponseDto = ModelConverters.getInstance().read(ProductPageResponseDTO.class);
		Map<String, Schema> productResponseDto = ModelConverters.getInstance().read(ProductResponseDTO.class);
		Map<String, Schema> pageResponseDto = ModelConverters.getInstance().read(PageResponseDTO.class);
		Map<String, Schema> createOrderResponseDto = ModelConverters.getInstance().read(CreateOrderResponseDTO.class);
		Map<String, Schema> orderStatusResponseDto = ModelConverters.getInstance().read(OrderStatusResponseDTO.class);
		Map<String, Schema> orderProductsResponseDto = ModelConverters.getInstance()
				.read(CreateOrderRequestDTO.OrderProducts.class);


		Schema errorsValidateDataArraySchema = new ArraySchema()
			.items(new Schema<>().$ref("#/components/schemas/ErrorsValidateData"));

		schemaMap.putAll(problemSchema);
		schemaMap.putAll(customerResponseDto);
		schemaMap.putAll(productPageResponseDto);
		schemaMap.putAll(productResponseDto);
		schemaMap.putAll(pageResponseDto);
		schemaMap.putAll(createOrderResponseDto);
		schemaMap.putAll(orderStatusResponseDto);
		schemaMap.putAll(orderProductsResponseDto);
		schemaMap.put("ErrorsValidateDataList", errorsValidateDataArraySchema);

		return schemaMap;
	}

}