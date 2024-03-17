resource "aws_sqs_queue" "ms_pagamento_evento_pedido_recebido" {
  name = "ms-pagamento-evento-pedido-recebido"

  delay_seconds              = 0
  max_message_size           = 262144
  message_retention_seconds  = 345600
  receive_wait_time_seconds  = 20
  visibility_timeout_seconds = 60

  policy = data.aws_iam_policy_document.sqs_policy.json

  redrive_policy = jsonencode({
    deadLetterTargetArn = aws_sqs_queue.ms_pagamento_evento_pedido_recebido_dlq.arn
    maxReceiveCount     = 3
  })
}

resource "aws_sqs_queue" "ms_pagamento_evento_pedido_recebido_dlq" {
  name = "ms-pagamento-evento-pedido-recebido-dlq"

  delay_seconds              = 0
  max_message_size           = 262144
  message_retention_seconds  = 345600
  receive_wait_time_seconds  = 20
  visibility_timeout_seconds = 60

  policy = data.aws_iam_policy_document.sqs_policy.json
}

resource "aws_sns_topic_subscription" "ms_pagamento_evento_pedido_recebido" {
  topic_arn            = data.aws_sns_topic.ms_pedido.arn
  protocol             = "sqs"
  endpoint             = aws_sqs_queue.ms_pagamento_evento_pedido_recebido.arn
  raw_message_delivery = true
}

data "aws_iam_policy_document" "sqs_policy" {
  # docs: https://docs.aws.amazon.com/sns/latest/dg/subscribe-sqs-queue-to-sns-topic.html
  statement {
    effect = "Allow"

    principals {
      type = "Service"
      identifiers = [
        "sns.amazonaws.com"
      ]
    }

    actions = [
      "sqs:SendMessage"
    ]

    resources = [
      "*"
    ]

    condition {
      test     = "StringEquals"
      variable = "AWS:SourceAccount"
      values = [
        data.aws_caller_identity.current.account_id
      ]
    }
  }
}
