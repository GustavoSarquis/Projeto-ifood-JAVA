package sistema;
import java.util.ArrayList;
import java.util.List;

import itens.Restaurante;
import usuarios.Cliente;
import usuarios.DonoRestaurante;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Sistema implements Serializable{
    private List<Restaurante> restaurantes;
    private List<DonoRestaurante> donos;
    private List<Cliente> clientes;
    private static final long serialVersionUID = 1L;

    public Sistema() {
        restaurantes = new ArrayList<>(); // <-- inicializa a lista
        donos = new ArrayList<>();
        clientes = new ArrayList<>();
    }

    public void cadastrarDono(DonoRestaurante dono) {
        donos.add(dono);
    }

    public void cadastrarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    // Novo método para cadastrar restaurante diretamente no sistema
    public void cadastrarRestaurante(Restaurante restaurante) {
        restaurantes.add(restaurante);
    }

    public Restaurante buscarRestaurante(String nome) {
        for (Restaurante r : restaurantes) {
            if (r.getNome().equalsIgnoreCase(nome)) {
                return r;
            }
        }
        return null; // se não encontrar
    }

    public List<Restaurante> getRestaurantes() {
        return restaurantes;
    }

    public List<Restaurante> listarRestaurantes() {
        return restaurantes; // retorna a lista já cadastrada
    }

    // getters se precisar acessar listas diretamente
    public List<DonoRestaurante> getDonos() {
        return donos;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public static void salvarDados(Sistema sistema) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("dados_restaurante.bin"))) {
            out.writeObject(sistema);
        } catch (IOException e) {
            System.err.println("Erro ao salvar: " + e.getMessage());
        }
    }

    public static Sistema carregarDados() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("dados_restaurante.bin"))) {
            return (Sistema) in.readObject();
        } catch (Exception e) {
            return new Sistema(); // Se der erro ou não existir, cria um novo
        }
    }
}