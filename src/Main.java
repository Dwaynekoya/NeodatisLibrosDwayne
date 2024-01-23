import controlador.ControlBBDD;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.ICriterion;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

public class Main {
    public static void main(String[] args) {
        //Todo: USER LOGIN
        ControlBBDD controlBBDD = new ControlBBDD();
        //INSERTAR DATOS PRUEBA

        //VISUALIZAR
        controlBBDD.cerrarBBDD();
    }

}