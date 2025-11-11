import java.util.*;

public class PrimMap {
    private Map<String, Integer> custoAresta = new HashMap<>();
    private Map<String, String> predecessores = new HashMap<>();
    private Set<String> visitados = new HashSet<>();

    public void calcular(No inicial, Map<String, No> grafo) {

        Runtime runtime = Runtime.getRuntime();
        runtime.gc(); // força limpeza de memória antes da medição
        long memoriaAntes = runtime.totalMemory() - runtime.freeMemory();
        long inicio = System.nanoTime();

        // Inicializa custos: infinito para todos, exceto o inicial
        for (String nome : grafo.keySet()) {
            custoAresta.put(nome, Integer.MAX_VALUE);
        }
        custoAresta.put(inicial.getNome(), 0);

        // Fila de prioridade baseada no menor custo de aresta
        PriorityQueue<No> fila = new PriorityQueue<>(
            Comparator.comparingInt(no -> custoAresta.getOrDefault(no.getNome(), Integer.MAX_VALUE))
        );
        fila.addAll(grafo.values());

        // Executa o algoritmo de Prim
        while (!fila.isEmpty()) {
            No atual = fila.poll();
            String nomeAtual = atual.getNome();

            // Pula nós já processados
            if (visitados.contains(nomeAtual)) continue;
            visitados.add(nomeAtual);

            // Percorre vizinhos
            for (Map.Entry<No, Integer> vizinho : atual.getVizinhos().entrySet()) {
                String nomeVizinho = vizinho.getKey().getNome();
                int peso = vizinho.getValue();

                // Se o vizinho ainda não foi visitado e o peso é menor que o custo atual
                if (!visitados.contains(nomeVizinho) && peso < custoAresta.get(nomeVizinho)) {
                    custoAresta.put(nomeVizinho, peso);
                    predecessores.put(nomeVizinho, nomeAtual);

                    // Atualiza a prioridade
                    fila.remove(vizinho.getKey());
                    fila.add(vizinho.getKey());
                }
            }
        }

        long fim = System.nanoTime();
        long memoriaDepois = runtime.totalMemory() - runtime.freeMemory();
        long memoriaUsada = (memoriaDepois - memoriaAntes) / (1024 * 1024); // em MB
        double tempoSegundos = (fim - inicio) / 1_000_000_000.0;


        // Exibe a Árvore Geradora Mínima (MST)
        // int custoTotal = 0;
        // System.out.println("\n Árvore Geradora Mínima (Prim):");
        // for (Map.Entry<String, String> entrada : predecessores.entrySet()) {
        //     String filho = entrada.getKey();
        //     String pai = entrada.getValue();
        //     int peso = grafo.get(pai).getVizinhos().get(grafo.get(filho));
        //     custoTotal += peso;
        //     System.out.printf("   %s --(%d) -- %s%n", pai, peso, filho);
        // }

        System.out.printf("PRIM Tempo: %.6f segundos%n", tempoSegundos);
        System.out.printf(" PRIM usada: %d MB%n", memoriaUsada);
        // System.out.println("\n Custo total da árvore: " + custoTotal);
    }
}
