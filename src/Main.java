import controlador.ControlBBDD;
import javafx.application.Application;
import org.neodatis.odb.ODB;
import tests.Test;
import vista.Login;
import vista.MainScreen;


public class Main {
    private static boolean skipLogin = true;
    public static void main(String[] args) {
        //TODO: backups
        //ControlBBDD controlBBDD = new ControlBBDD();
        //controlBBDD.backup(); The process cannot access the file because another process has locked a portion of the file
        //Test.test1(controlBBDD);
        if (skipLogin){
            Application.launch(MainScreen.class);
        }else Application.launch(Login.class);

    }

}