package es.unex.cheapgamesv2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import es.unex.cheapgamesv2.data.LoadStores;
import es.unex.cheapgamesv2.data.model.Usuario;
import es.unex.cheapgamesv2.data.model.UsuarioGlobal;
import es.unex.cheapgamesv2.databinding.ActivityMainBinding;
import es.unex.cheapgamesv2.ui.ajustes.AjustesFragment;
import es.unex.cheapgamesv2.ui.login.PantallaInicial;
import es.unex.cheapgamesv2.ui.search.SearchVideogameActivity;

public class MenuInicialActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private Usuario uAux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        LoadStores loadStores = new LoadStores(this);
        loadStores.Stores();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_seguimiento, R.id.ajustesFragment)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        View headerView = navigationView.getHeaderView(0);
        Button b_sesion = headerView.findViewById(R.id.b_sesion);

        if(!UsuarioGlobal.getInstance().getNomUsuario().equals("Invitado")){
            TextView navUsername = (TextView) headerView.findViewById(R.id.nav_NomUsuario);
            navUsername.setText(UsuarioGlobal.getInstance().getNomUsuario());
            TextView email = headerView.findViewById(R.id.textView);
            email.setText(UsuarioGlobal.getInstance().getEmail());
            Log.v("Nom usuario", "hola");
        }
        else{
            Menu menu=navigationView.getMenu();
            MenuItem m1 = menu.findItem(R.id.nav_seguimiento);
            m1.setVisible(false);
            MenuItem m2 = menu.findItem(R.id.ajustesFragment);
            m2.setVisible(false);
            Log.v("Nom usuario", "adios");
            TextView navUsername = (TextView) headerView.findViewById(R.id.nav_NomUsuario);
            navUsername.setText("Inicia sesión para tener todas las funcionalidades");
            TextView email = headerView.findViewById(R.id.textView);
            email.setVisibility(View.INVISIBLE);
            b_sesion.setText("Iniciar sesión");
        }

        b_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsuarioGlobal.resetearUsuario();
                Intent intent = new Intent(MenuInicialActivity.this, PantallaInicial.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_search:
                Intent intent = new Intent(MenuInicialActivity.this, SearchVideogameActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}