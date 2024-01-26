import controlador.ControlLogin;
import javafx.application.Application;
import controlador.ControlMainScreen;


public class Main {
    private static boolean skipLogin = false;
    public static void main(String[] args) {
        if (skipLogin){
            Application.launch(ControlMainScreen.class);
        }else Application.launch(ControlLogin.class);
    }

}