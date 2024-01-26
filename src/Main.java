import controlador.ControlBBDD;
import javafx.application.Application;
import org.neodatis.odb.ODB;
import tests.Test;
import vista.Login;
import vista.MainScreen;


public class Main {
    private static boolean skipLogin = true;
    public static void main(String[] args) {
        //Test.test1();
        if (skipLogin){
            Application.launch(MainScreen.class);
        }else Application.launch(Login.class);
    }

}