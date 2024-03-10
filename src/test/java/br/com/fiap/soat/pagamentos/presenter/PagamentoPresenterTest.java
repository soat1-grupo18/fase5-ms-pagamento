package br.com.fiap.soat.pagamentos.presenter;

import br.com.fiap.soat.pagamentos.entities.Pagamento;
import br.com.fiap.soat.pagamentos.entities.Status;
import br.com.fiap.soat.pagamentos.presenters.PagamentoPresenter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PagamentoPresenterTest {
    @Test
    void fromDomain() {
        // Given
        String clienteId = UUID.randomUUID().toString();
        Pagamento pagamento = new Pagamento(
                "1",
                "Pedido123",
                new BigDecimal("70.50"),
                Status.APROVADO,
                "2024-01-28T10:30:00Z",
                clienteId
        );

        PagamentoPresenter pagamentoPresenter = PagamentoPresenter.fromDomain(pagamento);

        assertEquals("1", pagamentoPresenter.getId());
        assertEquals("Pedido123", pagamentoPresenter.getPedidoId());
        assertEquals(new BigDecimal("70.50"), pagamentoPresenter.getTotal());
        assertEquals(Status.APROVADO, pagamentoPresenter.getStatus());
        assertEquals("2024-01-28T10:30:00Z", pagamentoPresenter.getDataDeCriacao());
        assertEquals(clienteId, pagamentoPresenter.getClienteId());
    }
}
