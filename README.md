# QS_assertions

## Exemplos baseados numa loja virtual, cuidando com as regras de negocio

// ChatGPT

Suponha agora que você está trabalhando em um software para gerenciamento de estoque de uma loja virtual e precisa testar uma classe "EstoqueService" que é responsável por controlar o estoque dos produtos. A classe possui vários métodos, mas vamos nos concentrar em um método específico: "adicionarProdutosAoEstoque".

Este método recebe como parâmetros uma lista de objetos "Produto" e um objeto "Loja", e deve adicionar os produtos ao estoque da loja. No entanto, existem várias regras que precisam ser verificadas antes de adicionar os produtos ao estoque. Algumas dessas regras são:

O estoque máximo da loja não pode ser ultrapassado.
A quantidade de cada produto não pode ser negativa.
O produto precisa estar cadastrado na loja.
Para testar esse método, podemos escrever um caso de teste que cria uma lista de produtos e uma loja, e chama o método "adicionarProdutosAoEstoque". O teste deve verificar se os produtos foram adicionados corretamente ao estoque e se as regras foram respeitadas. Um exemplo de teste usando assertions complexas pode ser o seguinte:
