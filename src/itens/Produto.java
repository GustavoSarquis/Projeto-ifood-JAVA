package itens;
import java.io.Serializable;
public abstract class Produto implements Serializable{
    protected String nome;
    protected String descricao;
    protected double preco;
    protected int tempoPreparo; // em minutos

    public Produto(String nome, String descricao, double preco, int tempoPreparo) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.tempoPreparo = tempoPreparo;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao; // <-- adicionado
    }

    public double getPreco() {
        return preco;
    }

    public int getTempoPreparo() {
        return tempoPreparo;
    }

    public abstract String getTipo();

    @Override
    public String toString() {
        return nome + " - R$" + preco + " (" + tempoPreparo + " min)";
    }
    
    public String getDetalhesCompletos() {
    StringBuilder sb = new StringBuilder();
    sb.append("--- Ficha Técnica ---\n");
    sb.append("Nome: ").append(nome).append("\n");
    sb.append("Descrição: ").append(descricao).append("\n");
    sb.append("Preço: R$ ").append(String.format("%.2f", preco)).append("\n");
    sb.append("Tempo de Preparo: ").append(tempoPreparo).append(" min\n");
    return sb.toString();
}
}