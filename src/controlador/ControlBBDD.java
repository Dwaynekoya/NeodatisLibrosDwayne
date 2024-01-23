package controlador;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.ICriterion;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

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
    }
}
