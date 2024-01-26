package tests;

import controlador.ControlBBDD;
import modelo.Autor;
import modelo.Libro;
import org.neodatis.odb.Objects;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
        testBusquedaAutor();
    }

    public static void testBusquedaAutor() {
        Objects autores = ControlBBDD.buscar("nombre", "Jane", Autor.class);
        Autor autor = (Autor) autores.getFirst();
        System.out.println(autor);
        Objects libros = ControlBBDD.buscar("autor", autor, Libro.class);
        System.out.println(libros);
        Map<String, Object> criterios = new HashMap<>();
        criterios.put("autor.nombre", autor.getNombre());
        criterios.put("autor.apellidos", autor.getApellidos());
        libros = ControlBBDD.busquedaCompleja(Libro.class, criterios);
        System.out.println(libros);
    }

    public static void testBusquedaFecha() {
        LocalDate january262024 = LocalDate.of(2024, 1, 26);

        // Convert LocalDate to Date
        Date today = Date.from(january262024.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Objects libros = ControlBBDD.buscar("fecha_lanzamiento", today, Libro.class);
        System.out.println(libros);
    }
}
