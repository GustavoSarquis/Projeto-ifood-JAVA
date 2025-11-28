package telas;
import javax.swing.*;

import itens.Restaurante;
import sistema.Sistema;
import usuarios.*;

import java.awt.*;
import java.awt.event.*;

public class TelaCliente extends JFrame {
    private Sistema sistema;
    private JFrame telaAnterior;

    public TelaCliente(Sistema sistema, JFrame telaAnterior) {
        this.sistema = sistema;
        this.telaAnterior = telaAnterior;

        setTitle("Tela do Cliente");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // --- Layout principal ---
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Selecione um Restaurante");
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));

        JComboBox<String> comboRestaurantes = new JComboBox<>();
        comboRestaurantes.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        comboRestaurantes.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Preenche o combo com restaurantes cadastrados no sistema
        for (Restaurante r : sistema.listarRestaurantes()) {
            comboRestaurantes.addItem(r.getNome());
        }

        //Cria o texto do endereço
        JLabel lblEndereco = new JLabel("Endereço: -");
        lblEndereco.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblEndereco.setFont(new Font("Arial", Font.ITALIC, 12));
        lblEndereco.setForeground(Color.DARK_GRAY);

        JButton btnVisualizar = new JButton("Ver Cardápio");
        btnVisualizar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVisualizar.setMaximumSize(new Dimension(150, 30));

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVoltar.setMaximumSize(new Dimension(150, 30));

        //Lógica para atualizar o endereço
        Runnable atualizarEndereco = () -> {
        String nomeSelecionado = (String) comboRestaurantes.getSelectedItem();
        if (nomeSelecionado != null) {
        Restaurante r = sistema.buscarRestaurante(nomeSelecionado);
            if (r != null) {
            lblEndereco.setText("Endereço: " + r.getEndereco());
            }
         }
        };

        //avisa quando troca o item da lista
        comboRestaurantes.addActionListener(e -> atualizarEndereco.run());

        // Roda uma vez agora para já iniciar com o endereço do primeiro da lista
        atualizarEndereco.run();
        
        panel.add(lblTitulo);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(comboRestaurantes);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(lblEndereco); 
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(btnVisualizar);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(btnVoltar);

        add(panel);

        // --- Botão Visualizar ---
        btnVisualizar.addActionListener(e -> {
            String nomeRestaurante = (String) comboRestaurantes.getSelectedItem();
            Restaurante r = sistema.buscarRestaurante(nomeRestaurante);

            if (r != null) {
                // Abre nova tela de cardápio
                new TelaCardapioCliente(r, TelaCliente.this);
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Restaurante não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        // --- Botão Voltar ---
        btnVoltar.addActionListener(e -> {
            telaAnterior.setVisible(true);
            dispose();
        });

        setVisible(true);
    }
}
