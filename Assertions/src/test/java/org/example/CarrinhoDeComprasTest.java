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

     /**
     * Ao tentar efetivar uma compra, verifica-se se os valores retornados estão de acordo com os testes
     * Se sim, nenhuma exceção precisa ser jogada.
    */
    @Test
    public void testCalcularValorTotalCompra() {
        final var limiteCredito = 550;

        var carrinhoDeCompras = new CarrinhoDeCompras(limiteCredito);
        // Adiciona dois produtos ao carrinho de compras
        carrinhoDeCompras.adicionarItemCompra(new ItemCompra(produtos.get(0), 2));
        carrinhoDeCompras.adicionarItemCompra(new ItemCompra(produtos.get(1), 1));
        

        // Calcula o valor total da compra com desconto de R$ 10,00 e frete de R$ 20,00
        double valorTotalCompra = carrinhoDeCompras.efetivarCompra(produtos, 10.0, 20.0);

        // Verifica se o valor total da compra está correto
        Assertions.assertAll("valorTotalCompra",
                () -> Assertions.assertEquals(120.0, valorTotalCompra),
                () -> Assertions.assertEquals(120.0, carrinhoDeCompras.getValorTotalCompra()),
                () -> Assertions.assertEquals(18, produtos.get(0).getQuantidade()),
                () -> Assertions.assertEquals(199, produtos.get(1).getQuantidade()),
                () -> Assertions.assertEquals(8, produtos.get(2).getQuantidade())
        );
    }

}
