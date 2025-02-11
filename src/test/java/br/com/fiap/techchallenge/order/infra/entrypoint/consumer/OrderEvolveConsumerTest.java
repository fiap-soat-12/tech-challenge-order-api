package br.com.fiap.techchallenge.order.infra.entrypoint.consumer;

import br.com.fiap.techchallenge.order.application.usecase.EvolveOrderUseCase;
import br.com.fiap.techchallenge.order.infra.entrypoint.consumer.dto.OrderEvolveDTO;
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

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderEvolveConsumerTest {

    @Mock
    private EvolveOrderUseCase evolveOrderUseCase;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private OrderEvolveConsumer orderEvolveConsumer;

    private OrderEvolveDTO evolveDTO;

    @BeforeEach
    void setUp() {
        this.buildArranges();
    }

    @Test
    @DisplayName("Should Call OrderEvolveConsumer When Receiving message")
    void shouldCallOrderEvolveConsumerWhenReceivingMessage() throws JsonProcessingException {
        orderEvolveConsumer.receiveMessage(evolveDTO.toString());

        verify(evolveOrderUseCase, times(1))
                .evolveOrder(objectMapper.readValue(evolveDTO.toString(), OrderEvolveDTO.class));
        verify(objectMapper, times(2)).readValue(evolveDTO.toString(), OrderEvolveDTO.class);
    }

    private void buildArranges(){
        evolveDTO = new OrderEvolveDTO(UUID.randomUUID(), true);
    }

}