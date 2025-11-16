import java.util.*;

public class KruskalMap {

    // Estrutura para Union-Find (conjuntos disjuntos)
    private static class Subconjunto {
        String pai;
        int rank;

        Subconjunto(String pai) {
            this.pai = pai;
            this.rank = 0;
        }
    }

    private Map<String, Subconjunto> subconjuntos = new HashMap<>();

    // 🔗 Encontra o "pai" de um nó no conjunto (com compressão de caminho)
    private String encontrar(String nome) {
        Subconjunto sub = subconjuntos.get(nome);
        if (!sub.pai.equals(nome)) {
            // APOS CHEGAR AO FINAL DA PILHA DE RECURSÃO, ATUALIZA O PAI DE CADA NÓ PARA O DA RAIZ
            sub.pai = encontrar(sub.pai);
        }
        return sub.pai;
    }

    // 🔗 Une dois subconjuntos com base no rank
    private void unir(String nome1, String nome2) {
        String raiz1 = encontrar(nome1);
        String raiz2 = encontrar(nome2);

        if (raiz1.equals(raiz2)) return;

        Subconjunto sub1 = subconjuntos.get(raiz1);
        Subconjunto sub2 = subconjuntos.get(raiz2);

        if (sub1.rank < sub2.rank) {
            sub1.pai = raiz2;
        } else if (sub1.rank > sub2.rank) {
            sub2.pai = raiz1;
        } else {
            sub2.pai = raiz1;
            sub1.rank++;
        }
    }

    // ⚙️ Executa o algoritmo de Kruskal
    public void calcular(Map<String, No> grafo) {

        Runtime runtime = Runtime.getRuntime();
        runtime.gc(); // limpa memória antes da medição

        long memoriaAntes = runtime.totalMemory() - runtime.freeMemory();
        long inicio = System.nanoTime();

        // 1️⃣ Monta lista de todas as arestas
        List<Aresta> arestas = new ArrayList<>();
        for (No no : grafo.values()) {
            for (Map.Entry<No, Integer> vizinho : no.getVizinhos().entrySet()) {
                // apenas os nós com nome menor para evitar duplicatas podem adicionar os vizinhos
                if (no.getNome().compareTo(vizinho.getKey().getNome()) < 0) {
                    arestas.add(new Aresta(no, vizinho.getKey(), vizinho.getValue()));
                }
            }
        }

        // 2️⃣ Ordena arestas pelo peso (menor primeiro)
        arestas.sort(Comparator.comparingDouble(Aresta::getPeso));

        // 3️⃣ Inicializa subconjuntos
        for (String nome : grafo.keySet()) {
            subconjuntos.put(nome, new Subconjunto(nome));
        }

        List<Aresta> resultado = new ArrayList<>();

        // 4️⃣ Percorre as arestas em ordem crescente
        for (Aresta a : arestas) {
            String raiz1 = encontrar(a.getNo1().getNome());
            String raiz2 = encontrar(a.getNo2().getNome());

            // Se não formarem ciclo, adiciona à MST
            if (!raiz1.equals(raiz2)) {
                resultado.add(a);
                unir(raiz1, raiz2);
            }
        }

        long fim = System.nanoTime();
        long memoriaDepois = runtime.totalMemory() - runtime.freeMemory();
        long memoriaUsada = (memoriaDepois - memoriaAntes) / (1024 * 1024);
        double tempoSegundos = (fim - inicio) / 1_000_000_000.0;

        // 5️⃣ Exibe resultado
        double custoTotal = 0;

        System.out.println("\n🌉 Arestas da Árvore Geradora Mínima (Kruskal):");

        // for (Aresta a : resultado) {
        //     System.out.printf("   %s -- %s (peso %.1f)%n", a.getNo1().getNome(), a.getNo2().getNome(), a.getPeso());
        //     custoTotal += a.getPeso();
        // }
        System.out.printf("Kruskal Tempo: %.6f segundos%n", tempoSegundos);
        System.out.printf("Kruskal Memória usada: %d MB%n", memoriaUsada);
        System.out.printf("O Custo total: %.1f%n", custoTotal);
    }
}
