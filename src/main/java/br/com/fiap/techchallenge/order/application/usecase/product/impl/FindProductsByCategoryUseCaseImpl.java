package br.com.fiap.techchallenge.order.application.usecase.product.impl;

import br.com.fiap.techchallenge.order.application.exceptions.DoesNotExistException;
import br.com.fiap.techchallenge.order.application.persistence.ProductPersistence;
import br.com.fiap.techchallenge.order.application.usecase.product.FindProductsByCategoryUseCase;
import br.com.fiap.techchallenge.order.domain.models.Product;
import br.com.fiap.techchallenge.order.domain.models.enums.ProductCategoryEnum;
import br.com.fiap.techchallenge.order.domain.models.pageable.CustomPageable;

public class FindProductsByCategoryUseCaseImpl implements FindProductsByCategoryUseCase {

	private final ProductPersistence persistence;

	public FindProductsByCategoryUseCaseImpl(ProductPersistence persistence) {
		this.persistence = persistence;
	}

	@Override
	public CustomPageable<Product> findByCategory(ProductCategoryEnum category, Integer page, Integer size) {
		var products = persistence.findByCategory(category, page, size);

		if (products.content().isEmpty()) {
			throw new DoesNotExistException("Products not found by category");
		}

		return products;
	}
}