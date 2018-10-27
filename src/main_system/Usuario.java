/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main_system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ikaro
 */

public class Usuario {
    //nome do usuário
    private String nome;    
    //faturamento mensal do usuario
    private float renda;
    //lista de contas normais ou fixas
    private List<Conta> contas = new ArrayList(); 
    //lista de contas parceladas
    private List<ContaParcelada> parceladas = new ArrayList();

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setRenda(float renda) {
        this.renda = renda;
    }

    public String getNome() {
        return this.nome;
    }

    public float getRenda() {
        return this.renda;
    }

    public List<Conta> getContas() {
        return this.contas;
    }

    public List<ContaParcelada> getParceladas() {
        return this.parceladas;
    }
    
    /**
     * Adiciona a conta nova à lista de contas normais ou fixas.
     * @param nova Conta que será adicionada.
     */
    public void addConta(Conta nova) {
        contas.add(nova);
    }
    
    /**
     * Adiciona a conta nova à lista de contas parceladas.
     * @param nova Conta que será adicionada.
     */
    public void addConta(ContaParcelada nova) {
        parceladas.add(nova);
    }
    
    /**
     * Remove a conta especificada.
     * @param conta Conta que será removida.
     */
    public void removerConta(Conta conta) {
        contas.remove(conta);
    }
    
    /**
     * Remove a conta parcelada especificada.
     * @param conta Conta que será removida.
     */
    public void removerConta(ContaParcelada conta) {
        parceladas.remove(conta);
    }
    
    /**
     * Usando o algoritmo bubble sort, ordena em ordem crescente as contas do usuário
     * segundo seu tipo, onde: Normal = 1, Fixa = 2, Parcelada = 3.
     */
    public void ordenarPorTipo() {
        int tamanho_contas = contas.size();         //guarda a quantidade de contas
        int tamanho_parceladas = parceladas.size(); //guarda a quantidade de contas parceladas
        
        //looping que ordena a lista de contas normais ou fixas.
        for(int i = 0; i < tamanho_contas; i++) {
            for(int j = 1; j < (tamanho_contas-i); j++) {
                Conta temp1 = contas.get(j-1);
                Conta temp2 = contas.get(j);
                
                if(temp1.tipo.getValue() > temp2.tipo.getValue()) {
                    contas.set(j-1, temp2);
                    contas.set(j, temp1);
                }
            }
        }
        
        for(int i = 0; i < tamanho_parceladas; i++) {
            for(int j = 1; j < (tamanho_parceladas-i); j++) {
                Conta temp1 = contas.get(j-1);
                Conta temp2 = contas.get(j);
                
                if(temp1.tipo.getValue() > temp2.tipo.getValue()) {
                    contas.set(j-1, temp2);
                    contas.set(j, temp1);
                }
            }
        }
    }

    /**
     * 
     * @return String formatada com as informações do usuário.
     */
    @Override
    public String toString() {
        String resultado = "";
        resultado += String.format("%s\n", nome);
        
        for(Conta c : contas) {
            resultado += c.toString();
        }        
        for(ContaParcelada c : parceladas) {
            resultado += c.toString();
        }
        
        return resultado;
    }
}
