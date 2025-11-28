package telas;
import javax.swing.*;

import sistema.Sistema;
import usuarios.Cliente;
import usuarios.DonoRestaurante;
import usuarios.Usuario;

import java.awt.*;

public class TelaLogin extends JFrame{
    private Sistema sistema;
    private JFrame telaAnterior;
    private boolean isDono; // true = Dono, false = Cliente

    public TelaLogin(Sistema sistema, JFrame telaAnterior, boolean isDono) {
        this.sistema = sistema;
        this.telaAnterior = telaAnterior;
        this.isDono = isDono;

        setTitle("Acesso - " + (isDono ? "Dono" : "Cliente"));
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane abas = new JTabbedPane();
        abas.add("Login", painelLogin());
        abas.add("Cadastro", painelCadastro());

        add(abas);
        setVisible(true);
    }

    // --- ABA 1: LOGIN ---
    private JPanel painelLogin() {
        JPanel painel = new JPanel(new GridLayout(4, 1, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField txtEmail = new JTextField();
        JPasswordField txtSenha = new JPasswordField();
        JButton btnEntrar = new JButton("Entrar");
        JButton btnVoltar = new JButton("Voltar");

        painel.add(new JLabel("Email (@gmail.com):")); painel.add(txtEmail);
        painel.add(new JLabel("Senha:")); painel.add(txtSenha);
        
        JPanel botoes = new JPanel();
        botoes.add(btnEntrar); botoes.add(btnVoltar);
        painel.add(botoes);

        // AÇÃO DE LOGIN
        btnEntrar.addActionListener(e -> {
            String email = txtEmail.getText();
            String senha = new String(txtSenha.getPassword());

            Usuario usuarioEncontrado = null;

            // Procura na lista certa
            if (isDono) {
                for (DonoRestaurante d : sistema.getDonos()) {
                    if (d.getEmail().equals(email) && d.getSenha().equals(senha)) {
                        usuarioEncontrado = d;
                        break;
                    }
                }
            } else {
                for (Cliente c : sistema.getClientes()) {
                    if (c.getEmail().equals(email) && c.getSenha().equals(senha)) {
                        usuarioEncontrado = c;
                        break;
                    }
                }
            }

            if (usuarioEncontrado != null) {
                abrirProximaTela(usuarioEncontrado);
            } else {
                JOptionPane.showMessageDialog(this, "Email ou senha incorretos!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnVoltar.addActionListener(e -> {
            telaAnterior.setVisible(true);
            dispose();
        });

        return painel;
    }

    // --- ABA 2: CADASTRO ---
    private JPanel painelCadastro() {
        JPanel painel = new JPanel(new GridLayout(8, 1, 5, 5));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JTextField txtNome = new JTextField();
        JTextField txtEmail = new JTextField();
        JPasswordField txtSenha = new JPasswordField();
        JButton btnCadastrar = new JButton("Confirmar Cadastro");

        painel.add(new JLabel("Nome:")); painel.add(txtNome);
        painel.add(new JLabel("Email (@gmail.com):")); painel.add(txtEmail);
        painel.add(new JLabel("Senha (min 6 chars):")); painel.add(txtSenha);
        painel.add(new JLabel("")); 
        painel.add(btnCadastrar);

        btnCadastrar.addActionListener(e -> {
            String nome = txtNome.getText();
            String email = txtEmail.getText();
            String senha = new String(txtSenha.getPassword());

            // --- 1. VALIDAÇÕES
            
            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
                return; // <--- OBRIGATÓRIO: Para o código aqui se estiver vazio
            }

            if (!email.endsWith("@gmail.com")) {
                JOptionPane.showMessageDialog(this, "O email deve terminar com @gmail.com!");
                return; // <--- OBRIGATÓRIO: Para o código aqui se o email for ruim
            }

            if (senha.length() < 6) {
                JOptionPane.showMessageDialog(this, "A senha deve ter no mínimo 6 caracteres!", "Senha Fraca", JOptionPane.WARNING_MESSAGE);
                return; // ISSO IMPEDE O CADASTRO INDEVIDO
            }
            
            if (emailJaCadastrado(email)) {
            JOptionPane.showMessageDialog(this, "O email já está cadastrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            return; // PARA TUDO AQUI. Não deixa cadastrar.
        }

            // --- 2. CADASTRO E SALVAMENTO 

            try {
                if (isDono) {
                    DonoRestaurante novoDono = new DonoRestaurante(nome, email, senha);
                    sistema.cadastrarDono(novoDono);
                    
                    // SALVA IMEDIATAMENTE NO ARQUIVO
                    Sistema.salvarDados(sistema); 

                    JOptionPane.showMessageDialog(this, "Dono cadastrado com sucesso!");
                    abrirProximaTela(novoDono);
                } else {
                    Cliente novoCliente = new Cliente(nome, email, senha);
                    sistema.cadastrarCliente(novoCliente);
                    
                    // SALVA IMEDIATAMENTE NO ARQUIVO
                    Sistema.salvarDados(sistema);

                    JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
                    abrirProximaTela(novoCliente);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar cadastro: " + ex.getMessage());
            }
        });

        return painel;
    }

    // Método auxiliar para decidir para onde ir depois de logar/cadastrar
    private void abrirProximaTela(Usuario usuario) {
        if (isDono) {
            new TelaDonoPrincipal(sistema, (DonoRestaurante) usuario, this);
        } else {
            new TelaCliente(sistema, this);
        }
        setVisible(false); // Esconde a tela de login (não fecha, para poder voltar se precisar)
    }

    // Método auxiliar para verificar duplicidade
private boolean emailJaCadastrado(String email) {
    // 1. Verifica na lista de Donos
    for (DonoRestaurante d : sistema.getDonos()) {
        if (d.getEmail().equalsIgnoreCase(email)) {
            return true; // Encontrou!
        }
    }
    
    // 2. Verifica na lista de Clientes
    for (Cliente c : sistema.getClientes()) {
        if (c.getEmail().equalsIgnoreCase(email)) {
            return true; // Encontrou!
        }
    }
    
    return false; // Não encontrou ninguém com esse email
}
}
