package vista;

import controlador.ControlBBDD;
import javafx.application.Application;
import tests.Test;
import vista.Login;
import vista.MainScreen;


public class Main {
    private static boolean skipLogin = true;
    public static void main(String[] args) {
        //TODO: backups
        //ControlBBDD controlBBDD = new ControlBBDD();
        //Test.test1(controlBBDD);
        if (skipLogin){
            Application.launch(MainScreen.class);
        }else Application.launch(Login.class);

    }

}