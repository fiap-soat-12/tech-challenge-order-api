package br.com.fiap.techchallenge.order.infra.entrypoint.controller.dto;

import br.com.fiap.techchallenge.order.domain.models.Product;
import br.com.fiap.techchallenge.order.domain.models.pageable.CustomPageable;

import java.util.List;

public record ProductPageResponseDTO(List<ProductResponseDTO> content, PageResponseDTO page) {
	public ProductPageResponseDTO(CustomPageable<Product> productPage) {
		this(productPage.content().stream().map(ProductResponseDTO::new).toList(),
				new PageResponseDTO(productPage.page()));
	}
}