/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main_system;

import gui.LoginFrame;
import gui.MainFrame;
import java.io.IOException;
import java.text.ParseException;
/**
 *
 * @author ikaro
 */
public class Main {
    
    public static void main(String[] args) throws IOException, ParseException {
        Sistema.inicializar();
        
        if(Sistema.isFirstRun()) {
            new LoginFrame().setVisible(true);
        }else {
            new MainFrame().setVisible(true);
        }
    }
}
