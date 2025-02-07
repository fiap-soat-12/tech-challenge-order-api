package br.com.fiap.techchallenge.order.infra.gateway.database.repository.impl;

import br.com.fiap.techchallenge.order.domain.models.Product;
import br.com.fiap.techchallenge.order.domain.models.enums.ProductCategoryEnum;
import br.com.fiap.techchallenge.order.domain.models.enums.ProductStatusEnum;
import br.com.fiap.techchallenge.order.domain.models.pageable.CustomPage;
import br.com.fiap.techchallenge.order.domain.models.pageable.CustomPageable;
import br.com.fiap.techchallenge.order.infra.gateway.database.entities.ProductEntity;
import br.com.fiap.techchallenge.order.infra.gateway.database.mapper.PageMapper;
import br.com.fiap.techchallenge.order.infra.gateway.database.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductPersistenceImplTest {

    @Mock
    private ProductRepository repository;

    @Mock
    private PageMapper<Product> mapper;

    @InjectMocks
    private ProductPersistenceImpl productPersistence;

    private Product product;

    private ProductEntity productEntity;

    private CustomPage domainPage;

    @BeforeEach
    void setUp() {
        this.buildArranges();
    }

    @Test
    @DisplayName("Should successfully save a ProductEntity to the database.")
    void shouldSaveProductEntity() {
        when(repository.save(any(ProductEntity.class))).thenReturn(productEntity);

        productPersistence.create(product);

        verify(repository).save(any(ProductEntity.class));
    }

    @Test
    @DisplayName("Should successfully return a specific ProductEntity in the database")
    void shouldReturnAProductEntity() {
        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(productEntity));

        Optional<Product> foundProduct = productPersistence.findById(product.getId());

        assertTrue(foundProduct.isPresent());
        assertEquals(product.getId(), foundProduct.get().getId());
        assertEquals(product.getName(), foundProduct.get().getName());
        assertEquals(product.getCategory(), foundProduct.get().getCategory());
        assertEquals(product.getPrice(), foundProduct.get().getPrice());
        assertEquals(product.getDescription(), foundProduct.get().getDescription());
        assertEquals(product.getStatus(), foundProduct.get().getStatus());

        verify(repository).findById(any(UUID.class));
    }

    @Test
    @DisplayName("Should get Products by category successfully.")
    void shouldGetProductsByCategorySuccessfully() {
        var pageRequest = PageRequest.of(0, 10);

        List<ProductEntity> productEntities = List.of(new ProductEntity(product));
        CustomPageable<Product> customPageable = new CustomPageable<>(new ArrayList<>(), domainPage);

        when(repository.findByCategory(ProductCategoryEnum.MAIN_COURSE, pageRequest))
                .thenReturn(new PageImpl<>(productEntities, pageRequest, productEntities.size()));
        when(mapper.toDomainPage(any(Page.class))).thenReturn(customPageable);

        var result =
                productPersistence.findByCategory(ProductCategoryEnum.MAIN_COURSE, pageRequest.getPageNumber(), pageRequest.getPageSize());

        assertNotNull(result);
        assertEquals(customPageable, result);
        verify(repository).findByCategory(ProductCategoryEnum.MAIN_COURSE, pageRequest);
        verify(mapper).toDomainPage(any(Page.class));
    }


    @Test
    @DisplayName("Should successfully update a ProductEntity to the database")
    void shouldUpdateProductEntity() {
        when(repository.save(any(ProductEntity.class))).thenReturn(productEntity);

        productPersistence.update(product);

        verify(repository).save(any(ProductEntity.class));
    }

    private void buildArranges() {
        UUID id = UUID.randomUUID();
        product = new Product(id, "Sanduíche de Frango", ProductCategoryEnum.MAIN_COURSE, new BigDecimal("99.99"),
                "Sanduíche de frango com salada", ProductStatusEnum.ACTIVE, LocalDateTime.now());
        productEntity = new ProductEntity(product);

        domainPage = new CustomPage(1L, 1L, 1L, 1L, true, true, 1L, false);
    }
}