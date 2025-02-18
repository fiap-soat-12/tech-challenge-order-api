package br.com.fiap.techchallenge.order.application.usecase.product.impl;

import java.util.UUID;
import br.com.fiap.techchallenge.order.application.exceptions.DoesNotExistException;
import br.com.fiap.techchallenge.order.application.persistence.ProductPersistence;
import br.com.fiap.techchallenge.order.application.usecase.product.DeleteProductByIdUseCase;
import br.com.fiap.techchallenge.order.domain.models.enums.ProductStatusEnum;

public class DeleteProductByIdUseCaseImpl implements DeleteProductByIdUseCase {

	private final ProductPersistence persistence;

	public DeleteProductByIdUseCaseImpl(ProductPersistence persistence) {
		this.persistence = persistence;
	}

	@Override
	public void delete(UUID id) {
		var product = persistence.findById(id)
				.orElseThrow(() -> new DoesNotExistException("Product Doesn't Exist"));

		product.setStatus(ProductStatusEnum.INACTIVE);

		persistence.update(product);
	}
}