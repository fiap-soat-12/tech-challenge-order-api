package br.com.fiap.techchallenge.order.infra.gateway.producer.paid;

import br.com.fiap.techchallenge.order.infra.gateway.producer.paid.dto.PaidDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PaidProducer {

    @Value("${sqs.queue.paid.evolve.producer}")
    private String producePaidQueue;

    private final SqsTemplate sqsTemplate;
    private final ObjectMapper objectMapper;

    public PaidProducer(SqsTemplate sqsTemplate, ObjectMapper objectMapper) {
        this.sqsTemplate = sqsTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendToPaid(PaidDTO dto) throws JsonProcessingException {
        sqsTemplate.send(producePaidQueue, objectMapper.writeValueAsString(dto));
    }
}
