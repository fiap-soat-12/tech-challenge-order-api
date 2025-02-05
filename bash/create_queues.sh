#!/bin/bash

# Define o endpoint base
ENDPOINT="http://localhost:4566"

# Define as filas a serem criadas
QUEUES=(
  "order-product-create-queue"
  "order-product-delete-queue"
  "order-product-update-queue"
  "order-status-update-queue"
  "payment-order-create-queue"
  "cook-order-create-queue"
)

# Loop para criar as filas
for QUEUE_NAME in "${QUEUES[@]}"; do
  echo "Criando a fila: $QUEUE_NAME"
  aws --endpoint="$ENDPOINT" sqs create-queue --queue-name "$QUEUE_NAME"
done

echo "Todas as filas foram criadas com sucesso!"
