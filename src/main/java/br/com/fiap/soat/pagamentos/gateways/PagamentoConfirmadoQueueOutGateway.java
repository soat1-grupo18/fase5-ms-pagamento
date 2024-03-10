package br.com.fiap.soat.pagamentos.gateways;

import br.com.fiap.soat.pagamentos.entities.Pagamento;
import br.com.fiap.soat.pagamentos.entities.Status;
import br.com.fiap.soat.pagamentos.gateways.requests.PagamentoConfirmadoRequest;
import br.com.fiap.soat.pagamentos.interfaces.gateways.PagamentoConfirmadoQueueOutGatewayPort;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Component
public class PagamentoConfirmadoQueueOutGateway implements PagamentoConfirmadoQueueOutGatewayPort {

    @Value("${queue.name.pagamento-aprovado}")
    private String queuePagamentoAprovado;

    @Value("${queue.name.pagamento-recusado}")
    private String queuePagamentoRecusado;

    @Autowired
    private SqsAsyncClient sqsAsyncClient;

    @Override
    public void enviar(Pagamento pagamento) {
        SqsTemplate template = SqsTemplate.builder().sqsAsyncClient(this.sqsAsyncClient)
                .configureDefaultConverter(converter -> converter.setPayloadTypeHeaderValueFunction(msg -> null))
                .build();

        var pagamentoRequest = PagamentoConfirmadoRequest.fromDomain(pagamento);
        String queueName = pagamento.getStatus() == Status.APROVADO ? queuePagamentoAprovado : queuePagamentoRecusado;
        template.send(to -> to.queue(queueName).payload(pagamentoRequest));
    }
}
