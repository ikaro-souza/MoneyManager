/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main_system;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import static main_system.Sistema.toCalendar;

/**
 *
 * @author ikaro
 */

public class ContaParcelada extends Conta {
    private final List<Parcela> parcelas = new ArrayList();                            
    private Parcela atual;                                                      
    private final int num_parcelas;               
    private final SimpleDateFormat sdf = new SimpleDateFormat(Sistema.DATE_FORMAT);
    private float valor_parcela;
    
    /**
     * @param desc Descrição da conta
     * @param primeira Data da primeira parcela
     * @param val Valor total da conta
     * @param parcelas Quantidade de parcelas da conta
     * @param foi_paga Se a conta foi ou não paga.
     */    
    public ContaParcelada(String desc, Date primeira, float val, int parcelas, boolean foi_paga) {
        super(desc, primeira, val, Tipo.P.getText(), foi_paga);
        this.num_parcelas = parcelas;
        setValorParcelas();
    }    
    /**
     * Define a parcela atual, que é a parcela posterior a última que foi paga.
     * @see #setValorParcelas() 
     */
    public void setParcelaAtual() {
        
        for(Parcela p : parcelas) {
            //se p não estiver paga, a parcela atual passa a ser p e o looping é encerrado.
            if(p.isPaga() == false) {
                atual = p;
                return;
            }
        }           
    }   

    /**
     * Define o valor de cada parcela.
     * @see #getParcelaAtual() 
     */
    public void setValorParcelas() {
        this.valor_parcela = valor / num_parcelas;
    }    
    
    /**
     * Retorna o número de parcelas da conta.
     * @return Número de parcelas da conta.
     * @see #getParcelaAtual() 
     * @see #getValorParcela() 
     */
    public int getNumParcelas() {
        return this.num_parcelas;
    }
    
    /**
     * Retorna a parcela atual da conta.
     * @return Parcela atual da conta.
     */
    public Parcela getParcelaAtual() {
        return this.atual;
    }
    
    /**
     * Retorna a lista de parcelas da conta.
     * @return Lista de parcelas da conta.
     */
    public List<Parcela> getParcelas() {
        return this.parcelas;
    }
    
    /**
     * Retorna o valor de cada parcela da conta.
     * @return Valor de cada parcela da conta.
     * @see #getValor() 
     */
    public float getValorParcela() {
        return this.valor_parcela;
    }
    
    /**
     * Adiciona uma parcela à conta.
     * @param data Data de pagamento da parcela.
     * @param paga Se a parcela foi ou não paga.
     * @param val Valor da parcela.
     * @see #gerarParcelas(java.util.Date, int, float)  
     */
    public void addParcela(Date data, boolean paga, float val) {
        this.parcelas.add(new Parcela(data, paga, val));
    }
    
    /**
     * Gera todas as parcelas a partir da data da primeira parcela.
     * @param primeira Data de pagamento da primeira parcela.
     * @param parcelas Quantidade de parcelas que devem ser geradas.
     * @param val Valor da conta.
     * @see #addParcela(java.util.Date, boolean, float) 
     */
    public void gerarParcelas(Date primeira, int parcelas, float val) {
        //a data é transformada em calendário para ser feita a manipulação de data
        //com mais facilidade.
        Calendar cal_primeira = toCalendar(primeira);
        //looping que incrementa o mês da data atual em 1 e depois 
        //adiciona a parcela com a data atual.
        do {
            //todas as parcelas cuja data e, mais especificamente o mês é menor que a data
            //e o mês, mais especificamente, o mês da data atual do sistema.
            boolean _paga = (cal_primeira.compareTo(toCalendar(Sistema.HOJE)) < 0 && 
                             cal_primeira.get(Calendar.MONTH) < toCalendar(Sistema.HOJE).get(Calendar.MONTH));
            //a parcela é adicionada à lista de parcelas
            addParcela(cal_primeira.getTime(), _paga, val/parcelas);
            //é acicinado 1 ao mês da data
            cal_primeira.add(Calendar.MONTH, 1);
        }while (this.parcelas.size() < parcelas); 
        
        //a data de pagametno da conta passa a ser a data de pagamento da 
        //última parcela.
        setData(this.parcelas.get(parcelas-1).getData());
    }             
    
    /**
     * Paga a parcela atual da conta.
     * @see #setParcelaAtual() 
     * @see #getParcelaAtual() 
     */
    public void pagarParcela() {
        //define o campo paga da parcela como true.
        atual.setPaga(true);
        
        //define a conta como paga se a parcela atual for a última da lista de parcelas
        if (atual == parcelas.get(num_parcelas-1)) {
            this.setPaga(true);
            return;
        }
        
        //se a parcela atual não for a última, ela passa a ser a sua parcela posterior.
        setParcelaAtual();
    }

    /**
     * Retorna a string formatada da conta,
     * seguindo o padrão: data da primeira parcela-descrição-valor-número de parcelas seguido das parcelas
     * exemplo: 01/06/2018-placa de vídeo-999.99-9 e logo abaixo cada parcela
     * @return String formata da conta.
     */
    @Override
    public String toString() {
        String resultado = "";
        resultado += String.format(
                "CONTA\n%s-%s-%s-%s-%s\n", 
                descricao,
                sdf.format(getData()),
                valor,
                num_parcelas,
                (paga) ? "s" : "n"
        );
        
        for(Parcela p : parcelas) {
           resultado += p.toString();
        }
        resultado += "FIMPARCELAS\n";
        return resultado;
    }
    
    
    public boolean equals(ContaParcelada outra) {
        return false;
    }
}
