package itens;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Comida extends Produto {
    private String tipoCozinha;
    private boolean vegetariano;
    private boolean vegano;

    public Comida(String nome, String descricao, double preco, int tempoPreparo, 
                  String tipoCozinha, boolean vegetariano, boolean vegano) {
        super(nome, descricao, preco, tempoPreparo);
        this.tipoCozinha = tipoCozinha;
        this.vegetariano = vegetariano;
        this.vegano = vegano;
    }
    
    public String getTipoCozinha() {
        return tipoCozinha;
    }

    @Override
    public String getTipo() {
        return "Comida";
    }

    @Override
    public String toString() {
        return super.toString() + " | Cozinha: " + tipoCozinha + 
               (vegetariano ? " | Vegetariano" : "") + 
               (vegano ? " | Vegano" : "");
    }

    @Override
public String getDetalhesCompletos() {
    String base = super.getDetalhesCompletos();
    
    StringBuilder sb = new StringBuilder(base);
    sb.append("Tipo: Comida\n");
    sb.append("Cozinha: ").append(tipoCozinha).append("\n");
    sb.append("Vegetariano: ").append(vegetariano ? "Sim" : "Não").append("\n");
    sb.append("Vegano: ").append(vegano ? "Sim" : "Não").append("\n");
    
    return sb.toString();
}

}
