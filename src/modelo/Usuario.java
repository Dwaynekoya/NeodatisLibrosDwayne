package modelo;

public class Usuario {
    private String nombreUsuario;
    private String contra;

    public Usuario(String nombreUsuario, String contra) {
        this.nombreUsuario = nombreUsuario;
        this.contra = contra;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }
}
