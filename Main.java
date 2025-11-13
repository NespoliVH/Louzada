package app;

import model.Pedido;
import service.PizzeriaService;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        PizzeriaService svc = new PizzeriaService();
        Scanner sc = new Scanner(System.in);

        svc.adicionarSabor("Calabresa");
        svc.adicionarSabor("Mussarela");

        boolean run = true;
        while(run){
            System.out.println("1. Listar sabores");
            System.out.println("2. Adicionar sabor");
            System.out.println("0. Sair");
            int op = Integer.parseInt(sc.nextLine());
            switch(op){
                case 1 -> svc.listarSabores().forEach(System.out::println);
                case 2 -> {
                    System.out.print("Sabor: ");
                    System.out.println(svc.adicionarSabor(sc.nextLine()) ? "Adicionado" : "Já existe");
                }
                case 0 -> run = false;
            }
        }
    }
}
MODEL 
CLIENTE 
package model;

public class Cliente {
    private String nome;
    private String telefone;
    private String email;

    public Cliente(String nome, String telefone, String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.email =a email;
    }

    public String getNome() { return nome; }
    public String getTelefone() { return telefone; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return String.format("Cliente{name='%s', telefone='%s', email='%s'}", nome, telefone, email);
    }
}