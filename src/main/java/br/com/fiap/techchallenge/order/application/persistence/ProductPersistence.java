package br.com.fiap.techchallenge.order.application.persistence;


import br.com.fiap.techchallenge.order.domain.models.Product;
import br.com.fiap.techchallenge.order.domain.models.enums.ProductCategoryEnum;
import br.com.fiap.techchallenge.order.domain.models.pageable.CustomPageable;

import java.util.Optional;
import java.util.UUID;

public interface ProductPersistence {

	CustomPageable<Product> findByCategory(ProductCategoryEnum category, Integer page, Integer size);

	void create(Product product);

	Optional<Product> findById(UUID id);

	void update(Product product);

}