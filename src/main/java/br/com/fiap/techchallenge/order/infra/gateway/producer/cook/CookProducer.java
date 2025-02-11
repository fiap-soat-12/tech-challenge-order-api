package br.com.fiap.techchallenge.order.infra.gateway.producer.cook;

import br.com.fiap.techchallenge.order.infra.gateway.producer.cook.dto.CookDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CookProducer {

    @Value("${sqs.queue.cook.create.producer}")
    private String produceCookQueue;

    private final SqsTemplate sqsTemplate;
    private final ObjectMapper objectMapper;

    public CookProducer(SqsTemplate sqsTemplate, ObjectMapper objectMapper) {
        this.sqsTemplate = sqsTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendToCook(CookDTO dto) throws JsonProcessingException {
        sqsTemplate.send(produceCookQueue, objectMapper.writeValueAsString(dto));
    }
}
