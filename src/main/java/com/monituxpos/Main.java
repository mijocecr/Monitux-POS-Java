package com.monituxpos;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or

import com.monituxpos.Clases.*;
import com.monituxpos.Ventanas.V_Initial_Setup;
import com.monituxpos.Ventanas.V_Login;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
   
    
    public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        boolean ajustesCreados = AppSettings.getAjustes_Creados();
        boolean primerArranque = AppSettings.getPrimer_Arranque();
        boolean empresaCreada = AppSettings.getEmpresa_Creada();

        JFrame ventana;

        if (ajustesCreados && !primerArranque && empresaCreada ) {
            ventana = new V_Login();
        } else {
            ventana = new V_Initial_Setup();
        }

        ventana.setVisible(true);
        ventana.requestFocus();
    });
}

    
}