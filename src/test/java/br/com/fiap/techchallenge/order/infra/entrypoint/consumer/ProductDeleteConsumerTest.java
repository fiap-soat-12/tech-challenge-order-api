package br.com.fiap.techchallenge.order.infra.entrypoint.consumer;

import br.com.fiap.techchallenge.order.application.usecase.product.DeleteProductByIdUseCase;
import br.com.fiap.techchallenge.order.infra.entrypoint.consumer.dto.ProductDeleteDTO;
import br.com.fiap.techchallenge.order.infra.entrypoint.consumer.mapper.ProductMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductDeleteConsumerTest {

    @Mock
    private DeleteProductByIdUseCase deleteProductByIdUseCase;

    @InjectMocks
    private ProductDeleteConsumer productDeleteConsumer;

    private UUID idProduct;

    @BeforeEach
    void setUp() {
        this.buildArranges();
    }

    @Test
    @DisplayName("Should Call DeleteProductByIdUseCase When Receiving message")
    void shouldCallDeleteProductByIdUseCaseWhenReceivingMessage() {
        productDeleteConsumer.receiveMessage(new ProductDeleteDTO(idProduct));

        verify(deleteProductByIdUseCase, times(1)).delete(idProduct);
    }

    private void buildArranges(){
        idProduct = UUID.randomUUID();
    }

}