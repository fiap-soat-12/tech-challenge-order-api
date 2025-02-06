package br.com.fiap.techchallenge.order.application.usecase.product.impl;

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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateProductUseCaseImplTest {

    @Mock
    private ProductPersistence persistence;

    @InjectMocks
    private UpdateProductUseCaseImpl updateProductUseCase;

    private UUID productId;
    private Product product;

    @BeforeEach
    void setUp() {
        this.buildArranges();
    }

    @Test
    @DisplayName("Should do update when the id is found")
    void shouldDoUpdateWhenTheIdIsFound() {
        when(persistence.findById(productId)).thenReturn(Optional.of(product));

        updateProductUseCase.update(product);

        verify(persistence).findById(product.getId());
        verify(persistence).update(product);
    }

    @Test
    @DisplayName("Shouldn't update when the id is not found")
    void shouldntUpdateWhenTheIdIsNotFound() {
        when(persistence.findById(productId)).thenReturn(Optional.empty());

        assertThrows(DoesNotExistException.class, () -> updateProductUseCase.update(product));
        verify(persistence, never()).update(any());
    }

    private void buildArranges() {
        productId = UUID.randomUUID();
        product = new Product(productId, "Sanduíche de Frango", ProductCategoryEnum.MAIN_COURSE,
                new BigDecimal("99.99"), "Sanduíche de frango com salada", ProductStatusEnum.ACTIVE,
                LocalDateTime.now());
    }

}