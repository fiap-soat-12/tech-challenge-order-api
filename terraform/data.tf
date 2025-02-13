data "aws_eks_cluster" "eks_cluster" {
  name = var.eks_cluster_name
}

data "aws_eks_cluster_auth" "eks_cluster" {
  name = data.aws_eks_cluster.eks_cluster.name
}

# data "aws_ecr_repository" "ecr_repo" {
#   name = var.ecr_repository_name
# }

# data "aws_ecr_image" "latest_image" {
#   repository_name = data.aws_ecr_repository.ecr_repo.name
#   image_tag       = "latest"
# }


data "aws_sqs_queue" "order_product_create_queue" {
  name = var.order_product_create_queue
}

data "aws_sqs_queue" "order_product_delete_queue" {
  name = var.order_product_delete_queue
}

data "aws_sqs_queue" "order_status_update_queue" {
  name = var.order_status_update_queue
}

data "aws_sqs_queue" "payment_order_create_queue" {
  name = var.payment_order_create_queue
}
data "aws_sqs_queue" "cook_order_create_queue" {
  name = var.cook_order_create_queue
}

data "aws_sqs_queue" "order_product_update_queue" {
  name = var.order_product_update_queue
}

data "aws_ssm_parameter" "db_url" {
  name = "/fiap-tech-challenge/cook-rds-endpoint"
}

data "aws_secretsmanager_secret_version" "db_credentials" {
  secret_id = var.secret_name
}
