package br.com.fiap.techchallenge.order.application.usecase.product;

import br.com.fiap.techchallenge.order.domain.models.Product;

public interface CreateProductUseCase {

	void create(Product product);

}