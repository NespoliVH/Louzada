package app;

import model.Pedido;
import service.PizzariaService;

public class Main {

    public static void main(String[] args) {
        PizzariaService pizzaria = new PizzariaService();
        System.out.println("  BEM-VINDO À PIZZARIA JAVA DELÍCIA  ");

        // --- REQUISITO 1: Cadastro de Sabores (Set) ---
        System.out.println("\n===== TAREFA 1: Cadastro de Sabores (Set) =====");
        pizzaria.adicionarSabor("Calabresa");
        pizzaria.adicionarSabor("Mussarela");
        pizzaria.adicionarSabor("Quatro Queijos");
        boolean resultado = pizzaria.adicionarSabor("Calabresa"); // Tentativa de duplicata
        System.out.println("Resultado da adição duplicada: " + resultado); // Deve ser 'false'
        pizzaria.listarSabores();

        // --- REQUISITO 2: Registro de Pedidos (ArrayList) ---
        System.out.println("\n===== TAREFA 2: Registro de Pedidos (ArrayList) =====");
        Pedido p1 = new Pedido(101, "Ana", "Mussarela", "G", 55.00, 30);
        Pedido p2 = new Pedido(102, "Bruno", "Calabresa", "M", 48.00, 25);
        Pedido p3 = new Pedido(103, "Carla", "Quatro Queijos", "P", 40.00, 20);

        pizzaria.adicionarPedido(p1);
        pizzaria.adicionarPedido(p2);
        pizzaria.adicionarPedido(p3);
        pizzaria.listarPedidosAbertos();

        // Buscando pedido
        System.out.println("\nBuscando pedido 102: " + pizzaria.buscarPedidoPorNumero(102));

        // Ordenando (Comparator)
        pizzaria.ordenarPedidosPorValor();
        pizzaria.ordenarPedidosPorCliente();

        // --- REQUISITOS 3, 4 e 5 (Simulação de Operação) ---
        System.out.println("\n===== TAREFAS 3, 4, 5: Simulação da Operação (Queues e Stack) =====");

        // Novos pedidos chegando...
        Pedido p4_vip = new Pedido(104, "Vitor VIP", "Calabresa", "G", 50.00, 10); // VIP! (10 min)
        Pedido p5 = new Pedido(105, "Debora", "Mussarela", "M", 45.00, 25);
        Pedido p6_vip = new Pedido(106, "Marcos VIP", "Portuguesa", "G", 60.00, 15); // VIP! (15 min)

        // Adiciona sabor "Portuguesa" que não existia
        pizzaria.adicionarSabor("Portuguesa");

        // Adiciona pedidos às listas de abertos
        pizzaria.adicionarPedido(p4_vip);
        pizzaria.adicionarPedido(p5);
        pizzaria.adicionarPedido(p6_vip);

        // Cliente Carla (Pedido 103) cancela o pedido!
        pizzaria.cancelarPedido(p3); // Move p3 para a Stack de cancelados

        // --- Processando Pedidos para Entrega ---
        // A lógica da pizzaria é:
        // 1. Enviar pedidos VIPs (PriorityQueue)
        // 2. Enviar pedidos normais (Queue)

        // Enviando pedidos abertos (p1, p2, p4, p5, p6) para as filas corretas
        System.out.println("\nEnviando pedidos para as filas de entrega...");
        pizzaria.adicionarAFilaPrioritaria(p4_vip); // 10 min
        pizzaria.adicionarAFilaPrioritaria(p6_vip); // 15 min
        
        pizzaria.adicionarAFilaEntrega(p1); // Pedido Ana
        pizzaria.adicionarAFilaEntrega(p2); // Pedido Bruno
        pizzaria.adicionarAFilaEntrega(p5); // Pedido Debora

        // --- Simulando Entregas (Processando as filas) ---
        // A pizzaria sempre processa a fila VIP primeiro.
        System.out.println("\n---  PROCESSANDO ENTREGAS  ---");
        System.out.println("--- Processando VIPs (PriorityQueue) ---");
        pizzaria.processarProximaEntregaPrioritaria(); // Deve sair o p4 (10 min)
        pizzaria.processarProximaEntregaPrioritaria(); // Deve sair o p6 (15 min)
        pizzaria.processarProximaEntregaPrioritaria(); // Fila vazia

        System.out.println("\n--- Processando Entregas Normais (Queue - FIFO) ---");
        System.out.println("Próximo da fila normal (peek): " + pizzaria.consultarProximaEntrega().getNumeroPedido()); // p1
        pizzaria.processarProximaEntrega(); // Deve sair o p1
        pizzaria.processarProximaEntrega(); // Deve sair o p2
        pizzaria.processarProximaEntrega(); // Deve sair o p5
        pizzaria.processarProximaEntrega(); // Fila vazia

        // --- REQUISITO 5: Verificando o Histórico de Cancelados (Stack) ---
        System.out.println("\n--- Verificando Pilha de Cancelados (Stack) ---");
        System.out.println("Último pedido cancelado (peek): " + pizzaria.verUltimoCancelado().getNumeroPedido()); // p3
        // pizzaria.reverterUltimoCancelamento(); // Descomente para testar o 'pop'


        // --- REQUISITO 6: Registro de Vendas (Map) ---
        System.out.println("\n===== TAREFA 6: Relatório de Vendas (Map) =====");
        // O registro de vendas (pizzaria.registrarVenda()) foi chamado
        // automaticamente dentro de processarProximaEntrega() e processarProximaEntregaPrioritaria().

        pizzaria.exibirRankingVendas();

        // Consultando total específico
        System.out.println("\nTotal de vendas de 'Mussarela': " + pizzaria.getTotalVendidoPorSabor("Mussarela"));
        System.out.println("Total de vendas de 'Quatro Queijos': " + pizzaria.getTotalVendidoPorSabor("Quatro Queijos")); // Deve ser 0 (foi cancelado)

        System.out.println("\n===== FIM DA SIMULAÇÃO =====");
    }

}
