package br.com.fiap.techchallenge.order.application.usecase.product;

import br.com.fiap.techchallenge.order.domain.models.Product;
import br.com.fiap.techchallenge.order.domain.models.enums.ProductCategoryEnum;
import br.com.fiap.techchallenge.order.domain.models.pageable.CustomPageable;

public interface FindProductsByCategoryUseCase {

	CustomPageable<Product> findByCategory(ProductCategoryEnum category, Integer page, Integer size);

}