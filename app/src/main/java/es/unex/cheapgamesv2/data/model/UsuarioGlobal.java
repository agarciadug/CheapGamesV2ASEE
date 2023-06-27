package es.unex.cheapgamesv2.data.model;

public class UsuarioGlobal {
    private Long ID;
    private String nomUsuario;
    private String email;
    private String password;

    private static UsuarioGlobal instance;

    private UsuarioGlobal(Long ID, String nomUsuario, String email, String password) {
        this.ID = ID;
        this.nomUsuario = nomUsuario;
        this.email = email;
        this.password = password;
    }

    public static UsuarioGlobal getInstance() {
        if (instance == null) {
            instance = new UsuarioGlobal(Long.valueOf(0), "Invitado", "invitado@gmail.com", "");
        }
        return instance;
    }

    public static void resetearUsuario() {
        instance.ID = Long.valueOf(0);
        instance.nomUsuario = "Invitado";
        instance.email = "invitado@gmail.com";
        instance.password = "";
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getNomUsuario() {
        return nomUsuario;
    }

    public void setNomUsuario(String nomUsuario) {
        this.nomUsuario = nomUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
