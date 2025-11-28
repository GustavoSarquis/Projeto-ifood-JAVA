package usuarios;
import java.io.Serializable;

public abstract class Usuario implements Serializable{
    protected String nome;
    protected String email;
    protected String senha;

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public abstract String getTipoUsuario();

    
}
