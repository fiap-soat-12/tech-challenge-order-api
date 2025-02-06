package br.com.fiap.techchallenge.order.infra.entrypoint.consumer;

import br.com.fiap.techchallenge.order.application.usecase.EvolveOrderUseCase;
import br.com.fiap.techchallenge.order.infra.entrypoint.consumer.dto.OrderEvolveDTO;
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

    @InjectMocks
    private OrderEvolveConsumer orderEvolveConsumer;

    private OrderEvolveDTO evolveDTO;

    @BeforeEach
    void setUp() {
        this.buildArranges();
    }

    @Test
    @DisplayName("Should Call OrderEvolveConsumer When Receiving message")
    void shouldCallOrderEvolveConsumerWhenReceivingMessage() {
        orderEvolveConsumer.receiveMessage(evolveDTO);

        verify(evolveOrderUseCase, times(1)).evolveOrder(evolveDTO);
    }

    private void buildArranges(){
        evolveDTO = new OrderEvolveDTO(UUID.randomUUID(), true);
    }

}