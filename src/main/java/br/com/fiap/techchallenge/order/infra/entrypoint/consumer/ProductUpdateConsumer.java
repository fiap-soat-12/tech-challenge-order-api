package br.com.fiap.techchallenge.order.infra.entrypoint.consumer;

import br.com.fiap.techchallenge.order.application.usecase.product.UpdateProductUseCase;
import br.com.fiap.techchallenge.order.infra.entrypoint.consumer.dto.ProductUpdateDTO;
import br.com.fiap.techchallenge.order.infra.entrypoint.consumer.mapper.ProductMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
public class ProductUpdateConsumer {
    private final UpdateProductUseCase updateProductUseCase;
    private final ProductMapper mapper;

    public ProductUpdateConsumer(UpdateProductUseCase updateProductUseCase, ProductMapper mapper) {
        this.updateProductUseCase = updateProductUseCase;
        this.mapper = mapper;
    }

    @SqsListener("${sqs.queue.product.update.listener}")
    public void receiveMessage(ProductUpdateDTO productDTO) {
        updateProductUseCase.update(mapper.toProduct(productDTO));
    }
}