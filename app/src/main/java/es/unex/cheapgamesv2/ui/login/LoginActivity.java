package es.unex.cheapgamesv2.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import es.unex.cheapgamesv2.MainActivityButtons;
import es.unex.cheapgamesv2.R;
import es.unex.cheapgamesv2.data.model.Usuario;
import es.unex.cheapgamesv2.data.model.UsuarioGlobal;
import es.unex.cheapgamesv2.data.room.CheapGamesDB;
import es.unex.cheapgamesv2.data.room.UsuarioDao;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button login;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();
                if (emailText.isEmpty() || passwordText.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Rellena todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    final UsuarioDao usuarioDao = CheapGamesDB.getInstance(getApplicationContext()).usuarioDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Usuario usuario = usuarioDao.login(emailText, passwordText);
                            if (usuario == null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "¡¡Credenciales inválidas!!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                UsuarioGlobal usuarioGlobal = UsuarioGlobal.getInstance();
                                usuarioGlobal.setID(usuario.getID());
                                usuarioGlobal.setNomUsuario(usuario.getNomUsuario());
                                usuarioGlobal.setEmail(usuario.getEmail());
                                usuarioGlobal.setPassword(usuario.getPassword());

                                Intent intent = new Intent(LoginActivity.this, MainActivityButtons.class);
                                startActivity(intent);
                            }
                        }
                    }).start();
                }
            }
        });
    }
}
