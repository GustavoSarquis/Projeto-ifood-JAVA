package usuarios;
import java.util.ArrayList;
import java.util.List;

import itens.Restaurante;
import java.io.Serializable;

public class DonoRestaurante extends Usuario {
    private Restaurante restaurante;

    public DonoRestaurante(String nome, String email, String senha) {
        super(nome, email, senha);
    }

    public void cadastrarRestaurante(String nome, String endereco) {
        this.restaurante = new Restaurante(nome, endereco);
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    @Override
    public String getTipoUsuario() {
        return "Dono";
    }
}
