  @Test
    public void valorDaCompraDeveSomarFreteESubtrairDesconto_assertEquals() {
        CarrinhoDeCompras carrinho = new CarrinhoDeCompras();
        carrinho.setLimiteDeCredito(1000.0);
        carrinho.adicionarItem(new ItemCompra(new Produto("1", "produto1", 10.0, 5), 2));
        carrinho.adicionarItem(new ItemCompra(new Produto("2", "produto2", 20.0, 5), 1));

        double frete = 20.0;
        double desconto = 5.0;
        double valorTotalCompra = carrinho.calcularValorTotalCompra(frete, desconto);

        double valorEsperado = (2 * 10.0 + 1 * 20.0) + frete - desconto;
        assertEquals(valorEsperado, valorTotalCompra, 0.001);
    }

    @Test
    public void limiteDeCompraDeveSerAtualizadoAposEfetivarCompra_assertEquals() {
        CarrinhoDeCompras carrinho = new CarrinhoDeCompras();
        carrinho.setLimiteDeCredito(1000.0);
        carrinho.adicionarItem(new ItemCompra(new Produto("1", "produto1", 10.0, 5), 2));
        carrinho.adicionarItem(new ItemCompra(new Produto("2", "produto2", 20.0, 5), 1));

        double frete = 20.0;
        double desconto = 5.0;
        double valorTotalCompra = carrinho.calcularValorTotalCompra(frete, desconto);
        carrinho.efetivarCompra(valorTotalCompra);

        double limiteDeCreditoEsperado = 1000.0 - valorTotalCompra;
        assertEquals(limiteDeCreditoEsperado, carrinho.getLimiteDeCredito(), 0.001);
    }

    @Test
    public void produtoDeveExistir_assertThrows() {
        CarrinhoDeCompras carrinho = new CarrinhoDeCompras();
        carrinho.adicionarItem(new ItemCompra(new Produto("1", "produto1", 10.0, 5), 2));

        assertThrows(ProdutoNaoEncontradoException.class, () -> carrinho.adicionarItem(new ItemCompra(new Produto("2", "produto2", 20.0, 5), 1)));
    }

    @Test
    public void valorTotalDaCompraNaoDeveUltrapassarOLimiteDeCredito_assertThrows() {
        CarrinhoDeCompras carrinho = new CarrinhoDeCompras();
        carrinho.setLimiteDeCredito(100.0);
        carrinho.adicionarItem(new ItemCompra(new Produto("1", "produto1", 10.0, 5), 2));
        carrinho.adicionarItem(new ItemCompra(new Produto("2", "produto2", 20.0, 5), 1));

        double frete = 20.0;
        double desconto = 5.0;
        double valorTotalCompra = carrinho.calcularValorTotalCompra(frete, desconto);

        assertThrows(LimiteDeCreditoExcedidoException.class, () -> carrinho.efetivarCompra(valorTotalCompra));
    }

    @Test
    public void valorTotalDaCompraNaoDeveUltrapassarOLimiteDeCredito_assertDoesNotThrow() {
        Produto produto1 = new Produto("1", "Cerveja", 2.5, 10);
        Produto produto2 = new Produto("2", "Vinho", 50.0, 5);
        ItemCompra itemCompra1 = new ItemCompra(produto1, 3);
        ItemCompra itemCompra2 = new ItemCompra(produto2, 2);
        carrinho.addItem(itemCompra1);
        carrinho.addItem(itemCompra2);
        Assertions.assertDoesNotThrow(() -> carrinho.efetivarCompra());
    }

    @Test
    public void testCalcularValorTotalCompra() {
        Produto produto1 = new Produto("1", "Cerveja", 2.5, 10);
        Produto produto2 = new Produto("2", "Vinho", 50.0, 5);
        ItemCompra itemCompra1 = new ItemCompra(produto1, 3);
        ItemCompra itemCompra2 = new ItemCompra(produto2, 2);
        carrinho.addItem(itemCompra1);
        carrinho.addItem(itemCompra2);
        double valorTotalCompra = carrinho.calcularValorTotalCompra();
        Assertions.assertEquals(108.0, valorTotalCompra);
    }

    @Test
    public void valorTotaldaCompraDeveSerZeroSeCarrinhoVazio_assertIterableEquals() {
        Assertions.assertIterableEquals(new ArrayList<>(), carrinho.getItensCompra());
        Assertions.assertEquals(0.0, carrinho.getValorTotalCompra());
    }

    @Test
    public void valorEstoqueProdutoDeveSerMaiorOuIgualAoValorComprado_assertTrue() {
        // Adicionando produtos ao carrinho
        carrinho.adicionarProduto(vinho1, 1);
        carrinho.adicionarProduto(vinho2, 2);
        carrinho.adicionarProduto(vinho3, 3);

        // Verificando se o valor total do estoque é maior ou igual ao valor total no carrinho
        double valorTotalEstoque = Produto.getValorTotalEstoque();
        double valorTotalCarrinho = carrinho.getValorTotalCompra();
        Assertions.assertTrue(valorTotalEstoque >= valorTotalCarrinho,
                "O valor total do estoque deve ser maior ou igual ao valor total no carrinho");
    }

    @Test
    public void quantidadeEstoqueDeveSerAtualizadaAposCompra_assertFalse() {
        // Adicionando produtos ao carrinho
        carrinho.adicionarProduto(vinho1, 5);
        carrinho.adicionarProduto(vinho2, 3);

        // Comprando produtos no carrinho
        carrinho.comprar();

        // Verificando se a quantidade de produtos em estoque foi atualizada corretamente
        int quantidadeVinho1 = Produto.getQuantidadeEstoque(vinho1.getCodigo());
        int quantidadeVinho2 = Produto.getQuantidadeEstoque(vinho2.getCodigo());
        Assertions.assertFalse(carrinho.getItensCompra().contains(new ItemCompra(vinho1, 5)),
                "A quantidade de produtos no carrinho não pode ser maior que a quantidade de produtos em estoque");
        Assertions.assertEquals(5 - carrinho.getItensCompra().get(0).getQuantidade(), quantidadeVinho1,
                "A quantidade de produtos em estoque deve ser atualizada corretamente após a compra");
        Assertions.assertEquals(3 - carrinho.getItensCompra().get(1).getQuantidade(), quantidadeVinho2,
                "A quantidade de produtos em estoque deve ser atualizada corretamente após a compra");
    }

    @Test
    public void encontrarProdutoDeveRetornarObjetoSeCodigoExistente_assertNotNull() {
        Produto produto = carrinhoDeCompras.encontrarProduto("001");
        Assertions.assertNotNull(produto);
    }

    @Test
    public void encontrarProdutoDeveRetornarNullSeCodigoNaoExistente_assertNull() {
        Produto produto = carrinhoDeCompras.encontrarProduto("003");
        Assertions.assertNull(produto);
    }

    @Test
    public void produtosNaoSaoIguais_assertNotSame() {
        Produto produto1 = new Produto("001", "Produto 1", 10.0, 20);
        Produto produto2 = new Produto("002", "Produto 2", 15.0, 30);

        assertNotSame("Os produtos não podem ser iguais", produto1, produto2);
    }

