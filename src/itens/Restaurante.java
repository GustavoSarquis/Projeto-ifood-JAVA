package itens;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Restaurante implements Serializable {
    private String nome;
    private String endereco;
    private List<Produto> produtos;

    public Restaurante(String nome, String endereco) {
        this.nome = nome;
        this.endereco = endereco;
        this.produtos = new ArrayList<>();
    }

    public void adicionarProduto(Produto p) {
        produtos.add(p);
    }

    public void removerProduto(Produto p) {
        produtos.remove(p);
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    @Override
    public String toString() {
        return nome + " - " + endereco;
    }
}