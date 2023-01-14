package es.unex.cheapgamesv2.ui.tracing;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.unex.cheapgamesv2.AppExecutors;
import es.unex.cheapgamesv2.R;
import es.unex.cheapgamesv2.data.model.ListaSeguimiento;
import es.unex.cheapgamesv2.data.model.UsuarioGlobal;
import es.unex.cheapgamesv2.data.model.Videogame;
import es.unex.cheapgamesv2.data.network.VideogameExactNetworkLoaderRunnable;
import es.unex.cheapgamesv2.data.room.CheapGamesDB;
import es.unex.cheapgamesv2.data.room.ListaSeguimientoDao;
import es.unex.cheapgamesv2.databinding.FragmentAjustesBinding;
import es.unex.cheapgamesv2.databinding.FragmentTracingBinding;
import es.unex.cheapgamesv2.ui.search.VideogameAdapter;

/**
 * A fragment representing a list of Items.
 */
public class TracingFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    RecyclerView listSeguimiento;
    List<ListaSeguimiento> seguimientos;
    List<Videogame> lDVR;
    private FragmentTracingBinding binding;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TracingFragment() {
    }


    public static TracingFragment newInstance(int columnCount) {
        TracingFragment fragment = new TracingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentTracingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        listSeguimiento = root.findViewById(R.id.RecyclerViewTracing);


        // Set the adapter

            seguimientos = new ArrayList<>();
            lDVR = new ArrayList<>();
            AppExecutors.getInstance().diskIO().execute(() -> {
                Log.v("Seguimiento", "Entrado en juegos en seguimiento");
                CheapGamesDB cheapGamesDB = CheapGamesDB.getInstance(getActivity().getApplicationContext());
                ListaSeguimientoDao listaSeguimientoDao = cheapGamesDB.listaSeguimientoDao();
                seguimientos = listaSeguimientoDao.obtenerSeguimientos(UsuarioGlobal.getID().toString());
                Log.v("Seguimiento", "Juegos en seguimiento obtenidos");
                Log.v("Seguimiento", String.valueOf(seguimientos.size()));


                for(ListaSeguimiento lS : seguimientos){
                    Log.v("Seguimiento", lS.getVideogameID());

                    AppExecutors.getInstance().networkIO()
                            .execute(new VideogameExactNetworkLoaderRunnable(
                                    lS.getTitle(),
                                    (videojuegosSeg)->PutDataIntoRecyclerView(videojuegosSeg.get(0))));
                }
            });


            /* Se carga en otro hilo y no se obtiene el juego*/
            Log.v("Seguimiento", "Acaba almacenado");
        return root;
    }

    private void PutDataIntoRecyclerView(Videogame videojuegoSeg) {
        lDVR.add(videojuegoSeg);
        TracingAdapter adapter = new TracingAdapter(getActivity().getApplicationContext(), lDVR);
        listSeguimiento.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        listSeguimiento.setAdapter(adapter);
    }

}