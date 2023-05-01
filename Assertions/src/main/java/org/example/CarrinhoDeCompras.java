package org.example;

import java.util.ArrayList;
import java.util.List;

public class CarrinhoDeCompras {

    private List<ItemCompra> itensCompra = new ArrayList<>();
    private double limiteDeCredito;
    private double valorTotalCompra;

    public CarrinhoDeCompras(double limiteDeCredito) {
        this.limiteDeCredito = limiteDeCredito;
    }

    public double getLimiteDeCredito() {
        return limiteDeCredito;
    }

    public double getValorTotalCompra() {
        return valorTotalCompra;
    }

    public void adicionarItemCompra(ItemCompra itemCompra) {
        itensCompra.add(itemCompra);
    }

    public void removerItemCompra(ItemCompra itemCompra) {
        itensCompra.remove(itemCompra);
    }

    public double efetivarCompra(List<Produto> produtos, double valorDesconto, double valorFrete) {
        double valorTotal = 0.0;
        for (ItemCompra itemCompra : itensCompra) {
            Produto produto = encontrarProdutoPorCodigo(produtos, itemCompra.getProduto().getCodigo());
            if (produto != null) {
                if (itemCompra.getQuantidade() <= produto.getQuantidade()) {
                    produto.setQuantidade(produto.getQuantidade() - itemCompra.getQuantidade());
                    valorTotal += itemCompra.getQuantidade() * produto.getPreco();
                } else {
                    throw new RuntimeException("Quantidade de produto em estoque insuficiente");
                }
            } else {
                throw new RuntimeException("Produto não encontrado");
            }
        }
        valorTotal = valorTotal - valorDesconto + valorFrete;

        if (valorTotal > limiteDeCredito) {
            throw new RuntimeException("Limite de crédito excedido");
        }

        valorTotalCompra = valorTotal;
        return valorTotal;
    }

    public double getValorTotalProduto(String codigoProduto) {
        double valorTotal = 0.0;
        for (ItemCompra itemCompra : itensCompra) {
            if (itemCompra.getProduto().getCodigo().equals(codigoProduto)) {
                valorTotal += itemCompra.getQuantidade() * itemCompra.getProduto().getPreco();
            }
        }
        return valorTotal;
    }

    private Produto encontrarProdutoPorCodigo(List<Produto> produtos, String codigo) {
        for (Produto produto : produtos) {
            if (produto.getCodigo().equals(codigo)) {
                return produto;
            }
        }
        return null;
    }
}
