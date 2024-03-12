package br.com.fiap.soat.pagamentos.usecases;

import br.com.fiap.soat.pagamentos.entities.Pagamento;
import br.com.fiap.soat.pagamentos.entities.Status;
import br.com.fiap.soat.pagamentos.exceptions.ConfirmacaoDePagamentoInvalidaException;
import br.com.fiap.soat.pagamentos.interfaces.gateways.PagamentoConfirmadoQueueOutGatewayPort;
import br.com.fiap.soat.pagamentos.interfaces.usecases.ReceberConfirmacaoPagamentoUseCasePort;
import br.com.fiap.soat.pagamentos.dynamodb.entities.PagamentoDynamoEntity;
import br.com.fiap.soat.pagamentos.interfaces.gateways.PagamentosGatewayPort;
import br.com.fiap.soat.pagamentos.usecases.model.ComandoDeConfirmacaoDePagamento;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ReceberConfirmacaoPagamentoUseCase implements ReceberConfirmacaoPagamentoUseCasePort {
    private final PagamentosGatewayPort pagamentoGateway;
    private final PagamentoConfirmadoQueueOutGatewayPort pagamentoConfirmadoQueueOutGateway;

    public ReceberConfirmacaoPagamentoUseCase(PagamentosGatewayPort pagamentoGateway,
                                              PagamentoConfirmadoQueueOutGatewayPort pagamentoConfirmadoQueueOutGateway) {
        this.pagamentoGateway = pagamentoGateway;
        this.pagamentoConfirmadoQueueOutGateway = pagamentoConfirmadoQueueOutGateway;
    }

    @Transactional
    public String execute(ComandoDeConfirmacaoDePagamento comandoDeConfirmacaoDePagamento) {
        String action = comandoDeConfirmacaoDePagamento.getAction();
        List<String> validActions = Arrays.asList("payment.created", "payment.denied");
        if (!validActions.contains(action)) {
            throw ConfirmacaoDePagamentoInvalidaException.aPartirDaAction(action);
        }

        String id = comandoDeConfirmacaoDePagamento.getPagamentoId();

        Optional<Pagamento> optPagamento = pagamentoGateway.obterPagamentoPorId(id);

        if (optPagamento.isPresent()) {
            Pagamento pagamento = optPagamento.get();
            if (action.equals("payment.denied")) {
                pagamento.setStatus(Status.RECUSADO);
            } else {
                pagamento.setStatus(Status.APROVADO);
            }
            pagamentoGateway.confirmarPagamento(pagamento);
            pagamentoConfirmadoQueueOutGateway.enviar(pagamento);
            return String.format("Pagamento %s %s", id, pagamento.getStatus().toString());
        } else {
            throw new RuntimeException("Pagamento não foi encontrado.");
        }
    }
}
