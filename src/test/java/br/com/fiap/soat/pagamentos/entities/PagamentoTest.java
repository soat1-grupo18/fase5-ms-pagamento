package br.com.fiap.soat.pagamentos.entities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PagamentoTest {
    @Test
    void toDomain() {
        var clienteId = UUID.randomUUID().toString();
        Pagamento pagamento = new Pagamento(
                "1",
                "Pedido123",
                new BigDecimal("70.50"),
                Status.APROVADO,
                "2024-01-28T10:30:00Z",
                clienteId
        );

        Pagamento result = pagamento.toDomain();

        assertEquals("1", result.getId());
        assertEquals("Pedido123", result.getPedidoId());
        assertEquals(new BigDecimal("70.50"), result.getTotal());
        assertEquals(Status.APROVADO, result.getStatus());
        assertEquals("2024-01-28T10:30:00Z", result.getDataDeCriacao());
        assertEquals(clienteId, result.getClienteId());
    }

    @Test
    void buscarStatus() {
        Pagamento pagamento = new Pagamento(
                "1",
                "Pedido123",
                new BigDecimal("70.50"),
                Status.APROVADO,
                "2024-01-28T10:30:00Z",
                UUID.randomUUID().toString()
        );

        Status result = pagamento.buscarStatus();
        assertEquals(Status.APROVADO, result);
    }

    @Test
    void buscarPedidoId() {
        Pagamento pagamento = new Pagamento(
                "1",
                "Pedido123",
                new BigDecimal("70.50"),
                Status.APROVADO,
                "2024-01-28T10:30:00Z",
                UUID.randomUUID().toString()
        );

        String result = pagamento.buscarPedidoId();
        assertEquals("Pedido123", result);
    }
}
