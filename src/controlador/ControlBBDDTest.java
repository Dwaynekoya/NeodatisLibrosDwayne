package controlador;

import modelo.Autor;

import org.neodatis.odb.Objects;


import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ControlBBDDTest {
//las clases que pruebo son las usadas para buscar en la BBDD
    @org.junit.jupiter.api.Test
    void buscar() {
        String campo = "nombre";
        String valor = "Jane";
        Objects resultado=ControlBBDD.buscar(campo, valor, Autor.class);
        Autor autor=(Autor) resultado.getFirst();
        String nombre=autor. getNombre();
        assertEquals(nombre, valor);
    }

    @org.junit.jupiter.api.Test
    void busquedaCompleja() {
        String[] campos={"nombre", "apellidos"};
        String[] valores={"Jane", "Austen"};
        Map<String,Object> criteriosBusqueda = new HashMap<>();
        for (int i=0; i<campos.length;i++) criteriosBusqueda.put(campos[i], valores[i]);
        Objects resultado2=ControlBBDD.busquedaCompleja(Autor.class, criteriosBusqueda);
        Autor autor=(Autor) resultado2.getFirst();
        String apellidos=autor. getApellidos();
        assertEquals(valores[1], apellidos);
    }
}