package es.unex.cheapgamesv2.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import es.unex.cheapgamesv2.R;

public class PantallaInicial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_inicial);

        Button buttonIniciarSesion = (Button)findViewById(R.id.button_fotoPerfil);
        buttonIniciarSesion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(PantallaInicial.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        Button buttonRegistro = (Button)findViewById(R.id.button_Registro);
        buttonRegistro.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(PantallaInicial.this, RegistroActivity.class);
                startActivity(intent);
            }
        });
    }




}