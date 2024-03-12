package br.com.fiap.soat.pagamentos.gateways.requests;

import br.com.fiap.soat.pagamentos.entities.Pagamento;
import br.com.fiap.soat.pagamentos.entities.Status;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class PedidoRecebidoRequest {
    private UUID pedidoId;
    private UUID clienteId;
    private BigDecimal preco;
    private List<ItemDoPedidoRecebidoRequest> itens;

    public UUID getPedidoId() {
        return pedidoId;
    }

    public UUID getClienteId() {
        return clienteId;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public List<ItemDoPedidoRecebidoRequest> getItens() {
        return itens;
    }

    public Pagamento toDomain() {
        Instant currentInstant = Instant.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String dataDeCriacao = formatter.format(currentInstant.atOffset(ZoneOffset.UTC));

        return new Pagamento(
                null,
                pedidoId.toString(),
                preco,
                Status.PENDENTE,
                dataDeCriacao,
                clienteId.toString()
        );
    }
}
