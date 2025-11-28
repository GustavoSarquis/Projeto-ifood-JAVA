package telas;
import javax.swing.*;

import itens.Restaurante;
import sistema.Sistema;
import usuarios.DonoRestaurante;

import java.awt.*;

public class TelaEscolherRestaurante extends JFrame {
    
    public TelaEscolherRestaurante(Sistema sistema, DonoRestaurante dono, JFrame telaAnterior) {
        // A verificação de "isEmpty" foi movida para a tela anterior (TelaDonoPrincipal).
        // Aqui assumimos que já existe pelo menos um restaurante.

        setTitle("Entrar em Restaurante");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1, 10, 10));

        JLabel lblInstrucao = new JLabel("Selecione o restaurante:", SwingConstants.CENTER);
        
        // Dropdown (ComboBox)
        JComboBox<String> comboRestaurantes = new JComboBox<>();
        for (Restaurante r : sistema.getRestaurantes()) {
            comboRestaurantes.addItem(r.getNome());
        }

        JButton btnEntrar = new JButton("Entrar");
        JButton btnVoltar = new JButton("Voltar");

        // Ação Entrar
        btnEntrar.addActionListener(e -> {
            String nomeSelecionado = (String) comboRestaurantes.getSelectedItem();

            if (nomeSelecionado != null) {
                Restaurante encontrado = sistema.buscarRestaurante(nomeSelecionado);
                
                if (encontrado != null) {
                    new TelaProdutosRestaurante(sistema, dono, encontrado, this);
                    setVisible(false);
                }
            }
        });

        // Ação Voltar
        btnVoltar.addActionListener(e -> {
            telaAnterior.setVisible(true);
            dispose();
        });

        add(lblInstrucao);
        add(comboRestaurantes);

        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnEntrar);
        painelBotoes.add(btnVoltar);
        add(painelBotoes);

        setVisible(true);
    }
}