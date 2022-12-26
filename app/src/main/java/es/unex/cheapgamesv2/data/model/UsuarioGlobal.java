package es.unex.cheapgamesv2.data.model;

public class UsuarioGlobal {


    public static Long ID = Long.valueOf(0);
    public static String nomUsuario = "Invitado";
    public static String email="invitado@gmail.com";
    public static String password="";

    public static Long getID() {
        return ID;
    }

    public static void setID(Long ID) {
        UsuarioGlobal.ID = ID;
    }

    public static String getNomUsuario() {
        return nomUsuario;
    }

    public static void setNomUsuario(String nomUsuario) {
        UsuarioGlobal.nomUsuario = nomUsuario;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        UsuarioGlobal.email = email;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        UsuarioGlobal.password = password;
    }




}
