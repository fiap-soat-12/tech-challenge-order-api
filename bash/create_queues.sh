#!/bin/bash

# Define o endpoint base
ENDPOINT="http://localhost:4566"

# Define as filas a serem criadas
QUEUES=(
  "order-product-create-queue"
  "order-product-create-accept-queue"
  "order-product-delete-queue"
  "order-product-delete-accept-queue"
  "order-product-update-queue"
  "order-product-update-accept-queue"
  "payment-order-create-queue"
  "payment-order-create-accept-queue"
  "order-status-update-queue"
  "order-order-evolve-queue"
  "cook-order-create-queue"
)

# Loop para criar as filas
for QUEUE_NAME in "${QUEUES[@]}"; do
  echo "Criando a fila: $QUEUE_NAME"
  aws --endpoint="$ENDPOINT" sqs create-queue --queue-name "$QUEUE_NAME"
done

echo "Todas as filas foram criadas com sucesso!"
