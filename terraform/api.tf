resource "kubernetes_namespace" "order_namespace" {
  metadata {
    name = "order"
  }
}

resource "kubernetes_secret" "order_secret" {
  metadata {
    name      = "tech-challenge-order-secret"
    namespace = kubernetes_namespace.order_namespace.metadata[0].name
  }

  data = {
    aws_access_key_id     = var.AWS_ACCESS_KEY_ID
    aws_secret_access_key = var.AWS_SECRET_ACCESS_KEY
    aws_session_token     = var.AWS_SESSION_TOKEN
    db_url                = data.aws_ssm_parameter.db_url.value
    db_username           = local.db_credentials.username
    db_password           = local.db_credentials.password
  }

  type = "Opaque"

  depends_on = [kubernetes_namespace.order_namespace]
}

resource "kubernetes_deployment" "order_deployment" {
  metadata {
    name      = "tech-challenge-order-api"
    namespace = kubernetes_namespace.order_namespace.metadata[0].name
    labels = {
      app = "tech-challenge-order-api"
    }
  }

  spec {
    replicas = 1

    selector {
      match_labels = {
        app = "tech-challenge-order-api"
      }
    }

    template {
      metadata {
        labels = {
          app = "tech-challenge-order-api"
        }
      }

      spec {
        container {
          image             = data.aws_ecr_image.latest_image.image_uri
          name              = "tech-challenge-order-api"
          image_pull_policy = "Always"

          resources {
            limits = {
              cpu    = "500m"
              memory = "1Gi"
            }
            requests = {
              cpu    = "250m"
              memory = "512Mi"
            }
          }

          liveness_probe {
            http_get {
              path = "/order/actuator/health"
              port = var.server_port
            }
            initial_delay_seconds = 60
            period_seconds        = 30
            timeout_seconds       = 5
            failure_threshold     = 3
          }

          readiness_probe {
            http_get {
              path = "/order/actuator/health"
              port = var.server_port
            }
            initial_delay_seconds = 60
            period_seconds        = 10
            timeout_seconds       = 3
            failure_threshold     = 1
          }

          env {
            name  = "SPRING_PROFILES_ACTIVE"
            value = "default"
          }

          env {
            name = "SPRING_DATASOURCE_URL"
            value_from {
              secret_key_ref {
                name = "tech-challenge-order-secret"
                key  = "db_url"
              }
            }
          }

          env {
            name = "SPRING_DATASOURCE_USERNAME"
            value_from {
              secret_key_ref {
                name = "tech-challenge-order-secret"
                key  = "db_username"
              }
            }
          }

          env {
            name = "SPRING_DATASOURCE_PASSWORD"
            value_from {
              secret_key_ref {
                name = "tech-challenge-order-secret"
                key  = "db_password"
              }
            }
          }

          env {
            name = "AWS_ACCESS_KEY_ID"
            value_from {
              secret_key_ref {
                name = "tech-challenge-order-secret"
                key  = "aws_access_key_id"
              }
            }
          }

          env {
            name = "AWS_SECRET_ACCESS_KEY"
            value_from {
              secret_key_ref {
                name = "tech-challenge-order-secret"
                key  = "aws_secret_access_key"
              }
            }
          }

          env {
            name = "AWS_SESSION_TOKEN"
            value_from {
              secret_key_ref {
                name = "tech-challenge-order-secret"
                key  = "aws_session_token"
              }
            }
          }

          env {
            name  = "SQS_QUEUE_PRODUCT_CREATE_LISTENER"
            value = var.order_product_create_queue
          }

          env {
            name  = "SQS_QUEUE_PRODUCT_DELETE_LISTENER"
            value = var.order_product_delete_queue
          }

          env {
            name  = "SQS_QUEUE_PRODUCT_UPDATE_LISTENER"
            value = var.order_product_update_queue
          }

          env {
            name  = "SQS_QUEUE_ORDER_EVOLVE_LISTENER"
            value = var.order_status_update_queue
          }

          env {
            name  = "SQS_QUEUE_PAYMENT_CREATE_PRODUCER"
            value = data.aws_sqs_queue.payment_order_create_queue.url
          }

          env {
            name  = "SQS_QUEUE_COOK_CREATE_PRODUCER"
            value = data.aws_sqs_queue.cook_order_create_queue.url
          }

          env {
            name  = "SQS_QUEUE_PAID_EVOLVE_PRODUCER"
            value = data.aws_sqs_queue.order_status_update_queue.url
          }

          env {
            name  = "AWS_REGION"
            value = "us-east-1"
          }
        }
      }
    }
  }

  timeouts {
    create = "4m"
    update = "4m"
    delete = "4m"
  }

  depends_on = [kubernetes_secret.order_secret]
}

resource "kubernetes_service" "order_service" {
  metadata {
    name      = "tech-challenge-order-api-service"
    namespace = kubernetes_namespace.order_namespace.metadata[0].name
  }

  spec {
    selector = {
      app = "tech-challenge-order-api"
    }

    port {
      port        = var.server_port
      target_port = var.server_port
    }

    cluster_ip = "None"
  }
}

resource "kubernetes_ingress_v1" "order_ingress" {
  metadata {
    name      = "tech-challenge-order-api-ingress"
    namespace = kubernetes_namespace.order_namespace.metadata[0].name

    annotations = {
      "nginx.ingress.kubernetes.io/x-forwarded-port" = "true"
      "nginx.ingress.kubernetes.io/x-forwarded-host" = "true"
    }
  }

  spec {
    ingress_class_name = "nginx"

    rule {
      http {
        path {
          path      = "/order"
          path_type = "Prefix"

          backend {
            service {
              name = "tech-challenge-order-api-service"
              port {
                number = var.server_port
              }
            }
          }
        }
      }
    }
  }

  depends_on = [kubernetes_service.order_service]

}

resource "kubernetes_horizontal_pod_autoscaler_v2" "order_hpa" {
  metadata {
    name      = "tech-challenge-order-api-hpa"
    namespace = kubernetes_namespace.order_namespace.metadata[0].name
  }

  spec {
    scale_target_ref {
      api_version = "apps/v1"
      kind        = "Deployment"
      name        = "tech-challenge-order-api"
    }

    min_replicas = 1
    max_replicas = 5

    metric {
      type = "Resource"

      resource {
        name = "cpu"
        target {
          type                = "Utilization"
          average_utilization = 75
        }
      }
    }
  }

  depends_on = [kubernetes_service.order_service]

}
