package modelo;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Autor {
    private String nombre;
    private String apellidos;
    private boolean activo;
    private Set<Libro> libros;

    public Autor(String nombre, String apellidos, boolean activo) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.activo = activo;
        this.libros = new HashSet<>();
    }
    public Autor(String nombre, String apellidos, boolean activo, Set<Libro> libros) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.activo = activo;
        this.libros = libros;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Set<Libro> getLibros() {
        return libros;
    }

    public void setLibros(Set<Libro> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        return apellidos + ", " + nombre;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Autor otroAutor = (Autor) o;
        return Objects.equals(nombre, otroAutor.nombre) &&
                Objects.equals(apellidos, otroAutor.apellidos);
    }
}
