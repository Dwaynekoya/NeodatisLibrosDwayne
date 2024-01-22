package modelo;

public class Usuario {
    private String nombreUsuario;
    private char[] contra;

    public Usuario(String nombreUsuario, char[] contra) {
        this.nombreUsuario = nombreUsuario;
        this.contra = contra;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public char[] getContra() {
        return contra;
    }

    public void setContra(char[] contra) {
        this.contra = contra;
    }
}
