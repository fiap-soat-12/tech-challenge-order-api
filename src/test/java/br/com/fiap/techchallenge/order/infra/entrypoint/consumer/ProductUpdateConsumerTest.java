package br.com.fiap.techchallenge.order.infra.entrypoint.consumer;

import br.com.fiap.techchallenge.order.application.usecase.product.impl.UpdateProductUseCaseImpl;
import br.com.fiap.techchallenge.order.domain.models.Product;
import br.com.fiap.techchallenge.order.domain.models.enums.ProductCategoryEnum;
import br.com.fiap.techchallenge.order.domain.models.enums.ProductStatusEnum;
import br.com.fiap.techchallenge.order.infra.entrypoint.consumer.dto.ProductUpdateDTO;
import br.com.fiap.techchallenge.order.infra.entrypoint.consumer.mapper.ProductMapper;
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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductUpdateConsumerTest {

    @Mock
    private UpdateProductUseCaseImpl updateProductUseCase;

    @Mock
    private ProductMapper mapper;

    @InjectMocks
    private ProductUpdateConsumer productUpdateConsumer;

    private ProductUpdateDTO productUpdateDTO;
    private Product product;

    @BeforeEach
    void setUp() {
        this.buildArranges();
    }

    @Test
    @DisplayName("Should Call ProductUpdateConsumer When Receiving message")
    void shouldCallProductUpdateConsumerWhenReceivingMessage() {
        when(mapper.toProduct(productUpdateDTO)).thenReturn(product);

        productUpdateConsumer.receiveMessage(productUpdateDTO);

        verify(mapper, times(1)).toProduct(productUpdateDTO);
        verify(updateProductUseCase, times(1)).update(product);
    }

    private void buildArranges(){
        productUpdateDTO = new ProductUpdateDTO(UUID.randomUUID(), "Sanduíche de Frango",
                ProductCategoryEnum.MAIN_COURSE, new BigDecimal("99.99"), "Sanduíche de frango com salada");
        product = new Product(UUID.randomUUID(), "Sanduíche de Frango", ProductCategoryEnum.MAIN_COURSE,
                new BigDecimal("99.99"), "Sanduíche de frango com salada", ProductStatusEnum.ACTIVE,
                LocalDateTime.now());
    }

}