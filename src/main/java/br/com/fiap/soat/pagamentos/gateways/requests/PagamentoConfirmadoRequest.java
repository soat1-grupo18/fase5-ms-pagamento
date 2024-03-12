package br.com.fiap.soat.pagamentos.gateways.requests;

import br.com.fiap.soat.pagamentos.entities.Pagamento;

import java.math.BigDecimal;

public class PagamentoConfirmadoRequest {

    private String pagamentoId;
    private String pedidoId;
    private String clienteId;
    private BigDecimal total;
    private String status;

    public static PagamentoConfirmadoRequest fromDomain(Pagamento pagamento) {
        var pagamentoConfirmadoRequest = new PagamentoConfirmadoRequest();

        pagamentoConfirmadoRequest.pagamentoId = pagamento.getId();
        pagamentoConfirmadoRequest.pedidoId = pagamento.getPedidoId();
        pagamentoConfirmadoRequest.clienteId = pagamento.getClienteId();
        pagamentoConfirmadoRequest.total = pagamento.getTotal();
        pagamentoConfirmadoRequest.status = pagamento.getStatus().name();

        return pagamentoConfirmadoRequest;
    }

    public String getPagamentoId() {
        return pagamentoId;
    }

    public String getPedidoId() {
        return pedidoId;
    }

    public String getClienteId() {
        return clienteId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }
}
