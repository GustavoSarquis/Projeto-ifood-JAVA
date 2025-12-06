package usuarios;
import java.io.Serializable;

public interface Usuario extends Serializable {
    
    // Métodos que todos devem ter
    String getNome();
    String getEmail();
    String getSenha();
    String getTipoUsuario();
}