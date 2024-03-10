package br.com.fiap.soat.pagamentos.presenters;
import br.com.fiap.soat.pagamentos.entities.Pagamento;
import br.com.fiap.soat.pagamentos.entities.Status;

import java.math.BigDecimal;

public class PagamentoPresenter {
    private String id;
    private String pedidoId;
    private Status status;
    private BigDecimal total;
    private String dataDeCriacao;

    private String clienteId;

    public PagamentoPresenter(String id, String pedidoId, Status status, BigDecimal total, String dataDeCriacao, String clienteId) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.status = status;
        this.total = total;
        this.dataDeCriacao = dataDeCriacao;
        this.clienteId = clienteId;
    }

    public static PagamentoPresenter fromDomain(Pagamento pagamento) {
        return new PagamentoPresenter(
                pagamento.getId(),
                pagamento.getPedidoId(),
                pagamento.getStatus(),
                pagamento.getTotal(),
                pagamento.getDataDeCriacao(),
                pagamento.getClienteId()
        );
    }

    public String getId() {
        return id;
    }
    public String getPedidoId() {
        return pedidoId;
    }
    public BigDecimal getTotal() {
        return total;
    }
    public Status getStatus() {
        return status;
    }
    public String getDataDeCriacao() {
        return dataDeCriacao;
    }
    public String getClienteId() {
        return clienteId;
    }
}
