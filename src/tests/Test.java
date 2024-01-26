package tests;

import controlador.ControlBBDD;
import modelo.Autor;
import org.neodatis.odb.Objects;

import java.text.ParseException;

public class Test {
    public static void test1(){
        try {
            ControlBBDD.insertarDatosPrueba();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        ControlBBDD.visualizarTodoConsola();
        Objects autores = ControlBBDD.buscar("nombre", "Jane", Autor.class);
        Autor jane = (Autor) autores.getFirst();
        System.out.printf("Encontrado: %s %n", jane);
        System.out.printf("Borrando... %n");
        //controlBBDD.eliminar("nombre", "Rick", Autor.class);
        ControlBBDD.visualizarTodoConsola();
    }
}
