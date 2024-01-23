package controlador;

import modelo.Autor;
import modelo.Libro;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.ICriterion;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ControlBBDD {
    private static ODB odb;

    public ControlBBDD() {
        odb = ODBFactory.open("EDITORIAL.ND");
    }
    public void cerrarBBDD(){
        odb.close();
    }

    /***
     * Función que realiza consulta de búsqueda en BBDD Neodatis. Devuelve nulo si no hay resultados, u Objects con
     * los resultados
     * @param campo
     * @param valor
     * @param clase
     * @return
     */
    public static Objects buscar(String campo, String valor, Class clase){

        ICriterion criterion = Where.equal(campo,valor);
        IQuery query = new CriteriaQuery(clase, criterion);
        Objects autores = odb.getObjects(query);
        if (autores.size()==0){
            return null;
        }
        else {
            return autores;
        }
    }

    /**
     * Este método llama al de búsqueda para tomar el resultado y eliminarlo de la base de datos.
     * @param campo
     * @param valor
     * @param clase
     */
    public static void eliminar(String campo, String valor, Class clase){
        Objects resultado = buscar(campo,valor,clase);
        if (resultado==null) {System.out.println("No se ha encontrado ningún " + clase); return;}
        if (resultado.size()>1){
            especificarBorrado(resultado);
        } else {
            //TODO: confirmación
            odb.delete(resultado.getFirst());
        }
    }

    /**
     * Este método es llamado cuando el resultado de una búsqueda es mayor a uno y se necesita especificar qué elemento
     * borrar
     * @param resultado
     */
    private static void especificarBorrado(Objects resultado) {
        //TODO
        List<Object> array=new ArrayList<>();
        while(resultado.hasNext()){
            Object siguiente=resultado.next();

        }
    }

    public static void añadirLibro() throws IOException {
        BufferedReader teclado=new BufferedReader(new InputStreamReader(System.in));
        ODB odb= ODBFactory.open("EDITORIAL.ND");
        System.out.println("Introduce el nombre del libro");
        String nombre=teclado.readLine();
        System.out.println("Introduce el género");
        String genero=teclado.readLine();
        System.out.println("Introduce la sinopsis");
        String sinopsis= teclado.readLine();
        Date fecha= Date.from(Instant.from(LocalDate.now())) ;
        System.out.println("Introduce el autor");
        String busqueda=teclado.readLine();

        ICriterion criterioActualizado= Where.equal("nombre", busqueda);
        IQuery queryActualizado=new CriteriaQuery(Autor.class, criterioActualizado);
        Autor autor=(Autor) odb.getObjects(queryActualizado).getFirst();

        Libro libro=new Libro(nombre, genero, sinopsis, fecha, autor);

        odb.store(libro);
    }

    public static void añadirAutor() throws IOException {
        BufferedReader teclado=new BufferedReader(new InputStreamReader(System.in));
        ODB odb= ODBFactory.open("EDITORIAL.ND");
        System.out.println("Introduce el nombre del autor");
        String nombre=teclado.readLine();
        System.out.println("Introduce los apellidos del autor");
        String apellidos=teclado.readLine();

        Autor autor=new Autor(nombre, apellidos, true);

        System.out.println("El autor se ha creado. ¿Deseas añadir libros? y=sí n=no");
        String eleccion=teclado.readLine();

        if(eleccion.equals("y")){
            System.out.println("Introduce el título");

        }
    }
}
