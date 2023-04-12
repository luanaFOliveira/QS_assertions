



@Test
public void testAdicionarProdutosAoEstoque() {
    // cria uma lista de produtos
    List<Produto> produtos = new ArrayList<>();
    Produto p1 = new Produto("001", "Camiseta", 50.0, 10);
    Produto p2 = new Produto("002", "Calça", 80.0, 5);
    Produto p3 = new Produto("003", "Tênis", 120.0, 2);
    produtos.add(p1);
    produtos.add(p2);
    produtos.add(p3);

    // cria uma loja com estoque máximo de 20 unidades
    Loja loja = new Loja("Minha Loja", 20);

    // adiciona os produtos ao estoque da loja
    EstoqueService estoqueService = new EstoqueService();
    estoqueService.adicionarProdutosAoEstoque(produtos, loja);

    // verifica se os produtos foram adicionados corretamente ao estoque
    assertEquals(10, loja.getQuantidadeDeProdutos(p1.getCodigo()));
    assertEquals(5, loja.getQuantidadeDeProdutos(p2.getCodigo()));
    assertEquals(2, loja.getQuantidadeDeProdutos(p3.getCodigo()));

    // verifica se as regras foram respeitadas
    assertTrue(loja.getQuantidadeDeProdutos() <= loja.getEstoqueMaximo(), "O estoque máximo foi ultrapassado");
    assertFalse(loja.getQuantidadeDeProdutos(p1.getCodigo()) < 0, "A quantidade de camisetas no estoque é negativa");
    assertFalse(loja.getQuantidadeDeProdutos(p2.getCodigo()) < 0, "A quantidade de calças no estoque é negativa");
    assertFalse(loja.getQuantidadeDeProdutos(p3.getCodigo()) < 0, "A quantidade de tênis no estoque é negativa");
    assertTrue(loja.getProdutos().contains(p1), "A camiseta não está cadastrada na loja");
    assertTrue(loja.getProdutos().contains(p2), "A calça não está cadastrada na loja");
    assertTrue(loja.getProdutos().contains(p3), "O tênis não está cadastrado na loja");
}
