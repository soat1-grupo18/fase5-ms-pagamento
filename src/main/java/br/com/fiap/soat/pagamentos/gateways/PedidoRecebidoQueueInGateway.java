package br.com.fiap.soat.pagamentos.gateways;

import br.com.fiap.soat.pagamentos.entities.Pagamento;
import br.com.fiap.soat.pagamentos.gateways.requests.PedidoRecebidoRequest;
import br.com.fiap.soat.pagamentos.interfaces.gateways.PedidoRecebidoQueueInGatewayPort;
import br.com.fiap.soat.pagamentos.interfaces.usecases.CriarPagamentoUseCasePort;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class PedidoRecebidoQueueInGateway implements PedidoRecebidoQueueInGatewayPort {

    private final CriarPagamentoUseCasePort criarPagamentoUseCase;

    public PedidoRecebidoQueueInGateway(CriarPagamentoUseCasePort criarPagamentoUseCase) {
        this.criarPagamentoUseCase = criarPagamentoUseCase;
    }

    private static final Logger logger = LoggerFactory.getLogger(PedidoRecebidoQueueInGateway.class);

    @Override
    @SqsListener("${queue.name.pedido-recebido}")
    public void receber(PedidoRecebidoRequest pedidoRecebido) {
        try {
            logger.info("Preço do pedido recebido: {}", pedidoRecebido.getPreco());
            Pagamento pagamento = pedidoRecebido.toDomain();
            Pagamento pagamentoCriado = criarPagamentoUseCase.execute(pagamento);
            logger.info("Pagamento criado: {}", pagamentoCriado.getId());
        } catch (Exception e) {
            logger.error("Error while deserializing message: {}", e.getMessage());
        }
    }
}
