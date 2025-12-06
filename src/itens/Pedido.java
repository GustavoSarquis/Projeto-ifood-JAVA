package itens;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Pedido {
    private List<Produto> itens;

    public Pedido() {
        this.itens = new ArrayList<>();
    }

    public void adicionarProduto(Produto p) {
        itens.add(p);
    }

    public double calcularTotal() {
        double total = 0;
        for (Produto p : itens) {
            total += p.getPreco();
        }
        return total;
    }

    public int calcularTempoEstimado() {
        int tempo = 0;
        for (Produto p : itens) {
            tempo += p.getTempoPreparo();
        }
        return tempo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Pedido:\n");
        for (Produto p : itens) {
            sb.append("- ").append(p.getNome()).append("\n");
        }
        sb.append("Total: R$").append(calcularTotal())
          .append(" | Tempo estimado: ").append(calcularTempoEstimado()).append(" min");
        return sb.toString();
    }
}