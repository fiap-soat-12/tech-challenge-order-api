package br.com.fiap.techchallenge.order.infra.gateway.producer.payment;

import br.com.fiap.techchallenge.order.infra.gateway.producer.payment.dto.PaymentDTO;
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
import java.util.UUID;

import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class PaymentProducerTest {

    @Mock
    private SqsTemplate sqsTemplate;

    @InjectMocks
    private PaymentProducer paymentProducer;

    private PaymentDTO paymentDTO;

    @BeforeEach
    void setUp() {
        this.buildArranges();
        ReflectionTestUtils.setField(paymentProducer, "producePaymentQueue", "payment-order-create-queue");
    }

    @Test
    @DisplayName("Should Send To Payment Queue")
    void shouldSendToPaymentQueue() {
        paymentProducer.sendToPayment(paymentDTO);

        verify(sqsTemplate).send("payment-order-create-queue", paymentDTO);
    }

    private void buildArranges(){
        paymentDTO = new PaymentDTO(UUID.randomUUID(), new BigDecimal(10));
    }

}