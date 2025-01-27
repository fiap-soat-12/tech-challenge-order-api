package br.com.fiap.techchallenge.order.infra.gateway.producer.payment;

import br.com.fiap.techchallenge.order.infra.gateway.producer.payment.dto.PaymentDTO;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PaymentProducer {

    @Value("${sqs.queue.payment.create.producer}")
    private String producePaymentQueue;

    private final SqsTemplate sqsTemplate;

    public PaymentProducer(SqsTemplate sqsTemplate) {
        this.sqsTemplate = sqsTemplate;
    }

    public void sendToPayment(PaymentDTO dto){
        sqsTemplate.send(producePaymentQueue, dto);
    }
}
