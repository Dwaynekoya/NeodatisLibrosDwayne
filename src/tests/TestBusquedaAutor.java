package tests;

import controlador.ControlBBDD;
import modelo.Autor;
import modelo.Libro;
import org.neodatis.odb.Objects;

import java.util.HashMap;
import java.util.Map;

public class TestBusquedaAutor {
    public static void main(String[] args) {
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
}
