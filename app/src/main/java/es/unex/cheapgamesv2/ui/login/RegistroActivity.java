package es.unex.cheapgamesv2.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import es.unex.cheapgamesv2.R;
import es.unex.cheapgamesv2.data.model.Usuario;
import es.unex.cheapgamesv2.data.room.CheapGamesDB;
import es.unex.cheapgamesv2.data.room.UsuarioDao;

public class RegistroActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EditText nomUsuario, email, password;

        setContentView(R.layout.activity_registro);
        nomUsuario = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        Button registro = (Button) findViewById(R.id.b_registro);


        registro.setOnClickListener(v -> {
            Usuario usuario = new Usuario(nomUsuario.getText().toString(),
                    email.getText().toString(), password.getText().toString());

            if (validateInput (usuario)) {
                CheapGamesDB cheapGamesDB = CheapGamesDB.getInstance (getApplicationContext());
                UsuarioDao usuarioDao = cheapGamesDB.usuarioDao();
                new Thread(() -> {
                    usuarioDao.registerUser(usuario);
                    runOnUiThread(() -> {
                        Toast.makeText(getApplicationContext(), "User Registered!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegistroActivity.this, LoginActivity.class));
                    });
                }).start();
            } else {
                Toast.makeText(getApplicationContext(),"Fill all fields!", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private Boolean validateInput (Usuario usuario) {
        if (usuario.getNomUsuario().isEmpty() ||
                usuario.getEmail().isEmpty() ||
                usuario.getPassword().isEmpty()) {
            return false;
        }
        return true;
    }
}
