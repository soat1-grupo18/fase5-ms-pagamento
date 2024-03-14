package br.com.fiap.soat.pagamentos.interfaces.gateways;

import br.com.fiap.soat.pagamentos.entities.Pagamento;

public interface PagamentoConfirmadoNotificationGatewayPort {
    void enviar(Pagamento pagamento);
}
