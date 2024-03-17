data "aws_sns_topic" "ms_pedido" {
  name = "ms-pedido"
}

resource "aws_sns_topic" "ms_pagamento" {
  name = "ms-pagamento"
}
