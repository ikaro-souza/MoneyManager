/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main_system;

/**
 *
 * @author ikaro
 */
public enum Tipo {
    N(1), //normal
    F(2), //fixa
    P(3); //parcelada
    
    int value;
    String text;
    
    Tipo(int value) {
        this.value = value;
        
        switch(value) {
            case 1:
                this.text = "Normal";
                break;
            case 2:
                this.text = "Fixa";
                break;
            case 3:
                this.text = "Parcelada";
                break;
            default:
                this.text = "Normal";
        }
    }
    
    public int getValue() {
        return this.value;
    }
    
    public String getText() {
        return this.text;
    }
    
    public static Tipo fromString(String text) {
        for (Tipo t : Tipo.values()) {
            if (t.text.equalsIgnoreCase(text)) {
                return t;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
    
}
