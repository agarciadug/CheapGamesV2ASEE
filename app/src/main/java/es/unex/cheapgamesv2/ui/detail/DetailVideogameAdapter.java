package es.unex.cheapgamesv2.ui.detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import es.unex.cheapgamesv2.R;
import es.unex.cheapgamesv2.data.model.Videogame;
import es.unex.cheapgamesv2.data.model.VideogameDeal;
import es.unex.cheapgamesv2.ui.search.VideogameAdapter;

public class DetailVideogameAdapter extends RecyclerView.Adapter<DetailVideogameAdapter.MyViewHolder>{

    private final Context mContex;
    private static Videogame mVideojuego;

    public DetailVideogameAdapter(Context context, Videogame item) {
        mContex = context;
        mVideojuego = item;
    }

    @NonNull
    @Override
    public DetailVideogameAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_detail_videogame, parent, false);

        return new DetailVideogameAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DetailVideogameAdapter.MyViewHolder holder, int position) {

        Glide.with(mContex).load(mVideojuego.getThumb()).into(holder.mImgView);
        holder.mItem = mVideojuego;
        holder.mNombreVideogameView.setText(mVideojuego.getExternal());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public final TextView mNombreVideogameView;
        public Videogame mItem;
        public ImageView mImgView;

        public MyViewHolder(View v) {
            super(v);
            mView = v;
            mNombreVideogameView = v.findViewById(R.id.nom_videogame);
            mImgView = v.findViewById(R.id.image_Videogame);
        }

    }

    /*métodos para el otro adapter de deals
    public static VideogameDeal getSelectedVideogame(int position){
        if(mVideojuegosDealLista!=null){
            if(mVideojuegosDealLista.size()>0){
                return mVideojuegosDealLista.get(position);
            }
        }
        return null;
    }

    public void swap(List<VideogameDeal> dataset){
        mVideojuegosDealLista = dataset;
        notifyDataSetChanged();
    }

    public void clear(){
        mVideojuegosLista.clear();
        notifyDataSetChanged();
    }*/
}
