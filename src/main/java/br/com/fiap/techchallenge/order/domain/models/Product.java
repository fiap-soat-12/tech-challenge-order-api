package br.com.fiap.techchallenge.order.domain.models;

import br.com.fiap.techchallenge.order.domain.models.enums.ProductCategoryEnum;
import br.com.fiap.techchallenge.order.domain.models.enums.ProductStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class Product {

	private UUID id;

	private String name;

	private ProductCategoryEnum category;

	private BigDecimal price;

	private String description;

	private ProductStatusEnum status;

	private LocalDateTime createdAt;

	public Product() {
	}

	public Product(UUID id, String name, ProductCategoryEnum category, BigDecimal price, String description,
                   ProductStatusEnum status, LocalDateTime createdAt) {
		this.id = id;
		this.name = name;
		this.category = category;
		this.price = price;
		this.description = description;
		this.status = status;
		this.createdAt = createdAt;
	}

	public Product(UUID id, String name, ProductCategoryEnum category, BigDecimal price, String description) {
		this.id = id;
		this.name = name;
		this.category = category;
		this.price = price;
		this.description = description;
		this.status = ProductStatusEnum.ACTIVE;
	}

	public void update(Product product) {
		this.name = Optional.ofNullable(product).map(Product::getName).orElse(this.name);
		this.category = Optional.ofNullable(product).map(Product::getCategory).orElse(this.category);
		this.price = Optional.ofNullable(product).map(Product::getPrice).orElse(this.price);
		this.description = Optional.ofNullable(product).map(Product::getDescription).orElse(this.description);
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public ProductCategoryEnum getCategory() {
		return category;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public String getDescription() {
		return description;
	}

	public ProductStatusEnum getStatus() {
		return status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setStatus(ProductStatusEnum status) {
		this.status = status;
	}

}