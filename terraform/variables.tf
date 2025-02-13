variable "aws_region" {
  type        = string
  default     = "us-east-1"
  description = "AWS Account region"
}

variable "eks_cluster_name" {
  type        = string
  default     = "fiap-tech-challenge-eks-cluster"
  description = "EKS Cluster name"
}

# variable "ecr_repository_name" {
#     type = string
#     default = "tech-challenge-cook-api"
#     description = "AWS ECR repository name"
# }

variable "server_port" {
    type = number
    default = 9100
    description = "Cook App server port"
}

variable "rds_instance_name" {
  type        = string
  default     = "tc_cook_db"
  description = "Rds instance name"
}

variable "AWS_ACCESS_KEY_ID" {
  type = string
  description = "aws_access_key_id"
}

variable "AWS_SECRET_ACCESS_KEY" {
  type = string
  description = "aws_secret_access_key"
}

variable "AWS_SESSION_TOKEN" {
  type = string
  description = "aws_session_token"
}

variable "secret_name" {
  type = string
  default = "tech-challenge-cook-db-credentials"
  description = "secret_name"
}

variable "database_port" {
  type = string
  default = "5432"
  description = "value of the database port"
}


variable "order_product_create_queue" {
  type = string
  default = "order-product-create-queue"
  description = "Order product create SQS Queue"
}

variable "order_product_delete_queue" {
  type = string
  default = "order-product-delete-queue"
  description = "Order product delete SQS Queue"
}

variable "order_status_update_queue" {
  type = string
  default = "order-status-update-queue"
  description = "Order status update SQS Queue"
}

variable "payment_order_create_queue" {
  type = string
  default = "payment-order-create-queue"
  description = "Payment order create SQS Queue"
}

variable "cook_order_create_queue" {
  type = string
  default = "cook-order-create-queue"
  description = "Cook order create SQS Queue"
}

variable "order_product_update_queue" {
  type = string
  default = "order-product-update-queue"
  description = "Order product update SQS Queue"
}

