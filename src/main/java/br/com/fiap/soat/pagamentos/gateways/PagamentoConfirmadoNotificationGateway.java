package br.com.fiap.soat.pagamentos.gateways;

import br.com.fiap.soat.pagamentos.entities.Pagamento;
import br.com.fiap.soat.pagamentos.gateways.requests.PagamentoConfirmadoRequest;
import br.com.fiap.soat.pagamentos.interfaces.gateways.PagamentoConfirmadoNotificationGatewayPort;
import io.awspring.cloud.sns.core.SnsTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PagamentoConfirmadoNotificationGateway implements PagamentoConfirmadoNotificationGatewayPort {

    @Value("${topic.name}")
    private String topicName;

    @Autowired
    private SnsTemplate snsTemplate;

    @Override
    public void enviar(Pagamento pagamento) {
        PagamentoConfirmadoRequest pagamentoConfirmadoRequest = PagamentoConfirmadoRequest.fromDomain(pagamento);
        String status = pagamento.getStatus().toString().toLowerCase();
        snsTemplate.convertAndSend(topicName, pagamentoConfirmadoRequest, Map.of("event-type", status));
    }
}
