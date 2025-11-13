package model;

import java.util.Objects;


public class Pedido {

    private int numeroPedido;
    private String nomeCliente;
    private String sabor;
    private String tamanho; // P, M, G
    private double valor;
    private int tempoPreparoEstimado; // Em minutos (para PriorityQueue)

    public Pedido(int numeroPedido, String nomeCliente, String sabor, String tamanho, double valor, int tempoPreparoEstimado) {
        this.numeroPedido = numeroPedido;
        this.nomeCliente = nomeCliente;
        this.sabor = sabor;
        this.tamanho = tamanho;
        this.valor = valor;
        this.tempoPreparoEstimado = tempoPreparoEstimado;
    }

    // Getters
    public int getNumeroPedido() {
        return numeroPedido;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public String getSabor() {
        return sabor;
    }

    public String getTamanho() {
        return tamanho;
    }

    public double getValor() {
        return valor;
    }

    public int getTempoPreparoEstimado() {
        return tempoPreparoEstimado;
    }

    @Override
    public String toString() {
        return "Pedido [Nº " + numeroPedido +
                ", Cliente: " + nomeCliente +
                ", Sabor: " + sabor +
                ", Tamanho: " + tamanho +
                ", Valor: R$" + valor +
                ", Preparo: " + tempoPreparoEstimado + " min" +
                ']';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return numeroPedido == pedido.numeroPedido;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroPedido);
    }
}