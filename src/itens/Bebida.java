package itens;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Bebida extends Produto {
    private int tamanhoMl;
    private boolean alcoolica;

    public Bebida(String nome, String descricao, double preco, int tempoPreparo,
                  int tamanhoMl, boolean alcoolica) {
        super(nome, descricao, preco, tempoPreparo);
        this.tamanhoMl = tamanhoMl;
        this.alcoolica = alcoolica;
    }

  public int getTamanhoMl(){
    return tamanhoMl;
  }
  
    @Override
    public String getTipo() {
        return "Bebida";
    }

    @Override
    public String toString() {
        return super.toString() + " | " + tamanhoMl + "ml" +
               (alcoolica ? " | Alcoólica" : " | Não alcoólica");
    }

    @Override
public String getDetalhesCompletos() {
    String base = super.getDetalhesCompletos();
    
    StringBuilder sb = new StringBuilder(base);
    sb.append("Tipo: Bebida\n");
    sb.append("Tamanho: ").append(tamanhoMl).append("ml\n");
    sb.append("Alcoólica: ").append(alcoolica ? "Sim (18+)" : "Não").append("\n");
    
    return sb.toString();
}
}