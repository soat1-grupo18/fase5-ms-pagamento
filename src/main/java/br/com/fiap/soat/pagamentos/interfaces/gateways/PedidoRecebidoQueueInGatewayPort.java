package br.com.fiap.soat.pagamentos.interfaces.gateways;

import br.com.fiap.soat.pagamentos.gateways.requests.PedidoRecebidoRequest;
import org.springframework.messaging.Message;

public interface PedidoRecebidoQueueInGatewayPort {

    public void receber(PedidoRecebidoRequest pedidoRecebido);
}
