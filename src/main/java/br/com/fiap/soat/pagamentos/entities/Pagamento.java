package br.com.fiap.soat.pagamentos.entities;

import java.math.BigDecimal;

public class Pagamento {
    private String id;
    private String pedidoId;
    private BigDecimal total;
    private Status status;
    private String dataDeCriacao;

    private String clienteId;


    public Status buscarStatus() {
        return status;
    }

    public String buscarPedidoId() {
        return pedidoId;
    }

    public Pagamento(
            String id,
            String pedidoId,
            BigDecimal total,
            Status status,
            String dataDeCriacao,
            String clienteId
    ) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.total = total;
        this.status = status;
        this.dataDeCriacao = dataDeCriacao;
        this.clienteId = clienteId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(String pedidoId) {
        this.pedidoId = pedidoId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public String getDataDeCriacao() {
        return dataDeCriacao;
    }

    public void setDataDeCriacao(String dataDeCriacao) {
        this.dataDeCriacao = dataDeCriacao;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public Pagamento toDomain() {
        return new Pagamento(
                id,
                pedidoId,
                total,
                status,
                dataDeCriacao,
                clienteId
        );
    }
}
