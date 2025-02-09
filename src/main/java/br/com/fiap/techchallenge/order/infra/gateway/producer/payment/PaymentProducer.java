package br.com.fiap.techchallenge.order.infra.gateway.producer.payment;

import br.com.fiap.techchallenge.order.infra.gateway.producer.payment.dto.PaymentDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PaymentProducer {

    @Value("${sqs.queue.payment.create.producer}")
    private String producePaymentQueue;

    private final SqsTemplate sqsTemplate;
    private final ObjectMapper objectMapper;

    public PaymentProducer(SqsTemplate sqsTemplate, ObjectMapper objectMapper) {
        this.sqsTemplate = sqsTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendToPayment(PaymentDTO dto) throws JsonProcessingException {
        sqsTemplate.send(producePaymentQueue, objectMapper.writeValueAsString(dto));
    }
}
