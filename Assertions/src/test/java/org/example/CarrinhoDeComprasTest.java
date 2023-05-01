package org.example;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
     * Ao efetivar uma compra, o valor do frete é somado ao valor do total e o desconto subtraído.
     */
    @Test
    public void valorDaCompraDeveSomarFreteESubtrairDesconto_assertEquals() {
        carrinho.adicionarItemCompra(new ItemCompra(produtos.get(0), 10));

        final var valorDesconto = 10.0;
        final var valorFrete = 20.0;

        var valorComprado = carrinho.efetivarCompra(produtos, valorDesconto, valorFrete);

        // Asserções podem ser importadas para facilitar a chamada
        assertEquals(510, valorComprado);
    }

    /**
     * Após efetivar uma compra, o valor dela é subtraído do limite de crédito disponível do cliente.
     */
    @Test
    public void limiteDeCompraDeveSerAtualizadoAposEfetivarCompra_assertEquals() {
        carrinho.adicionarItemCompra(new ItemCompra(produtos.get(0), 10));

        final var valorDesconto = 10.0;
        final var valorFrete = 20.0;

        var valorComprado = carrinho.efetivarCompra(produtos, valorDesconto, valorFrete);

        // Asserções podem ser importadas para facilitar a chamada
        assertEquals(LIMITE_CREDITO - valorComprado, carrinho.getLimiteDeCredito());
    }

    /**
     * Produtos que não estão na lista de produtos enviada ao carrinho de compras não "existem", e por isso
     * fazem uma exceção ser jogada.
     */
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
        // Adiciona dois produtos ao carrinho de compras
        carrinho.adicionarItemCompra(new ItemCompra(produtos.get(0), 2));
        carrinho.adicionarItemCompra(new ItemCompra(produtos.get(1), 1));

        // Calcula o valor total da compra com desconto de R$ 10,00 e frete de R$ 20,00
        double valorTotalCompra = carrinho.efetivarCompra(produtos, 10.0, 20.0);

        // Verifica se o valor total da compra está correto
        Assertions.assertAll("valorTotalCompra",
                () -> Assertions.assertEquals(120.0, valorTotalCompra),
                () -> Assertions.assertEquals(120.0, carrinho.getValorTotalCompra()),
                () -> Assertions.assertEquals(18, produtos.get(0).getQuantidade()),
                () -> Assertions.assertEquals(199, produtos.get(1).getQuantidade()),
                () -> Assertions.assertEquals(8, produtos.get(2).getQuantidade())
        );
    }

    /**
     * Ao limpar o carrinho, verifica-se se o valor total das compras também zerou
     */
    @Test
    public void valorTotaldaCompraDeveSerZeroSeCarrinhoVazio_assertIterableEquals() {
        final List<Produto> carrinhoVazio = new ArrayList<>();

        var carrinhoItens = carrinho.getItensCompra();

        Assertions.assertIterableEquals(carrinhoVazio, carrinhoItens);
        Assertions.assertEquals(0, carrinho.getValorTotalCompra());
    }

    /**
     * Certifica-se de que o valor total dos vinhos em estoque é maior ou igual que o valor total de vinhos no carrinho
     */
    @Test
    public void valorEstoqueProdutoDeveSerMaiorOuIgualAoValorComprado_assertTrue() {
        var valorTotalEstoqueVinho = produtos.get(3).getPreco() * produtos.get(3).getQuantidade();

        carrinho.adicionarItemCompra(new ItemCompra(produtos.get(3), 20));

        Assertions.assertTrue(valorTotalEstoqueVinho >= carrinho.getValorTotalProduto(produtos.get(3).getCodigo()));
    }

    /**
     * Verifica se a quantidade de produtos no carrinho eh maior que a quantidade de produtos em estoque, o que nao pode ser verdade
     */
    @Test
    public void quantidadeEstoqueDeveSerAtualizadaAposCompra_assertFalse() {
        var quantidadeEstoque = produtos.get(1).getQuantidade();

        carrinho.adicionarItemCompra(new ItemCompra(produtos.get(1), 50));
        carrinho.efetivarCompra(produtos, 0, 0);

        Assertions.assertFalse(produtos.get(1).getQuantidade() > quantidadeEstoque);
    }

    /**
     * Certifica-se de que caso o codigo do produto seja valido, um objeto é retornado
     */
    @Test
    public void encontrarProdutoDeveRetornarObjetoSeCodigoExistente_assertNotNull() {
        carrinho.adicionarItemCompra(new ItemCompra(produtos.get(1), 1));
        Assertions.assertNotNull(carrinho.encontrarProdutoPorCodigo(produtos, "1"));
    }

    /**
     * Certifica-se de que caso o codigo do produto nao seja valido, null é retornado
     */
    @Test
    public void encontrarProdutoDeveRetornarNullSeCodigoNaoExistente_assertNull() {
        Assertions.assertNull(carrinho.encontrarProdutoPorCodigo(produtos, "10"));
    }

    /**
     * Verifica se dois produtos são iguais
     */
    @Test
    public void produtosNaoSaoIguais_assertNotSame() {
        var p1 = new Produto("4", "Energetico", 9.0, 100);
        var p2 = new Produto("4", "Energetico", 8.99, 100);

        Assertions.assertNotSame(p1, p2);
    }

}
