import java.util.HashMap;
import java.util.Map;

public class No {
    private String nome;
    private int x;
    private int y;
   private  Map<No, Integer> vizinhos = new HashMap<>(); 


    public void addVizinho(No vizinho, int peso) {
        vizinhos.put(vizinho, peso);
    }
    public No(String nome, int x, int y) {
        this.nome = nome;
        this.x = x;
        this.y = y;
    }
    public No(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Map<No, Integer> getVizinhos() {
        return vizinhos;
    }
}
