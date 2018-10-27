/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main_system;

import java.util.Calendar;
import java.util.Date;
import static main_system.Sistema.SDF;
import static main_system.Sistema.toCalendar;

/**
 *
 * @author ikaro
 */
public class Conta {
    protected String descricao;            
    protected Date data_de_pagamento;      
    protected float valor;                 
    protected Tipo tipo; 
    protected boolean paga;
    
    /**
     * @param desc Descrição da conta.
     * @param data Data de pagamento da conta.
     * @param valor Valor da conta.
     * @param tipo Tipo da conta.
     * @param foi_paga Se a conta foi ou não paga.
     */
    public Conta(String desc, Date data, float valor, String tipo, boolean foi_paga) {
        this.descricao = desc;
        this.data_de_pagamento = data;
        this.valor = valor;
        this.tipo = Tipo.fromString(tipo);
        this.paga = foi_paga;
    }    

    /**
     * Define a descrição da conta.
     * @param descricao Descrição da conta.
     * @see #setData(java.util.Date)
     * @see #setPaga(boolean) 
     * @see #setValor(float) 
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Define a data de pagamento da conta.
     * @param data_de_pagamento Data de pagamento da conta.
     * @see #setDescricao(java.lang.String)
     * @see #setPaga(boolean) 
     * @see #setValor(float) 
     */
    public void setData(Date data_de_pagamento) {
        this.data_de_pagamento = data_de_pagamento;
    }

    /**
     * Define o valor da conta;
     * @param valor Valor da conta.
     * @see #setDescricao(java.lang.String) 
     * @see #setPaga(boolean) 
     * @see #setData(java.util.Date) 
     */
    public void setValor(float valor) {
        this.valor = valor;
    }

    /**
     * Define se a conta foi ou não paga.
     * @param paga Se a conta foi paga.
     * @see #setDescricao(java.lang.String) 
     * @see #setData(java.util.Date) 
     * @see #setValor(float) 
     */
    public void setPaga(boolean paga) {
        this.paga = paga;
    }

    /**
     * Retorna a descrição da conta.
     * @return Descrição da conta.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Retorna a data de pagamento da conta.
     * @return Data de pagamento da conta.
     */
    public Date getData() {
        return data_de_pagamento;
    }

    /**
     * Retorna o valor da conta.
     * @return Valor da conta.
     */
    public float getValor() {
        return valor;
    }

    /**
     * Retorna o tipo da conta.
     * @return Tipo da conta.
     * @see main_system.Tipo
     */
    public Tipo getTipo() {
        return tipo;
    }

    /**
     * Retorna se a conta foi ou não paga.
     * @return true se a contar estiver paga, false se não estiver.
     */
    public boolean isPaga() {
        return paga;
    }
    
    /**
     * Atualiza a data de pagamento de uma conta fixa, aumentado o valor do mês em 1.
     */
    public void atualizarData() {
        //guarda o calendário com a atual data de pagamento
        Calendar cal = toCalendar(data_de_pagamento);
        //adiciona o mês em 1
        cal.roll(Calendar.MONTH, 1);
        //define a nova data com o valor atualizado do calendário 
        setData(cal.getTime());
    }
    
    @Override
    public String toString() {
        return String.format("CONTA\n%s-%s-%s-%s-%s\n", 
                tipo.getText(), 
                descricao, 
                Sistema.SDF.format(data_de_pagamento), 
                valor,
                (paga) ? "s" : "n"
        );
    }
    

    public boolean equals(Conta outra) {
        if(this.descricao.equals(outra.getDescricao()) &&
           SDF.format(data_de_pagamento).equals(SDF.format(outra.getData())) &&
           this.valor == outra.getValor() &&
           this.tipo.equals(outra.getTipo()) &&
           this.isPaga() == outra.isPaga()) {
            return true;
        }
        
        return false;
    }
}
