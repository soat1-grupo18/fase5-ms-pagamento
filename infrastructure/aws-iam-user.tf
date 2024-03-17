// Bad practice. It should be an IAM Role.
resource "aws_iam_user" "ms_pagamento" {
  name = "ms-pagamento"
  path = "/fiap-store/"
}

resource "aws_iam_access_key" "ms_pagamento" {
  user = aws_iam_user.ms_pagamento.name
}

data "aws_iam_policy_document" "ms_pagamento" {
  statement {
    effect = "Allow"
    actions = [
      "dynamodb:DescribeTable",
      "dynamodb:GetItem",
      "dynamodb:PutItem",
      "dynamodb:Query",
      "dynamodb:Scan", # Bad design. Scan is slow and lead to high costs.
      "dynamodb:UpdateItem",
    ]
    resources = [
      aws_dynamodb_table.pagamentos.arn,
      "${aws_dynamodb_table.pagamentos.arn}/*"
    ]
  }

  statement {
    effect = "Allow"
    actions = [
      "sns:GetTopicAttributes",
      "sns:Publish",
    ]
    resources = [
      aws_sns_topic.ms_pagamento.arn
    ]
  }

  statement {
    effect = "Allow"
    actions = [
      "sqs:ChangeMessageVisibility",
      "sqs:DeleteMessage",
      "sqs:GetQueueAttributes",
      "sqs:GetQueueUrl",
      "sqs:ReceiveMessage",
      "sqs:SendMessage",
    ]
    resources = [
      aws_sqs_queue.ms_pagamento_evento_pedido_recebido.arn
    ]
  }
}

resource "aws_iam_user_policy" "ms_pagamento" {
  name   = "application"
  user   = aws_iam_user.ms_pagamento.name
  policy = data.aws_iam_policy_document.ms_pagamento.json
}
