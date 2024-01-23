import controlador.ControlBBDD;
import javafx.application.Application;
import tests.Test;
import vista.Login;


public class Main {
    public static void main(String[] args) {
        //Todo: USER LOGIN
        ControlBBDD controlBBDD = new ControlBBDD();
        Test.test1(controlBBDD);
        Application.launch(Login.class);
    }

}