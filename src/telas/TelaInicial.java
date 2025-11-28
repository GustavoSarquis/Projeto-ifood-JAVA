package telas;
import javax.swing.*;

import sistema.Sistema;

import java.awt.*;
import java.awt.event.*;

public class TelaInicial extends JFrame {
    private Sistema sistema;

    public TelaInicial(Sistema sistema) {
        this.sistema = sistema;

        setTitle("Sistema de Restaurante");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- Título no topo ---
        JLabel lblTitulo = new JLabel("Bem-vindo ao Sistema de Restaurante", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblTitulo, BorderLayout.NORTH);

        // --- Painel central com botões grandes ---
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(2, 1, 10, 10)); // 2 linhas, 1 coluna, espaçamento vertical
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50)); // margens internas

        JButton btnDono = new JButton("Entrar como Dono");
        JButton btnCliente = new JButton("Entrar como Cliente");

        // Aumenta a fonte e o tamanho dos botões
        Font fonteBotao = new Font("Arial", Font.PLAIN, 18);
        btnDono.setFont(fonteBotao);
        btnCliente.setFont(fonteBotao);

        btnDono.setFocusPainted(false);
        btnCliente.setFocusPainted(false);

        // Botão Dono: Abre Login configurado para Dono (true)
        btnDono.addActionListener(e -> {
            new TelaLogin(sistema, TelaInicial.this, true); 
            setVisible(false);
        });

        // Botão Cliente: Abre Login configurado para Cliente (false)
        btnCliente.addActionListener(e -> {
            new TelaLogin(sistema, TelaInicial.this, false);
            setVisible(false);
        });

        painelBotoes.add(btnDono);
        painelBotoes.add(btnCliente);
        add(painelBotoes, BorderLayout.CENTER);

        // --- Rodapé opcional ---
        JLabel lblRodape = new JLabel("© 2025 - Sistema de Restaurante", SwingConstants.CENTER);
        lblRodape.setFont(new Font("Arial", Font.ITALIC, 12));
        lblRodape.setForeground(Color.GRAY);
        add(lblRodape, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        Sistema sistema = new Sistema();
        new TelaInicial(sistema);
    }
}