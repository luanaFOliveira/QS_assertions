package org.example;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CarrinhoDeComprasTest {

    private static final double LIMITE_CREDITO = 550;
    private List<Produto> produtos;
    private CarrinhoDeCompras carrinho;

    /**
     * Inicializa a lista de produtos
     * Instancia um carrinho novo para cada teste
     */
    @BeforeEach
    public void initEach() {
        produtos = new ArrayList<>();

        int i = 0;
        var produto1 = new Produto(String.valueOf(++i), "Água de chuva", 50.0, 20);
        var produto2 = new Produto(String.valueOf(++i), "Água de coco", 10.0, 200);
        var produto3 = new Produto(String.valueOf(++i), "Suco de manga", 8.0, 8);
        var produto4 = new Produto(String.valueOf(++i), "Vinho de pêssego", 2.0, 31);
        produtos.addAll(List.of(produto1, produto2, produto3, produto4));

        carrinho = new CarrinhoDeCompras(LIMITE_CREDITO);
    }

    /**
     * Após efetivar uma compra, o valor dela é subtraído do limite de crédito disponível do cliente.
     */
    @Test
    public void limiteDeCompraDeveSerAtualizadoAposEfetivarCompra_assertEquals() {
        carrinho.adicionarItemCompra(new ItemCompra(produtos.get(0), 10));

        var valorComprado = carrinho.efetivarCompra(produtos, 0, 0);

        Assertions.assertEquals(carrinho.getLimiteDeCredito(), LIMITE_CREDITO - valorComprado);
    }

    @Test
    public void produtoDeveExistir_assertThrows() {
        carrinho.adicionarItemCompra(new ItemCompra(new Produto("999", "Produto inconsistente", 0.0, 999), 10));

        Assertions.assertThrows(RuntimeException.class, () -> carrinho.efetivarCompra(produtos, 0, 20));
    }

    /**
     * Ao tentar efetivar uma compra, verifica-se se o cliente possui limite de crédito suficiente
     * para comprar o montante desejado. Se não possui, uma exceção deve ser jogada.
     */
    @Test
    public void valorTotalDaCompraNaoDeveUltrapassarOLimiteDeCredito_assertThrows() {
        carrinho.adicionarItemCompra(new ItemCompra(produtos.get(0), 10));
        carrinho.adicionarItemCompra(new ItemCompra(produtos.get(1), 10));

        Assertions.assertThrows(RuntimeException.class, () -> carrinho.efetivarCompra(produtos, 0, 20));
    }

    /**
     * Ao tentar efetivar uma compra, verifica-se se o cliente possui limite de crédito suficiente
     * para comprar o montante desejado. Se possui, nenhuma exceção precisa ser jogada.
     */
    @Test
    public void valorTotalDaCompraNaoDeveUltrapassarOLimiteDeCredito_assertDoesNotThrow() {
        carrinho.adicionarItemCompra(new ItemCompra(produtos.get(0), 10));

        Assertions.assertDoesNotThrow(() -> carrinho.efetivarCompra(produtos, 0, 20));
    }

}
