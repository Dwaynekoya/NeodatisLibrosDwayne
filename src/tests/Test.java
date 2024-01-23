package tests;

import controlador.ControlBBDD;
import modelo.Autor;
import org.neodatis.odb.Objects;

public class Test {
    public static void test1(ControlBBDD controlBBDD){
        /*try {
            controlBBDD.insertarDatosPrueba();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }*/
        controlBBDD.visualizarTodoConsola();
        Objects autores = controlBBDD.buscar("nombre", "Jane", Autor.class);
        Autor jane = (Autor) autores.getFirst();
        System.out.printf("Encontrado: %s %n", jane);
        System.out.printf("Borrando... %n");
        controlBBDD.eliminar("nombre", "Rick", Autor.class);
        controlBBDD.visualizarTodoConsola();
    }
}
