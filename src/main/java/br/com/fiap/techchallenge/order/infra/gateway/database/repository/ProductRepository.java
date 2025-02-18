package br.com.fiap.techchallenge.order.infra.gateway.database.repository;

import br.com.fiap.techchallenge.order.domain.models.enums.ProductCategoryEnum;
import br.com.fiap.techchallenge.order.infra.gateway.database.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {

	Page<ProductEntity> findByCategory(ProductCategoryEnum category, Pageable pageable);

}
