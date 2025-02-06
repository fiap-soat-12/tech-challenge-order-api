package br.com.fiap.techchallenge.order.infra.entrypoint.consumer;

import br.com.fiap.techchallenge.order.application.usecase.product.CreateProductUseCase;
import br.com.fiap.techchallenge.order.domain.models.Product;
import br.com.fiap.techchallenge.order.domain.models.enums.ProductCategoryEnum;
import br.com.fiap.techchallenge.order.domain.models.enums.ProductStatusEnum;
import br.com.fiap.techchallenge.order.infra.entrypoint.consumer.dto.ProductCreateDTO;
import br.com.fiap.techchallenge.order.infra.entrypoint.consumer.mapper.ProductMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductCreateConsumerTest {

    @Mock
    private CreateProductUseCase createProductUseCase;

    @Mock
    private ProductMapper mapper;

    @InjectMocks
    private ProductCreateConsumer productCreateConsumer;

    private ProductCreateDTO productCreateDTO;
    private Product product;

    @BeforeEach
    void setUp() {
        this.buildArranges();
    }

    @Test
    @DisplayName("Should Call CreateProductUseCase When Receiving message")
    void shouldCallCreateProductUseCaseWhenReceivingMessage() {
        when(mapper.toProduct(productCreateDTO)).thenReturn(product);

        productCreateConsumer.receiveMessage(productCreateDTO);

        verify(mapper, times(1)).toProduct(productCreateDTO);
        verify(createProductUseCase, times(1)).create(product);
    }

    private void buildArranges(){
        productCreateDTO = new ProductCreateDTO(UUID.randomUUID(), "Sanduíche de Frango",
                ProductCategoryEnum.MAIN_COURSE, new BigDecimal("99.99"), "Sanduíche de frango com salada");
        product = new Product(UUID.randomUUID(), "Sanduíche de Frango", ProductCategoryEnum.MAIN_COURSE,
                new BigDecimal("99.99"), "Sanduíche de frango com salada", ProductStatusEnum.ACTIVE,
                LocalDateTime.now());
    }
}
