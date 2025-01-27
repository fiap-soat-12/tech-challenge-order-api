package br.com.fiap.techchallenge.order.infra.gateway.producer.cook;

import br.com.fiap.techchallenge.order.infra.gateway.producer.cook.dto.CookDTO;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CookProducer {

    @Value("${sqs.queue.cook.create.producer}")
    private String produceCookQueue;

    private final SqsTemplate sqsTemplate;

    public CookProducer(SqsTemplate sqsTemplate) {
        this.sqsTemplate = sqsTemplate;
    }

    public void sendToCook(CookDTO dto){
        sqsTemplate.send(produceCookQueue, dto);
    }
}
