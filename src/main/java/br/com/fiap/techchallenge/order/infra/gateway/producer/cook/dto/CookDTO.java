package br.com.fiap.techchallenge.order.infra.gateway.producer.cook.dto;


import br.com.fiap.techchallenge.order.domain.models.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record CookDTO(UUID id,
                      Integer sequence,
                      List<CookProductDTO> products,
                      LocalDateTime createdAt) {
    public CookDTO(Order order) {
        this(order.getId(), order.getSequence(), order.getProducts().stream().map(CookProductDTO::new).toList(),
                order.getCreatedAt());
    }
}
