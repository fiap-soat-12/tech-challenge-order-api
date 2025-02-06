package br.com.fiap.techchallenge.order.infra.gateway.producer.cook;

import br.com.fiap.techchallenge.order.domain.models.Order;
import br.com.fiap.techchallenge.order.domain.models.OrderDetails;
import br.com.fiap.techchallenge.order.domain.models.OrderTimestamps;
import br.com.fiap.techchallenge.order.domain.models.enums.OrderStatusEnum;
import br.com.fiap.techchallenge.order.infra.gateway.producer.cook.dto.CookDTO;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CookProducerTest {

    @Mock
    private SqsTemplate sqsTemplate;

    @InjectMocks
    private CookProducer cookProducer;

    private CookDTO cookDTO;

    @BeforeEach
    void setUp() {
        this.buildArranges();
        ReflectionTestUtils.setField(cookProducer, "produceCookQueue", "cook-order-create-queue");
    }

    @Test
    @DisplayName("Should Send To Cook Queue")
    void shouldSendToCookQueue() {
        cookProducer.sendToCook(cookDTO);

        verify(sqsTemplate).send("cook-order-create-queue", cookDTO);
    }

    private void buildArranges(){
        Order order = new Order(UUID.randomUUID(), BigDecimal.valueOf(100),
                new OrderDetails(1, OrderStatusEnum.RECEIVED, true, Collections.emptyList(), null, "payment123"),
                new OrderTimestamps(LocalDateTime.now(), LocalDateTime.now()));
        cookDTO = new CookDTO(order);
    }
}