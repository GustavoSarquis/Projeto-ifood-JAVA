package telas;
import javax.swing.*;

import itens.Bebida;
import itens.Comida;
import itens.Produto;
import itens.Restaurante;
import sistema.Sistema;
import usuarios.DonoRestaurante;

import java.awt.*;
import java.awt.event.*;

public class TelaProdutosRestaurante extends JFrame {
    private Sistema sistema;
    private DonoRestaurante dono;
    private Restaurante restaurante;
    private JFrame telaAnterior;

    private DefaultListModel<Produto> modeloLista; // lista de produtos visível

    public TelaProdutosRestaurante(Sistema sistema, DonoRestaurante dono, Restaurante restaurante, JFrame telaAnterior) {
        this.sistema = sistema;
        this.dono = dono;
        this.restaurante = restaurante;
        this.telaAnterior = telaAnterior;

        setTitle("Gerenciar Produtos - " + restaurante.getNome());
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- Título e botão voltar ---
        JPanel painelTopo = new JPanel(new BorderLayout());
        JLabel lblTitulo = new JLabel("Gerenciar Produtos - " + restaurante.getNome(), SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> {
            telaAnterior.setVisible(true);
            dispose();
        });
        painelTopo.add(btnVoltar, BorderLayout.WEST);
        painelTopo.add(lblTitulo, BorderLayout.CENTER);
        add(painelTopo, BorderLayout.NORTH);

        // --- Lista de produtos cadastrados ---
        modeloLista = new DefaultListModel<>();
        atualizarLista();

        JList<Produto> listaProdutos = new JList<>(modeloLista);
        listaProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaProdutos.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                Produto p = (Produto) value;
                String texto = p.getNome() + " - R$" + p.getPreco() + " (" + p.getTempoPreparo() + " min)";
                return super.getListCellRendererComponent(list, texto, index, isSelected, cellHasFocus);
            }
        });

        JScrollPane scroll = new JScrollPane(listaProdutos);
        add(scroll, BorderLayout.CENTER);

        // --- Botões de ação ---
        JButton btnCadastrar = new JButton("Cadastrar Novo Produto");
        JButton btnEditar = new JButton("Editar Produto");
        JButton btnRemover = new JButton("Remover Produto");
        JButton btnDetalhes = new JButton("Ver Detalhes");

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnDetalhes);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnRemover);
        add(painelBotoes, BorderLayout.SOUTH);

        // --- Ações dos botões ---
        btnCadastrar.addActionListener(e -> abrirTelaCadastro(null)); // null = novo produto

        btnDetalhes.addActionListener(e -> {
    Produto selecionado = listaProdutos.getSelectedValue();
    
    if (selecionado != null) {
        String textoDetalhes = selecionado.getDetalhesCompletos();
        
        JOptionPane.showMessageDialog(this, textoDetalhes, 
                "Detalhes do Produto", JOptionPane.INFORMATION_MESSAGE);
    } else {
        JOptionPane.showMessageDialog(this, "Selecione um produto para ver os detalhes!");
    }
});
        
        btnEditar.addActionListener(e -> {
            Produto selecionado = listaProdutos.getSelectedValue();
            if (selecionado != null) {
                abrirTelaCadastro(selecionado);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um produto para editar!");
            }
        });

        btnRemover.addActionListener(e -> {
            Produto selecionado = listaProdutos.getSelectedValue();
            if (selecionado != null) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Tem certeza que deseja remover " + selecionado.getNome() + "?",
                        "Confirmar remoção", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    restaurante.getProdutos().remove(selecionado);
                    atualizarLista();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um produto para remover!");
            }
        });

        setVisible(true);
    }

    private void atualizarLista() {
        modeloLista.clear();
        for (Produto p : restaurante.getProdutos()) {
            modeloLista.addElement(p);
        }
    }

    // --- Tela de cadastro e edição ---
    private void abrirTelaCadastro(Produto produtoExistente) {
    JTextField txtNome = new JTextField(15);
    JTextField txtDescricao = new JTextField(15);
    JTextField txtPreco = new JTextField(5);
    JTextField txtTempo = new JTextField(5);

    // --- MUDANÇA 1: CRIAÇÃO DOS COMPONENTES DINÂMICOS ---
    JComboBox<String> comboTipo = new JComboBox<>(new String[]{"Comida", "Bebida"});
    
    JLabel lblExtra1 = new JLabel("Tipo de Cozinha:");
    JTextField txtExtra1 = new JTextField(10); // Serve para Cozinha (texto) ou ML (número)
    
    JLabel lblExtra2 = new JLabel("Restrição:");
    JComboBox<String> comboExtra2 = new JComboBox<>(); // <--- AGORA É UM COMBOBOX, NÃO TEXTO
    // ----------------------------------------------------

    // --- MUDANÇA 2: LÓGICA DE TROCA (EVENT LISTENER) ---
    // Isso faz a tela mudar quando você troca entre Comida e Bebida
    comboTipo.addActionListener(e -> {
        String selecionado = (String) comboTipo.getSelectedItem();
        
        if ("Comida".equals(selecionado)) {
            lblExtra1.setText("Tipo de Cozinha:");
            lblExtra2.setText("Opções:");
            
            // Opções específicas de comida
            comboExtra2.setModel(new DefaultComboBoxModel<>(
                new String[]{"Nem vegetariano, nem vegano", "Vegetariano", "Vegano"}
            ));
            
        } else {
            lblExtra1.setText("Tamanho (ML):");
            lblExtra2.setText("Alcoólico?:");
            
            // Opções específicas de bebida
            comboExtra2.setModel(new DefaultComboBoxModel<>(
                new String[]{"Não", "Sim"}
            ));
        }
    });
    
    // Força a atualização inicial
    comboTipo.setSelectedIndex(0); 
    // ---------------------------------------------------

    // Preenchimento (Edição)
    if (produtoExistente != null) {
        txtNome.setText(produtoExistente.getNome());
        txtDescricao.setText(produtoExistente.getDescricao());
        txtPreco.setText(String.valueOf(produtoExistente.getPreco()));
        txtTempo.setText(String.valueOf(produtoExistente.getTempoPreparo()));
        comboTipo.setSelectedItem(produtoExistente.getTipo());
        
        // Tenta preencher o campo extra 1 (Cozinha ou ML)
        if (produtoExistente instanceof Comida) {
            // Necessário ter getTipoCozinha() público na classe Comida
             txtExtra1.setText(((Comida) produtoExistente).getTipoCozinha());
        } else if (produtoExistente instanceof Bebida) {
            // Necessário ter getTamanhoMl() público na classe Bebida
             txtExtra1.setText(String.valueOf(((Bebida) produtoExistente).getTamanhoMl()));
        }
    }

    // Montagem do Painel
    JPanel painel = new JPanel(new GridLayout(0, 2, 5, 5));
    painel.add(new JLabel("Tipo:")); painel.add(comboTipo);
    painel.add(new JLabel("Nome:")); painel.add(txtNome);
    painel.add(new JLabel("Descrição:")); painel.add(txtDescricao);
    painel.add(new JLabel("Preço:")); painel.add(txtPreco);
    painel.add(new JLabel("Tempo (min):")); painel.add(txtTempo);
    painel.add(lblExtra1); painel.add(txtExtra1);
    painel.add(lblExtra2); painel.add(comboExtra2);

    int result = JOptionPane.showConfirmDialog(this, painel,
            (produtoExistente == null ? "Cadastrar Produto" : "Editar Produto"),
            JOptionPane.OK_CANCEL_OPTION);

    if (result == JOptionPane.OK_OPTION) {
        try {
            String nome = txtNome.getText();
            String descricao = txtDescricao.getText();
            double preco = Double.parseDouble(txtPreco.getText().replace(",", "."));
            int tempo = Integer.parseInt(txtTempo.getText());
            String tipo = (String) comboTipo.getSelectedItem();

            Produto novoProduto;

            // --- MUDANÇA 3: INTERPRETAR AS ESCOLHAS DO USUÁRIO ---
            if (tipo.equals("Comida")) {
                String cozinha = txtExtra1.getText();
                String escolhaExtra = (String) comboExtra2.getSelectedItem();
                
                // Converte a frase escolhida em booleans
                boolean vegetariano = escolhaExtra.contains("Vegetariano") || escolhaExtra.contains("Vegano");
                boolean vegano = "Vegano".equals(escolhaExtra);

                novoProduto = new Comida(nome, descricao, preco, tempo, cozinha, vegetariano, vegano);
            
            } else {
                int tamanho = Integer.parseInt(txtExtra1.getText()); // Lê o ML
                boolean alcoolica = "Sim".equals(comboExtra2.getSelectedItem()); // Lê o Sim/Não

                novoProduto = new Bebida(nome, descricao, preco, tempo, tamanho, alcoolica);
            }
            // -----------------------------------------------------

            if (produtoExistente != null) {
                restaurante.getProdutos().remove(produtoExistente);
            }

            restaurante.adicionarProduto(novoProduto);
            Sistema.salvarDados(sistema); // Salva
            atualizarLista();

            JOptionPane.showMessageDialog(this, "Produto salvo com sucesso!");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
        }
    }
}
}
