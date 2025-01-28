package br.com.fiap.techchallenge.order.infra.entrypoint.consumer.mapper;

import br.com.fiap.techchallenge.order.domain.models.Product;
import br.com.fiap.techchallenge.order.infra.entrypoint.consumer.dto.ProductCreateDTO;
import br.com.fiap.techchallenge.order.infra.entrypoint.consumer.dto.ProductUpdateDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

	public Product toProduct(ProductCreateDTO dto) {
		return new Product(dto.name(), dto.category(), dto.price(), dto.description());
	}

	public Product toProduct(ProductUpdateDTO dto) {
		return new Product(dto.id(), dto.name(), dto.category(), dto.price(), dto.description());
	}
}