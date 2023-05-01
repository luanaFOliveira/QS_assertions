package org.example;

import java.util.List;

public class Main {

    private static final double LIMITE_CREDITO = 2000;

    public static void main(String[] args) {
        var carrinho = new CarrinhoDeCompras(LIMITE_CREDITO);

        int i = 0;
        var produto1 = new Produto(String.valueOf(++i), "Água de chuva", 50.0, 20);
        var produto2 = new Produto(String.valueOf(++i), "Água de coco", 10.0, 200);
        var produto3 = new Produto(String.valueOf(++i), "Suco de manga", 8.0, 8);
        var produto4 = new Produto(String.valueOf(++i), "Vinho de pêssego", 2.0, 31);
        List<Produto> produtos = List.of(produto1, produto2, produto3, produto4);

        carrinho.adicionarItemCompra(new ItemCompra(produto1, 2));
        carrinho.adicionarItemCompra(new ItemCompra(produto4, 20));

        carrinho.efetivarCompra(produtos, 0, 20);

    }
}
