package br.com.fiap.techchallenge.order.application.usecase.product.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import br.com.fiap.techchallenge.order.domain.models.enums.ProductCategoryEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.fiap.techchallenge.order.application.exceptions.DoesNotExistException;
import br.com.fiap.techchallenge.order.application.persistence.ProductPersistence;
import br.com.fiap.techchallenge.order.domain.models.Product;
import br.com.fiap.techchallenge.order.domain.models.enums.ProductStatusEnum;

@ExtendWith(MockitoExtension.class)
class DeleteProductByIdUseCaseImplTest {

    @Mock
    private ProductPersistence persistence;

    @InjectMocks
    private DeleteProductByIdUseCaseImpl deleteProductByIdUseCase;

    private UUID productId;
    private Product product;

    @BeforeEach
    void setUp() {
        this.buildArranges();
    }

    @Test
    @DisplayName("Should do logical delete when the id is found")
    void shouldDoLogicalDeleteWhenTheIdIsFound() {
        when(persistence.findById(productId)).thenReturn(Optional.of(product));

        deleteProductByIdUseCase.delete(productId);

        assertEquals(ProductStatusEnum.INACTIVE, product.getStatus());
        verify(persistence).update(product);
    }

    @Test
    @DisplayName("Shouldn't logical delete when the id is not found")
    void shouldntLogicalDeleteWhenTheIdIsNotFound() {
        when(persistence.findById(productId)).thenReturn(Optional.empty());

        assertThrows(DoesNotExistException.class, () -> deleteProductByIdUseCase.delete(productId));
        verify(persistence, never()).update(any());
    }

    private void buildArranges() {
        productId = UUID.randomUUID();
        product = new Product(productId, "Sanduíche de Frango", ProductCategoryEnum.MAIN_COURSE,
                new BigDecimal("99.99"), "Sanduíche de frango com salada", ProductStatusEnum.ACTIVE,
                LocalDateTime.now());
    }
}