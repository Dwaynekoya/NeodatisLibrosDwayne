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
            System.out.println(resultado);
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
    /*public static Objects busquedaCompleja(Class clase, String[] campos, Object[] valores) {
        Objects resultado;
        //TODO: arreglar
        if (campos == null || campos.length == 0 || valores == null || valores.length == 0 || campos.length != valores.length) {
            throw new IllegalArgumentException("Campos y valores deben ser no nulos y tener la misma longitud.");
        }

        ICriterion[] criterios = new ICriterion[campos.length];
        for (int i = 0; i < campos.length; i++) {
            criterios[i] = Where.equal(campos[i], valores[i]);
        }

        ICriterion criterio = criterios[0];
        for (int i = 1; i < criterios.length; i++) {
            criterio = Where.and().add(criterio).add(criterios[i]);
        }

        IQuery query=new CriteriaQuery(clase, criterio);
        resultado=odb.getObjects(query);
        /*ICriterion criterio = new And()
                .add(Where.equal("nombreUsuario", "admin"))
                .add(Where.equal("contra", valores[1]));

        System.out.println(valores[1]);
        IQuery query = new CriteriaQuery(clase,criterio);
        resultado=odb.getObjects(query);*/
       /* odb.commit();

        if (resultado.isEmpty()) {
            return null;
        } else {
            return resultado;
        }
    }*/
    public static Objects busquedaCompleja(Class clase, Map<String, Object> criterios) {
        Objects resultado;

        if (criterios == null || criterios.isEmpty()) {
            throw new IllegalArgumentException("El mapa de criterios no puede ser nulo o vacío.");
        }

        ICriterion[] criteriosArray = new ICriterion[criterios.size()];
        int index = 0;
        for (Map.Entry<String, Object> entry : criterios.entrySet()) {
            criteriosArray[index++] = Where.equal(entry.getKey(), entry.getValue());
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
     */
    public static void eliminar(Object object){
        /*Objects resultado = buscar(campo,valor,clase);
        if (resultado==null) {System.out.println("No se ha encontrado ningún " + clase); return;}
        if (resultado.size()>1){
            especificarBorrado(resultado);
        } else {
            odb.delete(resultado.getFirst());
        }*/
        odb.delete(object);
        odb.commit();
    }

    /**
     * Este método es llamado cuando el resultado de una búsqueda es mayor a uno y se necesita especificar qué elemento
     * borrar
     *
     * @return
     */
    /*private static void especificarBorrado(Objects resultado) {
        List<Object> array=new ArrayList<>();
        while(resultado.hasNext()){
            Object siguiente=resultado.next();
        }
    }*/

    /**
     * Método que añade un objeto a la base de datos. Comprueba que el objeto no haya sido introducido anteriormente
     * @param object: objeto para guardar, puede pertenecer a la clase Libro, Usuario o Autor
     * @return
     */
    public static boolean addObject(Object object) {
        /*BufferedReader teclado=new BufferedReader(new InputStreamReader(System.in));
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

        Libro libro=new Libro(nombre, genero, sinopsis, fecha, autor);*/
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

    /*public static void addAutor(Autor autor) {
        /*BufferedReader teclado=new BufferedReader(new InputStreamReader(System.in));
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
    }*/

    public static void backup(){
        /*try {
            FileOutputStream backup = new FileOutputStream ("EDITORIAL_BACKUP"+ LocalDate.now() +".ND");
            FileInputStream filein=new FileInputStream("EDITORIAL.ND");
            int i;
            while((i=filein.read())!=-1){
                backup.write(i);
            }
            backup.close();
            filein.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }gives IOException*/
        Path sourcePath = Paths.get("EDITORIAL.ND");
        Path backupFilePath = Paths.get("EDITORIAL_BACKUP"+ LocalDate.now() +".ND");

        try {
            Files.copy(sourcePath, backupFilePath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Backup created successfully.");
        } catch (IOException e) {
            System.err.println("Error creating backup: " + e.getMessage());
        }
    }
    public static ObservableList generarLista(Class aClass){
        Objects resultado = buscar(null, null, aClass);
        return FXCollections.observableArrayList(resultado);
    }

    public static void modificar(Object selectedItem) {
        odb.store(selectedItem);
        odb.commit();
    }

    public static ObservableList listaObservable(Objects items) {
        assert items != null;
        return FXCollections.observableArrayList(items);
    }
}
