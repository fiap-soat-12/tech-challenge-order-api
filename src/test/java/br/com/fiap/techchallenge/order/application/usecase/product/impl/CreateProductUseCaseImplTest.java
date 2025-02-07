package br.com.fiap.techchallenge.order.application.usecase.product.impl;

import static org.mockito.Mockito.*;

import br.com.fiap.techchallenge.order.application.persistence.ProductPersistence;
import br.com.fiap.techchallenge.order.domain.models.Product;
import br.com.fiap.techchallenge.order.domain.models.enums.ProductCategoryEnum;
import br.com.fiap.techchallenge.order.domain.models.enums.ProductStatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class CreateProductUseCaseImplTest {

    @Mock
    private ProductPersistence productPersistence;

    @InjectMocks
    private CreateProductUseCaseImpl createProductUseCase;

    private Product product;

    @BeforeEach
    void setUp() {
        this.buildArranges();
    }

    @Test
    @DisplayName("Should create a Product successfully")
    void shouldCreateProductSuccessfully() {

        createProductUseCase.create(product);

        verify(productPersistence, times(1)).create(product);
    }

    private void buildArranges() {
        product = new Product(UUID.randomUUID(), "Sanduíche de Frango", ProductCategoryEnum.MAIN_COURSE,
                new BigDecimal("99.99"), "Sanduíche de frango com salada", ProductStatusEnum.ACTIVE,
                LocalDateTime.now());
    }
}