package br.com.fiap.techchallenge.order.infra.entrypoint.consumer;

import br.com.fiap.techchallenge.order.application.usecase.product.CreateProductUseCase;
import br.com.fiap.techchallenge.order.infra.entrypoint.consumer.dto.ProductCreateDTO;
import br.com.fiap.techchallenge.order.infra.entrypoint.consumer.mapper.ProductMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
public class ProductCreateConsumer {

    private final CreateProductUseCase createProductUseCase;
    private final ProductMapper mapper;

    public ProductCreateConsumer(CreateProductUseCase createProductUseCase, ProductMapper mapper) {
        this.createProductUseCase = createProductUseCase;
        this.mapper = mapper;
    }

    @SqsListener("${sqs.queue.product.create.listener}")
    public void receiveMessage(ProductCreateDTO productDTO) {
        createProductUseCase.create(mapper.toProduct(productDTO));
    }
}