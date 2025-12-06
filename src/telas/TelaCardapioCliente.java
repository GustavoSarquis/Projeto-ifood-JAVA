package telas;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List; // Importante para usar List

import sistema.Sistema;
import itens.Produto;
import itens.Restaurante;

public class TelaCardapioCliente extends JFrame {
    private Restaurante restaurante;
    private JFrame telaAnterior;
    private List<Produto> carrinho; // O carrinho agora é um atributo da classe

    public TelaCardapioCliente(Restaurante restaurante, JFrame telaAnterior) {
        this.restaurante = restaurante;
        this.telaAnterior = telaAnterior;
        this.carrinho = new ArrayList<>(); // Inicializa o carrinho vazio

        setTitle("Cardápio - " + restaurante.getNome());
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- Título ---
        JLabel lblTitulo = new JLabel("Cardápio de " + restaurante.getNome(), SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTitulo, BorderLayout.NORTH);

        // --- Lista de produtos ---
        DefaultListModel<Produto> modelo = new DefaultListModel<>();
        for (Produto p : restaurante.getProdutos()) {
            modelo.addElement(p);
        }

        JList<Produto> listaProdutos = new JList<>(modelo);
        listaProdutos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listaProdutos.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                Produto p = (Produto) value;
                String texto = p.getNome() + " - R$" + String.format("%.2f", p.getPreco()) + " | " + p.getDescricao();
                return super.getListCellRendererComponent(list, texto, index, isSelected, cellHasFocus);
            }
        });

        JScrollPane scroll = new JScrollPane(listaProdutos);
        add(scroll, BorderLayout.CENTER);

        // --- Botões ---
        JButton btnAdicionar = new JButton("Adicionar ao Carrinho");
        JButton btnDetalhes = new JButton("Ver Detalhes");
        JButton btnVerCarrinho = new JButton("Ver Carrinho / Pagar"); // Nome mudou
        JButton btnVoltar = new JButton("Voltar");

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnDetalhes);
        painelBotoes.add(btnVerCarrinho);
        painelBotoes.add(btnVoltar);
        add(painelBotoes, BorderLayout.SOUTH);

        // --- AÇÃO: ADICIONAR ---
        btnAdicionar.addActionListener(e -> {
            List<Produto> selecionados = listaProdutos.getSelectedValuesList();
            if (selecionados.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecione pelo menos um produto!");
                return;
            }
            carrinho.addAll(selecionados);
            JOptionPane.showMessageDialog(this, selecionados.size() + " item(ns) adicionado(s) ao carrinho!");
        });

        // --- AÇÃO: DETALHES ---
        btnDetalhes.addActionListener(e -> {
            Produto selecionado = listaProdutos.getSelectedValue();
            if (selecionado != null) {
                JOptionPane.showMessageDialog(this, selecionado.getDetalhesCompletos(), "Detalhes", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um produto para ver os detalhes!");
            }
        });

        // --- AÇÃO: VER CARRINHO (MUDANÇA AQUI) ---
        btnVerCarrinho.addActionListener(e -> {
            // Agora abre a nova TelaCarrinho passando a lista atual
            new TelaCarrinho(carrinho, this);
        });

        // --- AÇÃO: VOLTAR ---
        btnVoltar.addActionListener(e -> {
            telaAnterior.setVisible(true);
            dispose();
        });

        setVisible(true);
    }
}