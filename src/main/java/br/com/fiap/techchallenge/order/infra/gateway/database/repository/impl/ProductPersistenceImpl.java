package br.com.fiap.techchallenge.order.infra.gateway.database.repository.impl;

import br.com.fiap.techchallenge.order.application.persistence.ProductPersistence;
import br.com.fiap.techchallenge.order.domain.models.Product;
import br.com.fiap.techchallenge.order.domain.models.enums.ProductCategoryEnum;
import br.com.fiap.techchallenge.order.domain.models.pageable.CustomPageable;
import br.com.fiap.techchallenge.order.infra.gateway.database.entities.ProductEntity;
import br.com.fiap.techchallenge.order.infra.gateway.database.mapper.PageMapper;
import br.com.fiap.techchallenge.order.infra.gateway.database.repository.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class ProductPersistenceImpl implements ProductPersistence {

	private final ProductRepository repository;

	private final PageMapper<Product> mapper;

	public ProductPersistenceImpl(ProductRepository repository, PageMapper<Product> mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public void create(Product product) {
		var productEntity = new ProductEntity(product);
		repository.save(productEntity);
	}

	@Override
	public CustomPageable<Product> findByCategory(ProductCategoryEnum category, Integer page, Integer size) {
		var products = repository.findByCategory(category, PageRequest.of(page, size)).map(ProductEntity::toProduct);
		return mapper.toDomainPage(products);
	}

	@Override
	public Optional<Product> findById(UUID id) {
		return repository.findById(id).map(ProductEntity::toProduct);
	}

	@Override
	public void update(Product product) {
		var productEntity = new ProductEntity().update(product);
		repository.save(productEntity);
	}
}