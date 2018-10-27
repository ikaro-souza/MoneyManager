package main_system;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Parcela {
    private final Date data_de_pagamento;  
    private boolean paga;    
    private final float valor;     
    //formatador de data.
    private final SimpleDateFormat sdf = new SimpleDateFormat(Sistema.DATE_FORMAT);
    
    /**
     * @param data data de pagamento.
     * @param paga se a parcela já foi paga.
     * @param val valor da parcela.
     */
    public Parcela(Date data, boolean paga, float val) {
        this.data_de_pagamento = data;
        this.paga = paga;
        this.valor = val;
    }

    public void setPaga(boolean paga) {
        this.paga = paga;
    }
    
    public Date getData() {
        return data_de_pagamento;
    }

    public boolean isPaga() {
        return paga;
    }

    public float getValor() {
        return valor;
    }

    //retorna a string formata da parcela
    //seguindo o padrão: data-(s se estiver paga e n se não estiver)-valor da parcela
    //exemplo: 01/06/2018-s-50
    @Override
    public String toString() {
        return String.format("PARCELA\n%s-%s-%s\n", 
                                       sdf.format(data_de_pagamento),
                                       (paga) ? "s":"n",
                                       valor
        );
    }
}
