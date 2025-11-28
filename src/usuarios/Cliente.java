package usuarios;
import java.io.Serializable;

public class Cliente extends Usuario {

    public Cliente(String nome, String email, String senha) {
        super(nome, email, senha);
    }

    @Override
    public String getTipoUsuario() {
        return "Cliente";
    }
}