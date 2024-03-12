package br.com.fiap.soat.pagamentos.interfaces.gateways;

import br.com.fiap.soat.pagamentos.entities.Pagamento;

public interface PagamentoConfirmadoQueueOutGatewayPort {
    void enviar(Pagamento pagamento);
}
