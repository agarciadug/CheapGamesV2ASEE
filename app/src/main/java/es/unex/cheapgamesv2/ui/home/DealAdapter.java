package es.unex.cheapgamesv2.ui.home;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import es.unex.cheapgamesv2.R;
import es.unex.cheapgamesv2.data.model.Deal;
import es.unex.cheapgamesv2.data.model.Videogame;
import es.unex.cheapgamesv2.data.model.VideogameBest;
import es.unex.cheapgamesv2.ui.detail.DetailVideogameActivity;

public class DealAdapter extends RecyclerView.Adapter<DealAdapter.MyViewHolder> {
    private final Context mContex;
    private static List<VideogameBest> mVideogamesBestLista;

    public DealAdapter(Context context, List<VideogameBest> items) {
        mContex = context;
        mVideogamesBestLista = items;
    }


    @Override
    public DealAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.videogame_list, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        Glide.with(mContex).load(mVideogamesBestLista.get(position).getThumb()).into(holder.img);
        holder.mItem = mVideogamesBestLista.get(position);
        holder.mNombreView.setText(mVideogamesBestLista.get(position).getTitle());
        holder.mPrecioView.setText(mVideogamesBestLista.get(position).getSalePrice()+"€");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Videogame selectedVideogame = getSelectedVideogame(position);
                if (selectedVideogame != null) {
                    Intent intent = new Intent(holder.itemView.getContext(), DetailVideogameActivity.class)
                            .putExtra("videojuego", selectedVideogame);
                    holder.itemView.getContext().startActivity(intent);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return mVideogamesBestLista.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public final TextView mNombreView;
        public final TextView mPrecioView;
        public VideogameBest mItem;
        public ImageView img;

        public MyViewHolder(View v) {
            super(v);
            mView = v;
            mNombreView = v.findViewById(R.id.nombreVj);
            mPrecioView = v.findViewById(R.id.precio);
            img = v.findViewById(R.id.imgJuego);
        }

    }


    public static Videogame getSelectedVideogame(int position){
        if(mVideogamesBestLista != null && position < mVideogamesBestLista.size()){
            VideogameBest selectedDeal = mVideogamesBestLista.get(position);
            Videogame selectedVideogame = new Videogame();
            selectedVideogame.setGameID(selectedDeal.getGameID());
            selectedVideogame.setExternal(selectedDeal.getTitle());
            selectedVideogame.setCheapest(selectedDeal.getPrice());
            selectedVideogame.setThumb(selectedDeal.getThumb());
            return selectedVideogame;
        }
        return null;
    }


    public void swap(List<VideogameBest> dataset){
        mVideogamesBestLista = dataset;
        if(mVideogamesBestLista == null || mVideogamesBestLista.size()==0){
           Log.v("ERROR", "No se muestra nada (Adapter");
        }
        notifyDataSetChanged();
    }

    public void clear() {
        mVideogamesBestLista.clear();
        notifyDataSetChanged();
    }
}
