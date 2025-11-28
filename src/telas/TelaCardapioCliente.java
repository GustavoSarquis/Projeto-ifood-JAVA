package telas;
import javax.swing.*;

import itens.Produto;
import itens.Restaurante;
import sistema.Sistema;
import usuarios.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class TelaCardapioCliente extends JFrame {
    private Restaurante restaurante;
    private JFrame telaAnterior;

    public TelaCardapioCliente(Restaurante restaurante, JFrame telaAnterior) {
        this.restaurante = restaurante;
        this.telaAnterior = telaAnterior;

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
                String texto = p.getNome() + " - R$" + p.getPreco() + " | " + p.getDescricao();
                return super.getListCellRendererComponent(list, texto, index, isSelected, cellHasFocus);
            }
        });

        JScrollPane scroll = new JScrollPane(listaProdutos);
        add(scroll, BorderLayout.CENTER);

        // --- Botões ---
        JButton btnAdicionar = new JButton("Adicionar ao Carrinho");
        JButton btnDetalhes = new JButton("Ver Detalhes");
        JButton btnVerCarrinho = new JButton("Ver Carrinho");
        JButton btnVoltar = new JButton("Voltar");

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnDetalhes);
        painelBotoes.add(btnVerCarrinho);
        painelBotoes.add(btnVoltar);
        add(painelBotoes, BorderLayout.SOUTH);

        // --- Carrinho ---
        java.util.List<Produto> carrinho = new ArrayList<>();

        btnAdicionar.addActionListener(e -> {
            java.util.List<Produto> selecionados = listaProdutos.getSelectedValuesList();
            if (selecionados.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecione pelo menos um produto!");
                return;
            }
            carrinho.addAll(selecionados);
            String nomes = String.join(", ", selecionados.stream().map(Produto::getNome).toArray(String[]::new));
            JOptionPane.showMessageDialog(this, "Produtos adicionados ao carrinho: " + nomes);
        });

        btnDetalhes.addActionListener(e -> {
        Produto selecionado = listaProdutos.getSelectedValue();
    
        if (selecionado != null) {
        String texto = selecionado.getDetalhesCompletos();
        
        JOptionPane.showMessageDialog(this, texto, "Detalhes do Produto", JOptionPane.INFORMATION_MESSAGE);
        }   else {
        JOptionPane.showMessageDialog(this, "Selecione um produto para ver os detalhes!");
        }
        });

        btnVerCarrinho.addActionListener(e -> {
            if (carrinho.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Seu carrinho está vazio!");
                return;
            }

            double totalPreco = 0;
            int tempoMax = 0;
            StringBuilder sb = new StringBuilder("Itens no carrinho:\n\n");
            for (Produto p : carrinho) {
                sb.append("- ").append(p.getNome())
                  .append(" | R$").append(p.getPreco())
                  .append(" | ").append(p.getTempoPreparo()).append(" min\n");
                totalPreco += p.getPreco();
                if (p.getTempoPreparo() > tempoMax) {
                    tempoMax = p.getTempoPreparo();
                }
            }

            sb.append("\nPreço Total: R$").append(String.format("%.2f", totalPreco));
            sb.append("\nTempo estimado: ").append(tempoMax).append(" minutos");

            JOptionPane.showMessageDialog(this, sb.toString(), "Carrinho", JOptionPane.INFORMATION_MESSAGE);
        });

        btnVoltar.addActionListener(e -> {
            telaAnterior.setVisible(true);
            dispose();
        });

        setVisible(true);
    }
}
