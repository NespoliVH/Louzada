package service;

import model.Pedido;

import java.util.*;


public class PizzariaService {

    // REQUISITO 1: Cadastro de Sabores (Set<String>)
    private Set<String> saboresDisponiveis = new HashSet<>();

    // REQUISITO 2: Registro de Pedidos (ArrayList<Pedido>)
    private List<Pedido> pedidosAbertos = new ArrayList<>();

    // REQUISITO 3: Fila de Entregas (Queue<Pedido>)
    private Queue<Pedido> filaEntregas = new LinkedList<>();

    // REQUISITO 4: Pedidos Prioritários (PriorityQueue<Pedido>)
    // Define o comparador para a fila prioritária (menor tempo de preparo sai primeiro)
    private Comparator<Pedido> comparatorTempoPreparo = Comparator.comparingInt(Pedido::getTempoPreparoEstimado);
    private PriorityQueue<Pedido> filaPrioritaria = new PriorityQueue<>(comparatorTempoPreparo);

    // REQUISITO 5: Histórico de Pedidos Cancelados (Stack<Pedido>)
    private Stack<Pedido> pedidosCancelados = new Stack<>();

    // REQUISITO 6: Registro de Vendas por Sabor (Map<String, Integer>)
    private Map<String, Integer> vendasPorSabor = new HashMap<>();


    // --- MÉTODOS REQUISITO 1: Sabores (Set) ---

    public boolean adicionarSabor(String sabor) {
        System.out.println("Tentando adicionar sabor: " + sabor);
        return saboresDisponiveis.add(sabor);
    }

    public void listarSabores() {
        System.out.println("--- Sabores Disponíveis ---");
        saboresDisponiveis.forEach(System.out::println);
    }

    public boolean verificarSabor(String sabor) {
        return saboresDisponiveis.contains(sabor);
    }

    // --- MÉTODOS REQUISITO 2: Pedidos Abertos (ArrayList) ---

    public void adicionarPedido(Pedido pedido) {
        pedidosAbertos.add(pedido);
        System.out.println("Pedido " + pedido.getNumeroPedido() + " adicionado.");
    }

    public void listarPedidosAbertos() {
        System.out.println("--- Pedidos Abertos (ArrayList) ---");
        pedidosAbertos.forEach(System.out::println);
    }

    public Pedido buscarPedidoPorNumero(int numero) {
        // Pesquisa linear
        for (Pedido p : pedidosAbertos) {
            if (p.getNumeroPedido() == numero) {
                return p;
            }
        }
        return null; // Não encontrado
    }

    public void ordenarPedidosPorValor() {
        System.out.println("\nOrdenando pedidos por VALOR...");
        pedidosAbertos.sort(Comparator.comparingDouble(Pedido::getValor));
        listarPedidosAbertos();
    }

    public void ordenarPedidosPorCliente() {
        System.out.println("\nOrdenando pedidos por NOME DO CLIENTE...");
        pedidosAbertos.sort(Comparator.comparing(Pedido::getNomeCliente));
        listarPedidosAbertos();
    }

    // --- MÉTODOS REQUISITO 3: Fila de Entregas (Queue) ---

    public void adicionarAFilaEntrega(Pedido pedido) {
        // offer() é preferível a add() pois não lança exceção se a fila estiver cheia (em filas com capacidade restrita)
        filaEntregas.offer(pedido);
        System.out.println("Pedido " + pedido.getNumeroPedido() + " adicionado à fila de entrega.");
    }

    public Pedido processarProximaEntrega() {
        // poll() remove e retorna o elemento da cabeça, ou null se a fila estiver vazia.
        Pedido proximo = filaEntregas.poll();
        if (proximo != null) {
            System.out.println("Processando entrega: " + proximo.getNumeroPedido());
            registrarVenda(proximo); // Registra a venda (Requisito 6)
        } else {
            System.out.println("Fila de entregas vazia.");
        }
        return proximo;
    }

    public Pedido consultarProximaEntrega() {
        // peek() retorna o elemento da cabeça sem removê-lo, ou null se vazio.
        return filaEntregas.peek();
    }

    public boolean filaEntregasVazia() {
        return filaEntregas.isEmpty();
    }

    // --- MÉTODOS REQUISITO 4: Fila Prioritária (PriorityQueue) ---

    public void adicionarAFilaPrioritaria(Pedido pedido) {
        filaPrioritaria.offer(pedido);
        System.out.println("Pedido " + pedido.getNumeroPedido() + " (VIP) adicionado à fila prioritária (Preparo: " + pedido.getTempoPreparoEstimado() + " min).");
    }

    public Pedido processarProximaEntregaPrioritaria() {
        Pedido proximoVip = filaPrioritaria.poll();
        if (proximoVip != null) {
            System.out.println("Processando entrega PRIORITÁRIA: " + proximoVip.getNumeroPedido() + " (Preparo: " + proximoVip.getTempoPreparoEstimado() + " min)");
            registrarVenda(proximoVip); // Registra a venda (Requisito 6)
        } else {
            System.out.println("Fila prioritária vazia.");
        }
        return proximoVip;
    }

    // --- MÉTODOS REQUISITO 5: Cancelados (Stack) ---

    public void cancelarPedido(Pedido pedido) {
        // push() adiciona ao topo da pilha
        pedidosCancelados.push(pedido);
        System.out.println("Pedido " + pedido.getNumeroPedido() + " CANCELADO e movido para o histórico.");
        // Remove da lista de pedidos abertos, se estiver lá
        pedidosAbertos.remove(pedido);
    }

    public Pedido verUltimoCancelado() {
        // peek() olha o topo sem remover
        if (!pedidosCancelados.isEmpty()) {
            return pedidosCancelados.peek();
        }
        return null;
    }

    public Pedido reverterUltimoCancelamento() {
        // pop() remove e retorna o topo
        if (!pedidosCancelados.isEmpty()) {
            Pedido revertido = pedidosCancelados.pop();
            System.out.println("Revertendo cancelamento: " + revertido.getNumeroPedido());
            adicionarPedido(revertido); // Adiciona de volta aos pedidos abertos
            return revertido;
        }
        return null;
    }

    // --- MÉTODOS REQUISITO 6: Vendas por Sabor (Map) ---

    // Método auxiliar chamado ao processar entregas
    private void registrarVenda(Pedido pedidoEntregue) {
        String sabor = pedidoEntregue.getSabor();
        // getOrDefault é perfeito para contadores:
        // Pega o valor atual de 'sabor', ou 0 se não existir, e então soma 1.
        int contagemAtual = vendasPorSabor.getOrDefault(sabor, 0);
        vendasPorSabor.put(sabor, contagemAtual + 1);
    }

    public void exibirRankingVendas() {
        System.out.println("\n--- 🏆 Ranking de Vendas por Sabor (Map) 🏆 ---");
        if (vendasPorSabor.isEmpty()) {
            System.out.println("Nenhuma venda registrada.");
            return;
        }

        // Para ordenar um Map por valor, convertemos para uma Lista de Entradas (Map.Entry)
        List<Map.Entry<String, Integer>> listaRanking = new ArrayList<>(vendasPorSabor.entrySet());

        // Ordena a lista em ordem decrescente de valor (vendas)
        listaRanking.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        int rank = 1;
        for (Map.Entry<String, Integer> entry : listaRanking) {
            System.out.println(rank + "º: " + entry.getKey() + " (" + entry.getValue() + " vendas)");
            rank++;
        }
    }

    public int getTotalVendidoPorSabor(String sabor) {
        return vendasPorSabor.getOrDefault(sabor, 0);
    }

    public void listarSaboresVendidos() {
        System.out.println("--- Sabores com vendas registradas ---");
        vendasPorSabor.keySet().forEach(System.out::println);
    }
}