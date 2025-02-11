package br.com.fiap.techchallenge.order.infra.entrypoint.consumer;

import br.com.fiap.techchallenge.order.application.usecase.product.DeleteProductByIdUseCase;
import br.com.fiap.techchallenge.order.infra.entrypoint.consumer.dto.ProductDeleteDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductDeleteConsumerTest {

    @Mock
    private DeleteProductByIdUseCase deleteProductByIdUseCase;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ProductDeleteConsumer productDeleteConsumer;

    private ProductDeleteDTO productDeleteDTO;

    @BeforeEach
    void setUp() {
        this.buildArranges();
    }

    @Test
    @DisplayName("Should Call DeleteProductByIdUseCase When Receiving message")
    void shouldCallDeleteProductByIdUseCaseWhenReceivingMessage() throws JsonProcessingException {
        when(objectMapper.readValue(productDeleteDTO.toString(), ProductDeleteDTO.class))
                .thenReturn(productDeleteDTO);

        productDeleteConsumer.receiveMessage(productDeleteDTO.toString());

        verify(deleteProductByIdUseCase, times(1)).delete(productDeleteDTO.id());
        verify(objectMapper, times(1))
                .readValue(productDeleteDTO.toString(), ProductDeleteDTO.class);
    }

    private void buildArranges(){
        productDeleteDTO = new ProductDeleteDTO(UUID.randomUUID());
    }

}