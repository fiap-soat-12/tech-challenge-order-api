package br.com.fiap.techchallenge.order.infra.entrypoint.controller.mapper;

import br.com.fiap.techchallenge.order.application.usecase.order.dto.CreateOrderDTO;
import br.com.fiap.techchallenge.order.infra.entrypoint.controller.dto.CreateOrderRequestDTO;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OrderMapper {

	public CreateOrderDTO toCreateOrder(CreateOrderRequestDTO dto) {
		return new CreateOrderDTO(dto.customerId() != null ? UUID.fromString(dto.customerId()) : null,
				dto.products()
					.stream()
					.map(orderProducts -> new CreateOrderDTO.OrderProducts(UUID.fromString(orderProducts.id()),
							orderProducts.observation()))
					.toList());
	}

}
