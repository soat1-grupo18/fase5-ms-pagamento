package br.com.fiap.soat.pagamentos.usecases;

import br.com.fiap.soat.pagamentos.entities.Pagamento;
import br.com.fiap.soat.pagamentos.entities.Status;
import br.com.fiap.soat.pagamentos.exceptions.ConfirmacaoDePagamentoInvalidaException;
import br.com.fiap.soat.pagamentos.interfaces.gateways.PagamentoConfirmadoQueueOutGatewayPort;
import br.com.fiap.soat.pagamentos.interfaces.gateways.PagamentosGatewayPort;
import br.com.fiap.soat.pagamentos.usecases.model.ComandoDeConfirmacaoDePagamento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReceberConfirmacaoPagamentoUseCaseTest {

    @Mock
    private PagamentosGatewayPort pagamentoGateway;

    @Mock
    private PagamentoConfirmadoQueueOutGatewayPort pagamentoConfirmadoQueueOutGateway;

    private ReceberConfirmacaoPagamentoUseCase receberConfirmacaoPagamentoUseCase;

    @BeforeEach
    void initUseCase() {
        receberConfirmacaoPagamentoUseCase = new ReceberConfirmacaoPagamentoUseCase(pagamentoGateway, pagamentoConfirmadoQueueOutGateway);
    }

    @Test
    void confirmarPagamentoAprovado() {
        var pagamentoId = UUID.randomUUID().toString();
        var comando = new ComandoDeConfirmacaoDePagamento("payment.created", pagamentoId);
        var pagamento = new Pagamento(pagamentoId, UUID.randomUUID().toString(), BigDecimal.valueOf(15), Status.PENDENTE, LocalDateTime.now().toString(), UUID.randomUUID().toString());
        when(pagamentoGateway.obterPagamentoPorId(pagamentoId)).thenReturn(Optional.of(pagamento));
        var result = receberConfirmacaoPagamentoUseCase.execute(comando);
        assertEquals(result, String.format("Pagamento %s APROVADO", pagamentoId));
        verify(pagamentoGateway, times(1)).obterPagamentoPorId(pagamentoId);
    }

    @Test
    void confirmarPagamentoRecusado() {
        var pagamentoId = UUID.randomUUID().toString();
        var comando = new ComandoDeConfirmacaoDePagamento("payment.denied", pagamentoId);
        var pagamento = new Pagamento(pagamentoId, UUID.randomUUID().toString(), BigDecimal.valueOf(15), Status.PENDENTE, LocalDateTime.now().toString(), UUID.randomUUID().toString());
        when(pagamentoGateway.obterPagamentoPorId(pagamentoId)).thenReturn(Optional.of(pagamento));
        var result = receberConfirmacaoPagamentoUseCase.execute(comando);
        assertEquals(result, String.format("Pagamento %s RECUSADO", pagamentoId));
        verify(pagamentoGateway, times(1)).obterPagamentoPorId(pagamentoId);
    }

    @Test
    void confirmacaoDePagamentoInvalidaAPartirDaAction() {
        var pagamentoId = UUID.randomUUID().toString();
        var comando = new ComandoDeConfirmacaoDePagamento("invalid.action", pagamentoId);
        assertThrows(ConfirmacaoDePagamentoInvalidaException.class, () -> receberConfirmacaoPagamentoUseCase.execute(comando));
    }

    @Test
    void pagamentoNaoEncontrado() {
        var pagamentoId = UUID.randomUUID().toString();
        var comando = new ComandoDeConfirmacaoDePagamento("payment.created", pagamentoId);
        when(pagamentoGateway.obterPagamentoPorId(pagamentoId)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> receberConfirmacaoPagamentoUseCase.execute(comando));
    }
}