# Projeto-ifood-JAVA
# Integrantes:
- Gustavo Sarquis
- Pedro Henrique 
# Sistema de Gerenciamento de Restaurante (Java Swing):
Este é um projeto desenvolvido em Java utilizando a biblioteca gráfica Swing. O sistema simula uma plataforma de delivery, conectando Donos de Restaurantes e Clientes, com foco em boas práticas de Orientação a Objetos e persistência de dados local. O objetivo deste software é permitir que donos de restaurantes cadastrem seus estabelecimentos e cardápios (comidas e bebidas), enquanto clientes podem navegar, visualizar detalhes técnicos dos produtos e montar carrinhos de compras. O sistema mantém os dados salvos automaticamente entre as sessões.
# Arquitetura e Organização (Pacotes):
O código foi reestruturado em pacotes, com o objetivo de deixar organizado e de facíl entendimento ao usuário que for ler o código, dividindo as funções em pacotes específicos, os pacotes com as classes foram dividido da seguinte forma: 

├── 📂 main
│   └── Main.java             
│
├── 📂 sistema
│   └── Sistema.java         
├── 📂 usuarios               
│   ├── Usuario.java          
│   ├── Cliente.java          
│   └── DonoRestaurante.java  
│
├── 📂 itens                  
│   ├── Restaurante.java      
│   ├── Produto.java          
│   ├── Comida.java           
│   ├── Bebida.java           
│   └── Pedido.java           
│
└── 📂 telas                  
    ├── TelaInicial.java      
    ├── TelaLogin.java        
    ├── TelaDonoPrincipal.java
    ├── TelaProdutosRestaurante.java  
    ├── TelaCliente.java      
    ├── TelaCadastroRestaurante.java
    ├── TelaCardapioCliente.java
    ├── TelaEscolherRestaurante.java


# Funcionalidades do código:
# - Segurança do site
- Login e Cadastro: Sistema unificado com abas para entrar ou criar conta.
- Validações Robustas:
- Impede e-mails duplicados no sistema.
- Exige e-mails válidos (@gmail.com).
- Exige senha mínima de 6 caracteres.
- Recuperação de Sessão: Ao abrir o programa, o usuário pode escolher carregar os dados anteriores ou iniciar do zero.
# - Parte do Dono
- Gestão de Restaurantes: Cadastro de múltiplos restaurantes vinculados ao dono.
- Gerenciamento de Cardápio (CRUD):
- Adicionar, Editar e Remover produtos.
- Polimorfismo Visual: O formulário de cadastro muda dinamicamente. Se selecionar "Comida", pede Tipo de Cozinha e Restrição Alimentar. Se "Bebida", pede ML e Teor Alcoólico.
- Verificação: O sistema impede acesso a telas de produtos se não houver restaurante cadastrado.
# - Parte do Cliente
- Navegação Inteligente: Lista de restaurantes com exibição dinâmica do endereço ao selecionar o nome.
- Carrinho de Compras: Adição de múltiplos itens.
- Cálculos Automáticos: O sistema calcula o valor total e o tempo estimado de entrega (baseado no prato mais demorado).
- Ficha Técnica: Visualização detalhada de produtos (ex: saber se um prato é Vegano ou se uma bebida é Alcoólica).
# Salvamento de Dados(Serialização):
- O sistema utiliza a interface Serializable do Java.
- Salvamento Automático: Implementação de um Shutdown Hook que garante que os dados sejam salvos no arquivo binário (dados_restaurante.bin) sempre que o programa é fechado, evitando perda de dados acidental.
# Conceitos utilizados:
- Java Swing (GUI): Uso de JFrame, JPanel, JList, JComboBox dinâmico, JTabbedPane e LayoutManagers (BorderLayout, GridLayout, BoxLayout).
- Programação Orientada a Objetos (POO):
- Herança: Cliente extends Usuario, Comida extends Produto.
- Polimorfismo: Método getDetalhesCompletos() e renderização de listas.
- Encapsulamento: Uso de modificadores de acesso e Getters/Setters.
- Tratamento de Exceções: Blocos try-catch para IO e validação de números.
- Streams API: Utilizado para manipulação eficiente de nomes na lista de carrinho.
# Como executar o código:
- Utilize algum app como o VScode, Eclipse, entre outros.
- Certifique-se de ter o JDK instalado.
- Abra o terminal na pasta src do projeto.
- Execute a classe Main

    
