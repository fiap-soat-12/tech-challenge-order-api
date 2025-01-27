package br.com.fiap.techchallenge.order.application.usecase.product.impl;

import br.com.fiap.techchallenge.order.application.exceptions.DoesNotExistException;
import br.com.fiap.techchallenge.order.application.persistence.ProductPersistence;
import br.com.fiap.techchallenge.order.application.usecase.product.UpdateProductUseCase;
import br.com.fiap.techchallenge.order.domain.models.Product;

public class UpdateProductUseCaseImpl implements UpdateProductUseCase {

	private final ProductPersistence persistence;

	public UpdateProductUseCaseImpl(ProductPersistence persistence) {
		this.persistence = persistence;
	}

	@Override
	public void update(Product product) {
		var productFound = persistence.findById(product.getId());

		if (productFound.isEmpty()) {
			throw new DoesNotExistException("Product Doesn't Exist");
		}

		var productUpdated = productFound.get().update(product);

		persistence.update(productUpdated);
	}
}