package modelo;

import java.util.Date;

public class Libro {

    private String nombre;
    private String genero;
    private String sinopsis;
    private Date fecha_lanzamiento;
    private Autor autor;
    public Libro(String nombre, String genero, Date fecha_lanzamiento, Autor autor) {
        this.nombre = nombre;
        this.genero = genero;
        this.sinopsis="";
        this.fecha_lanzamiento = fecha_lanzamiento;
        this.autor = autor;
    }
    public Libro(String nombre, String genero, String sinopsis, Date fecha_lanzamiento, Autor autor) {
        this.nombre = nombre;
        this.genero = genero;
        this.sinopsis = sinopsis;
        this.fecha_lanzamiento = fecha_lanzamiento;
        this.autor = autor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public Date getFecha_lanzamiento() {
        return fecha_lanzamiento;
    }

    public void setFecha_lanzamiento(Date fecha_lanzamiento) {
        this.fecha_lanzamiento = fecha_lanzamiento;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
