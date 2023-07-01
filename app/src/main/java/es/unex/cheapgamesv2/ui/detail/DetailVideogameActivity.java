package es.unex.cheapgamesv2.ui.detail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import es.unex.cheapgamesv2.AppContainer;
import es.unex.cheapgamesv2.AppExecutors;
import es.unex.cheapgamesv2.InjectorUtils;
import es.unex.cheapgamesv2.MainActivityButtons;
import es.unex.cheapgamesv2.MyApplication;
import es.unex.cheapgamesv2.R;
import es.unex.cheapgamesv2.data.model.ListaSeguimiento;
import es.unex.cheapgamesv2.data.model.Tienda;
import es.unex.cheapgamesv2.data.model.UsuarioGlobal;
import es.unex.cheapgamesv2.data.model.Videogame;
import es.unex.cheapgamesv2.data.room.CheapGamesDB;
import es.unex.cheapgamesv2.data.room.ListaSeguimientoDao;
import es.unex.cheapgamesv2.ui.tracing.TracingAdapter;

public class
DetailVideogameActivity extends AppCompatActivity {

    private DealVideogameAdapter mAdapter;
    private TracingAdapter tracingAdapter;
    private RecyclerView mRecyclerView;

    private ImageView img_videogame;
    private TextView title_videogame;
    List<ListaSeguimiento> listaSeguido;
    List<Tienda> listaTienda;
    Button b_Seguimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_videogame);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        img_videogame=findViewById(R.id.image_Videogame);
        title_videogame = findViewById(R.id.nom_videogame);
        Videogame vg = GetDataFromIntent();
        mRecyclerView = findViewById(R.id.list_precios);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        CheapGamesDB cheapGamesDB = CheapGamesDB.getInstance(this);
        listaTienda=new ArrayList<>();
        mAdapter = new DealVideogameAdapter(this,new ArrayList<>());
        tracingAdapter = new TracingAdapter(this, new ArrayList<>());
        mRecyclerView.setAdapter(mAdapter);

        DetailVideogameViewModelFactory dVfactory = InjectorUtils.provideDetailVideogameViewModelFactory(this.getApplicationContext());
        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
        DetailVideogameActivityViewModel mViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory)appContainer.dVfactory).get(DetailVideogameActivityViewModel.class);
        mViewModel.getVideogameDeals().observe(this, videogameDeals -> {
            mAdapter.swap(videogameDeals);
            if (videogameDeals != null && videogameDeals.size() != 0) showDealsDataView();
        });
        mViewModel.setVideogameID(vg.getGameID());
        Log.v("Id videojuego", vg.getGameID());

        //hacer funcionalidad boton seguimiento
        b_Seguimiento=findViewById(R.id.b_seguimiento);
        if(UsuarioGlobal.getInstance().getID()>0){
            Videogame videojuego = getIntent().getParcelableExtra("videojuego",Videogame.class);
            ListaSeguimientoDao listaSeguimientoDao = cheapGamesDB.listaSeguimientoDao();
            listaSeguido = new ArrayList<>();
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    Log.v("Datos", videojuego.getGameID() + ", " + UsuarioGlobal.getInstance().getID());
                    listaSeguido = listaSeguimientoDao.obtenerSeguido(videojuego.getGameID(),String.valueOf(UsuarioGlobal.getInstance().getID()));
                    Log.v("Tamaño lista", String.valueOf(listaSeguido.size()));
                    if(listaSeguido.size()!=1){
                        b_Seguimiento.setText("AÑADIR A SEGUIMIENTO");
                        b_Seguimiento.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AppExecutors.getInstance().diskIO().execute(() -> {
                                    Log.v("id del usuario", String.valueOf(UsuarioGlobal.getInstance().getID()));
                                    ListaSeguimiento seguimiento = new ListaSeguimiento(videojuego.getGameID(),videojuego.getExternal(), String.valueOf(UsuarioGlobal.getInstance().getID()));
                                    listaSeguimientoDao.insertarSeguimiento(seguimiento);
                                    Intent intent = new Intent(DetailVideogameActivity.this, MainActivityButtons.class);
                                    startActivity(intent);
                                });
                            }
                        });
                    }
                    else {
                        b_Seguimiento.setText("DEJAR DE SEGUIR");
                        b_Seguimiento.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AppExecutors.getInstance().diskIO().execute(() -> {
                                    Log.v("id del usuario", String.valueOf(UsuarioGlobal.getInstance().getID()));
                                    listaSeguimientoDao.borrarSeguimiento(videojuego.getGameID(), String.valueOf(UsuarioGlobal.getInstance().getID()));
                                    Intent intent = new Intent(DetailVideogameActivity.this, MainActivityButtons.class);
                                    startActivity(intent);
                                });
                            }
                        });
                    }
                }
            });
        }
        else{
            b_Seguimiento.setVisibility(View.INVISIBLE);
        }
    }

    private Videogame GetDataFromIntent() {
        if(getIntent().hasExtra("videojuego")){
            Videogame videojuego = getIntent().getParcelableExtra("videojuego",Videogame.class);
                title_videogame.setText(videojuego.getExternal());
                Glide.with(DetailVideogameActivity.this).load(videojuego.getThumb()).into(img_videogame);
            return videojuego;
        }
        else{
            return null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void showDealsDataView(){
        mRecyclerView.setVisibility(View.VISIBLE);
    }
}