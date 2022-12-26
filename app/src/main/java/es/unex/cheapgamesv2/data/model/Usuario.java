package es.unex.cheapgamesv2.data.model;

import android.content.Intent;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName = "usuario")
public class Usuario {
	@Ignore
	public static final String ITEM_SEP = System.getProperty("line.separator");

	@Ignore
	public final static String ID = "ID";
	@Ignore
	public final static String NOMUSUARIO = "nomUsuario";
	@Ignore
	public final static String EMAIL = "email";
	@Ignore
	public final static String PASSWORD = "password";


	@PrimaryKey(autoGenerate = true)
	private long mID;
	@ColumnInfo(name = "nomUsuario")
	private String mNomUsuario = new String();
	@ColumnInfo(name = "email")
	private String mEmail= new String();
	@ColumnInfo(name = "password")
	private String   mPassword = new String();


	public Usuario(String nomUsuario, String email, String password) {
		this.mNomUsuario = nomUsuario;
		this.mEmail = email;
		this.mPassword = password;
	}

	@Ignore
    public Usuario(long ID, String nomUsuario, String email, String password) {
        this.mID = ID;
        this.mNomUsuario = nomUsuario;
        this.mEmail = email;
        this.mPassword = password;

    }

	// Create a new Usuario2 from data packaged in an Intent
	@Ignore
	Usuario(Intent intent) {
		mID = intent.getLongExtra(Usuario.ID,0); //TODO think best default value for ID
		mNomUsuario = intent.getStringExtra(Usuario.NOMUSUARIO);
		mEmail = intent.getStringExtra(Usuario.EMAIL);
		mPassword = intent.getStringExtra(Usuario.PASSWORD);

	}

    public long getID() { return mID; }

    public void setID(long ID) { this.mID = ID; }

    public String getNomUsuario() {
		return mNomUsuario;
	}

	public void setNomUsuario(String nomUsuario) {
		mNomUsuario = nomUsuario;
	}

	public String getEmail() {
		return mEmail;
	}

	public void setEmail(String email) {
		mEmail = email;
	}

	public String getPassword() {
		return mPassword;
	}

	public void setPassword(String password) {
		mPassword = password;
	}


	// Take a set of String data values and
	// package them for transport in an Intent

	public static void packageIntent(Intent intent, String nomUsuario,
									 String email, String password) {

		intent.putExtra(Usuario.NOMUSUARIO, nomUsuario);
		intent.putExtra(Usuario.EMAIL, email);
		intent.putExtra(Usuario.PASSWORD, password);
	
	}

	public String toString() {
		return mID + ITEM_SEP + mNomUsuario + ITEM_SEP + mEmail + ITEM_SEP + mPassword;
	}

	public String toLog() {
		return "ID: " + mID + ITEM_SEP + "NomUsuario:" + mNomUsuario + ITEM_SEP + "Email:" + mEmail
				+ ITEM_SEP + "Password:" + mPassword;
	}

}
