resource "kubernetes_config_map" "application_config" {
  metadata {
    name      = "application-config"
    namespace = "ms-pagamento"
  }

  data = {
    aws_dynamodb_endpoint    = "https://dynamodb.${data.aws_region.current.name}.amazonaws.com"
    aws_region               = data.aws_region.current.name
    aws_sns_endpoint         = "https://sns.${data.aws_region.current.name}.amazonaws.com"
    aws_sns_ms_pagamento_arn = aws_sns_topic.ms_pagamento.arn
    aws_sqs_endpoint         = "https://sqs.${data.aws_region.current.name}.amazonaws.com"
  }

  depends_on = [kubernetes_namespace.ms_pagamento]
}
