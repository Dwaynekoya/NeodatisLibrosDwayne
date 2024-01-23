import controlador.ControlBBDD;
import modelo.Autor;
import org.neodatis.odb.Objects;
import tests.Test;


public class Main {
    public static void main(String[] args) {
        //Todo: USER LOGIN
        ControlBBDD controlBBDD = new ControlBBDD();
        Test.test1(controlBBDD);
        //LAST STEP: close db
        controlBBDD.cerrarBBDD();
    }

}