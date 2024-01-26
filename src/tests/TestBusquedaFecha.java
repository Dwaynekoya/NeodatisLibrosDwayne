package tests;

import controlador.ControlBBDD;
import modelo.Libro;
import org.neodatis.odb.Objects;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class TestBusquedaFecha {
    public static void main(String[] args) {
        LocalDate january262024 = LocalDate.of(2024, 1, 26);

        // Convert LocalDate to Date
        Date today = Date.from(january262024.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Objects libros = ControlBBDD.buscar("fecha_lanzamiento", today, Libro.class);
        System.out.println(libros);
    }
}
