package es.unex.cheapgamesv2.ui.detail;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import es.unex.cheapgamesv2.AppContainer;
import es.unex.cheapgamesv2.InjectorUtils;
import es.unex.cheapgamesv2.MyApplication;
import es.unex.cheapgamesv2.R;
import es.unex.cheapgamesv2.data.model.Videogame;
import es.unex.cheapgamesv2.ui.search.SearchVideogameActivityViewModel;
import es.unex.cheapgamesv2.ui.search.SearchVideogameViewModelFactory;
import es.unex.cheapgamesv2.ui.search.VideogameAdapter;

public class DetailVideogameActivity extends AppCompatActivity {

    private DealVideogameAdapter mAdapter;
    private RecyclerView mRecyclerView;

    private ImageView img_videogame;
    private TextView title_videogame;
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
        mAdapter = new DealVideogameAdapter(this,new ArrayList<>());
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

    private void showDealsDataView(){
        mRecyclerView.setVisibility(View.VISIBLE);
    }
}