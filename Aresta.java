public class Aresta {
    private No no1;
    private No no2;
    private double peso;

    public Aresta(No no1, No no2, double peso) {
        this.no1 = no1;
        this.no2 = no2;
        this.peso = peso;
    }

    public No getNo1() {
        return no1;
    }

    public No getNo2() {
        return no2;
    }

    public double getPeso() {
        return peso;
    }
}
