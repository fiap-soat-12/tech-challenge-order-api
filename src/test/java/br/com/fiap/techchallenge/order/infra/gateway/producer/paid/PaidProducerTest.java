package br.com.fiap.techchallenge.order.infra.gateway.producer.paid;

import br.com.fiap.techchallenge.order.infra.gateway.producer.paid.dto.PaidDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PaidProducerTest {

    @Mock
    private SqsTemplate sqsTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private PaidProducer paidProducer;

    private PaidDTO paidDTO;

    @BeforeEach
    void setUp() {
        this.buildArranges();
        ReflectionTestUtils.setField(paidProducer, "producePaidQueue", "order-product-update-queue");
    }

    @Test
    @DisplayName("Should Send To Paid Queue")
    void shouldSendToPaidQueue() throws JsonProcessingException {
        paidProducer.sendToPaid(paidDTO);

        verify(sqsTemplate).send("order-product-update-queue", objectMapper.writeValueAsString(paidDTO));
        verify(objectMapper, times(2)).writeValueAsString(paidDTO);
    }

    private void buildArranges() {
        paidDTO = new PaidDTO(UUID.randomUUID(), true);
    }

}