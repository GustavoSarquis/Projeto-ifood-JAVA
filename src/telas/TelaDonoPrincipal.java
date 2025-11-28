package telas;
import javax.swing.*;

import sistema.Sistema;
import usuarios.DonoRestaurante;

import java.awt.*;

public class TelaDonoPrincipal extends JFrame {
    private Sistema sistema;
    private DonoRestaurante dono;
    private JFrame telaAnterior;

    public TelaDonoPrincipal(Sistema sistema, DonoRestaurante dono, JFrame telaAnterior) {
        this.sistema = sistema;
        this.dono = dono;
        this.telaAnterior = telaAnterior;

        setTitle("Área do Dono");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1, 10, 10));

        JButton btnCadastrarRest = new JButton("Cadastrar Restaurante");
        JButton btnEntrarRest = new JButton("Entrar em Restaurante");
        JButton btnVoltar = new JButton("Voltar");

        // --- Botão Cadastrar ---
        btnCadastrarRest.addActionListener(e -> {
            new TelaCadastroRestaurante(sistema, dono, this);
            setVisible(false);
        });

        // --- Botão Entrar (A CORREÇÃO ESTÁ AQUI) ---
        btnEntrarRest.addActionListener(e -> {
            // Verifica ANTES de criar a janela nova e ANTES de esconder a atual
            if (sistema.getRestaurantes().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Você ainda não cadastrou nenhum restaurante!");
                // Não faz nada (continua nesta tela)
            } else {
                // Só agora abrimos a próxima tela e escondemos esta
                new TelaEscolherRestaurante(sistema, dono, this);
                setVisible(false);
            }
        });

        // --- Botão Voltar ---
        btnVoltar.addActionListener(e -> {
            telaAnterior.setVisible(true);
            dispose();
        });

        add(btnCadastrarRest);
        add(btnEntrarRest);
        add(btnVoltar);

        setVisible(true);
    }
}