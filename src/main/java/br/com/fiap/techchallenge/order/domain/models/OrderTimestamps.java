package br.com.fiap.techchallenge.order.domain.models;

import java.time.LocalDateTime;

public class OrderTimestamps {
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public OrderTimestamps(LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
