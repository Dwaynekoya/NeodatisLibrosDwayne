import controlador.ControlBBDD;
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
import java.time.LocalDateTime;
import java.util.Date;

public class Main {
    private static ODB odb;
    public static void main(String[] args) {
        //Todo: USER LOGIN
        ControlBBDD controlBBDD = new ControlBBDD();
        //INSERTAR DATOS PRUEBA

        //VISUALIZAR
        odb.close();
    }
}