package br.com.fiap.techchallenge.order.infra.entrypoint.controller.mapper;

import br.com.fiap.techchallenge.order.domain.models.Customer;
import br.com.fiap.techchallenge.order.infra.entrypoint.controller.dto.CustomerRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

	public Customer toCustomer(CustomerRequestDTO dto) {
		return new Customer(dto.name(), dto.document(), dto.email());
	}

}
