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

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ControlBBDD {
    private static final ODB odb = ODBFactory.open("EDITORIAL.ND");

    public static void visualizarTodoConsola() {
        Objects<Libro> libros = odb.getObjects(Libro.class);
        Objects<Autor> autors = odb.getObjects(Autor.class);
        while (libros.hasNext()){
            Libro libro = libros.next();
            System.out.printf("%s: %s %n", libro.getNombre(), libro.getAutor());
        }
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
    }

    /***
     * Función que realiza consulta de búsqueda en BBDD Neodatis. Devuelve nulo si no hay resultados, u Objects con
     * los resultados
     * @param campo: columna de la tabla
     * @param valor: valor que buscar
     * @param clase: clase del objeto, "tabla"
     * @return
     */
    public static Objects buscar(String campo, String valor, Class clase){
        Objects autores;
        if (campo==null){
            autores = odb.getObjects(clase);
            System.out.println(autores);
        }else {
            ICriterion criterion = Where.equal(campo,valor);
            IQuery query = new CriteriaQuery(clase, criterion);
             autores = odb.getObjects(query);
        }
        if (autores.isEmpty()){
            return null;
        }
        else {
            return autores;
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
            //TODO: confirmación
            odb.delete(resultado.getFirst());
        }*/
        odb.delete(object);
        odb.commit();
    }

    /**
     * Este método es llamado cuando el resultado de una búsqueda es mayor a uno y se necesita especificar qué elemento
     * borrar
     */
    /*private static void especificarBorrado(Objects resultado) {
        //TODO (o quizá basta con controlar que no haya repetidos)
        List<Object> array=new ArrayList<>();
        while(resultado.hasNext()){
            Object siguiente=resultado.next();
        }
    }*/

    public static void addLibro(Libro libro) {
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
        odb.store(libro);
        odb.commit();
    }

    public static void addAutor(Autor autor) {
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

        }*/
        odb.store(autor);
        odb.commit();
    }

    public static void backup(){
        try {
            FileOutputStream backup = new FileOutputStream ("EDITORIAL_BACKUP.ND");
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
        }
    }
}
