import java.util.ArrayList;
import java.util.List;

public class Produto {

    private String codigo;
    private String nome;
    private double preco;
    private int quantidade;

    public Produto(String codigo, String nome, double preco, int quantidade) {
        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}


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

    public double calcularValorTotalCompra(List<Produto> produtos, double valorDesconto, double valorFrete) {
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

public class ItemCompra {

    private Produto produto;
    private int quantidade;

    public ItemCompra(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
