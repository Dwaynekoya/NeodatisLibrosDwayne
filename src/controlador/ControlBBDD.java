package controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.Autor;
import modelo.Libro;
import modelo.Usuario;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.ICriterion;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Map;

public class ControlBBDD {
    private static final ODB odb = ODBFactory.open("EDITORIAL.ND");

    /**
     * Cierra la base de datos.
     */

    public static void cerrarBBDD(){
        odb.close();
        backup();
    }

    /***
     * Función que realiza consulta de búsqueda en BBDD Neodatis. Devuelve nulo si no hay resultados, u Objects con
     * los resultados
     * @param campo: columna de la tabla
     * @param valor: valor que buscar
     * @param clase: clase del objeto, "tabla"
     * @return
     */
    public static Objects buscar(String campo, Object valor, Class clase){
        Objects resultado;
        if (campo==null){
            resultado = odb.getObjects(clase);
            //System.out.println(resultado);
        }else {
            ICriterion criterion = Where.equal(campo,valor);
            IQuery query = new CriteriaQuery(clase, criterion);
            resultado = odb.getObjects(query);
        }
        if (resultado.isEmpty()){
            return null;
        }
        else {
            return resultado;
        }
    }

    /**
     * Busqueda en la base de datos usando una consulta And
     * @param clase: Clase a la que pertenecen los objetos de la búsqueda
     * @param criterios: Mapa donde cada clave es un campo o columna de la tabla y el valor es el valor para comparar
     * @return
     */
    public static Objects busquedaCompleja(Class clase, Map<String, Object> criterios) {
        Objects resultado;

        if (criterios == null || criterios.isEmpty()) {
            throw new IllegalArgumentException("El mapa de criterios no puede ser nulo o vacío.");
        }

        ICriterion[] criteriosArray = new ICriterion[criterios.size()];
        int index = 0;
        for (Map.Entry<String, Object> entry : criterios.entrySet()) {
            if (entry.getKey().equals("nombre")||entry.getKey().equals("apellidos")){
                criteriosArray[index++] = Where.like(entry.getKey(), (String) entry.getValue());
            }else {
                criteriosArray[index++] = Where.equal(entry.getKey(), entry.getValue());
            }
        }

        ICriterion criterio = criteriosArray[0];
        for (int i = 1; i < criteriosArray.length; i++) {
            criterio = Where.and().add(criterio).add(criteriosArray[i]);
        }

        IQuery query = new CriteriaQuery(clase, criterio);
        resultado = odb.getObjects(query);
        odb.commit();

        if (resultado.isEmpty()) {
            return null;
        } else {
            return resultado;
        }
    }

    /**
     * Este método elimina el objeto recibido de la BD
     * @param object: objeto a eliminar
     */
    public static void eliminar(Object object){
        odb.delete(object);
        odb.commit();
    }

    /**
     * Método que añade un objeto a la base de datos. Comprueba que el objeto no haya sido introducido anteriormente
     * @param object: objeto para guardar, puede pertenecer a la clase Libro, Usuario o Autor
     * @return
     */
    public static boolean addObject(Object object) {
        Objects duplicado = null;
        if (object instanceof Libro){
            duplicado = buscar("nombre", ((Libro) object).getNombre(), Libro.class);
        }
        if (object instanceof Autor){
            duplicado = buscar("nombre", ((Autor) object).getNombre(), Autor.class);
        }
        if (object instanceof Usuario){
            duplicado=buscar("nombreUsuario",((Usuario) object).getNombreUsuario(), Usuario.class);
        }
        if (!(duplicado ==null) && !(duplicado.isEmpty())) return false;
        odb.store(object);
        odb.commit();
        return true;
    }

    /**
     * Genera un archivo de respaldo de la base de datos.
     */
    public static void backup(){
        Path sourcePath = Paths.get("EDITORIAL.ND");
        Path backupFilePath = Paths.get("EDITORIAL_BACKUP"+LocalDate.now()+".bak");

        try {
            Files.copy(sourcePath, backupFilePath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Copia de seguridad realizada.");
        } catch (IOException e) {
            System.err.println("Error creando copia de seguridad: " + e.getMessage());
        }
    }

    /**
     * @param aClass: clase a la que pertenecen los objetos
     * @return lista de objetos de una clase. Usado para mostrar listas en aplicación principal
     */
    public static ObservableList generarLista(Class aClass){
        Objects resultado = buscar(null, null, aClass);
        return FXCollections.observableArrayList(resultado);
    }

    /**
     * edita el objeto presente en la base de datos.
     * @param selectedItem
     */
    public static void modificar(Object selectedItem) {
        odb.store(selectedItem);
        odb.commit();
    }

    public static ObservableList listaObservableAutores() {
        Objects<Autor> items = ControlBBDD.buscar(null, null, Autor.class);
        assert items != null;
        return FXCollections.observableArrayList(items);
    }
    /**
     * Método para visualizar datos por consola (Pruebas)
     */
    public static void visualizarTodoConsola() {
        Objects<Libro> libros = odb.getObjects(Libro.class);
        Objects<Autor> autors = odb.getObjects(Autor.class);
        System.out.println("---LIBROS---");
        while (libros.hasNext()){
            Libro libro = libros.next();
            System.out.printf("%s: %s %n", libro.getNombre(), libro.getAutor());
        }
        System.out.println("---AUTORES---");
        while (autors.hasNext()){
            Autor autor = autors.next();
            System.out.printf("%s, %s %n", autor.getApellidos(), autor.getNombre());
        }
    }

    public static void insertarDatosPrueba() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Autor autor1 = new Autor("George", "Orwell", false);
        Autor autor2 = new Autor("Jane", "Austen", false);
        Autor autor3 = new Autor("Rick", "Riordan", true);
        Libro libro1 = new Libro("Orgullo y Prejuicio", "Distopico", format.parse("01/01/2000") , autor2);
        Libro libro2 =new Libro("Emma", "Distopico", format.parse("01/01/2020") , autor2);
        Libro[] libros = new  Libro[]{
                new Libro("1984", "Distopico", format.parse("01/01/2000") , autor1),
                libro1,libro2
        };
        autor2.getLibros().add(libro1); autor2.getLibros().add(libro2);
        odb.store(autor1);odb.store(autor2);odb.store(autor3);
        for (Libro libro:libros){
            odb.store(libro);
        }
        odb.commit();
    }
}
