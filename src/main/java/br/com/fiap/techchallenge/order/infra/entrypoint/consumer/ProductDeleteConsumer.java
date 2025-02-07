package br.com.fiap.techchallenge.order.infra.entrypoint.consumer;

import br.com.fiap.techchallenge.order.application.usecase.product.DeleteProductByIdUseCase;
import br.com.fiap.techchallenge.order.infra.entrypoint.consumer.dto.ProductDeleteDTO;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
public class ProductDeleteConsumer {

    private final DeleteProductByIdUseCase deleteProductByIdUseCase;

    public ProductDeleteConsumer(DeleteProductByIdUseCase deleteProductByIdUseCase) {
        this.deleteProductByIdUseCase = deleteProductByIdUseCase;
    }

    @SqsListener("${sqs.queue.product.delete.listener}")
    public void receiveMessage(ProductDeleteDTO productDTO) {
        deleteProductByIdUseCase.delete(productDTO.id());
    }
}