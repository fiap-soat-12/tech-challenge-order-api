package br.com.fiap.techchallenge.order.application.usecase.product.impl;

import br.com.fiap.techchallenge.order.application.persistence.ProductPersistence;
import br.com.fiap.techchallenge.order.application.usecase.product.CreateProductUseCase;
import br.com.fiap.techchallenge.order.domain.models.Product;

public class CreateProductUseCaseImpl implements CreateProductUseCase {

	private final ProductPersistence persistence;

	public CreateProductUseCaseImpl(ProductPersistence persistence) {
		this.persistence = persistence;
	}

	@Override
	public void create(Product product) {
		persistence.create(product);
	}

}