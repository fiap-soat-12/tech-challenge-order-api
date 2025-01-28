package br.com.fiap.techchallenge.order.infra.entrypoint.consumer;

import br.com.fiap.techchallenge.order.application.usecase.product.DeleteProductByIdUseCase;
import br.com.fiap.techchallenge.order.infra.entrypoint.consumer.dto.AcceptQueue;
import br.com.fiap.techchallenge.order.infra.entrypoint.consumer.dto.ProductDeleteDTO;
import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProductDeleteConsumer {

    private final DeleteProductByIdUseCase deleteProductByIdUseCase;
    private final SqsTemplate sqsTemplate;

    @Value("${sqs.queue.product.delete.accept.producer}")
    private String acceptQueue;

    public ProductDeleteConsumer(DeleteProductByIdUseCase deleteProductByIdUseCase, SqsTemplate sqsTemplate) {
        this.deleteProductByIdUseCase = deleteProductByIdUseCase;
        this.sqsTemplate = sqsTemplate;
    }

    @SqsListener("${sqs.queue.product.delete.listener}")
    public void receiveMessage(ProductDeleteDTO productDTO) {
        deleteProductByIdUseCase.delete(productDTO.id());

        sqsTemplate.send(acceptQueue, new AcceptQueue("Order", true));
    }
}