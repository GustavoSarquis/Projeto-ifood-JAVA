package usuarios;

import itens.Restaurante;
import java.io.Serializable;

public class DonoRestaurante implements Usuario { 
    private String nome;
    private String email;
    private String senha;
    
    // Atributo específico do Dono
    private Restaurante restaurante;

    public DonoRestaurante(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public void cadastrarRestaurante(String nome, String endereco) {
        this.restaurante = new Restaurante(nome, endereco);
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    @Override
    public String getNome() { return nome; }

    @Override
    public String getEmail() { return email; }

    @Override
    public String getSenha() { return senha; }

    @Override
    public String getTipoUsuario() {
        return "Dono";
    }
}