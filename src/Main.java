import modelo.Autor;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.ICriterion;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

public class Main {
    private static ODB odb;
    public static void main(String[] args) {
        odb = ODBFactory.open("EDITORIAL.ND");
        //USER LOGIN

        //CONEXION BBDD

        //INSERTAR DATOS PRUEBA

        //VISUALIZAR
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
    public static void eliminar(String campo, String valor, Class clase){
        Objects resultado = buscar(campo,valor,clase);
        if (resultado==null) System.out.println("No se ha encontrado ningún " + clase); return;

    }
}