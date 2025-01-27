package br.com.fiap.techchallenge.order.infra.entrypoint.consumer;

import br.com.fiap.techchallenge.order.application.usecase.product.UpdateProductUseCase;
import br.com.fiap.techchallenge.order.infra.entrypoint.consumer.dto.AcceptQueue;
import br.com.fiap.techchallenge.order.infra.entrypoint.consumer.dto.ProductUpdateDTO;
import br.com.fiap.techchallenge.order.infra.entrypoint.consumer.mapper.ProductMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProductUpdateConsumer {

    private final UpdateProductUseCase updateProductUseCase;
    private final ProductMapper mapper;
    private final SqsTemplate sqsTemplate;

    @Value("${sqs.queue.product.update.accept.producer}")
    private String acceptQueue;

    public ProductUpdateConsumer(UpdateProductUseCase updateProductUseCase, ProductMapper mapper, SqsTemplate sqsTemplate) {
        this.updateProductUseCase = updateProductUseCase;
        this.mapper = mapper;
        this.sqsTemplate = sqsTemplate;
    }

    @SqsListener("${sqs.queue.product.update.listener}")
    public void receiveMessage(ProductUpdateDTO productDTO) {
        updateProductUseCase.update(mapper.toProduct(productDTO));

        sqsTemplate.send(acceptQueue, new AcceptQueue("Order", true));
    }
}