package usuarios;
import java.io.Serializable;

public class Cliente implements Usuario { 
    
    private String nome;
    private String email;
    private String senha;

    public Cliente(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    @Override
    public String getNome() { return nome; }

    @Override
    public String getEmail() { return email; }

    @Override
    public String getSenha() { return senha; }

    @Override
    public String getTipoUsuario() {
        return "Cliente";
    }
}