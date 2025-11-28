package telas;
import javax.swing.*;

import sistema.Sistema;
import usuarios.DonoRestaurante;
import itens.*;

import java.awt.*;

public class TelaCadastroRestaurante extends JFrame {
    public TelaCadastroRestaurante(Sistema sistema, DonoRestaurante dono, JFrame telaAnterior) {
        setTitle("Cadastro de Restaurante");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 5, 5));

        JLabel lblNome = new JLabel("Nome:");
        JTextField txtNome = new JTextField();

        JLabel lblEndereco = new JLabel("Endereço:");
        JTextField txtEndereco = new JTextField();

        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnVoltar = new JButton("Voltar");

        btnCadastrar.addActionListener(e -> {
            String nome = txtNome.getText();
            String endereco = txtEndereco.getText();

            if (nome.isEmpty() || endereco.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                return;
            }

            dono.cadastrarRestaurante(nome, endereco);
            sistema.getRestaurantes().add(dono.getRestaurante());
            JOptionPane.showMessageDialog(null, "Restaurante cadastrado com sucesso!");

            telaAnterior.setVisible(true);
            dispose();
        });

        btnVoltar.addActionListener(e -> {
            telaAnterior.setVisible(true);
            dispose();
        });

        add(lblNome);
        add(txtNome);
        add(lblEndereco);
        add(txtEndereco);
        add(btnCadastrar);
        add(btnVoltar);

        setVisible(true);
    }
}
