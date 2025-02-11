package br.com.fiap.techchallenge.order.infra.gateway.producer.cook.dto;

import br.com.fiap.techchallenge.order.domain.models.OrderProduct;

import java.util.UUID;

public record CookProductDTO(UUID id,
                             String customization) {
    public CookProductDTO(OrderProduct orderProduct) {
        this(orderProduct.getProductId(), orderProduct.getCustomization());
    }
}
