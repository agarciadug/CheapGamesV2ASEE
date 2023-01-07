package es.unex.cheapgamesv2.ui.search;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import es.unex.cheapgamesv2.R;
import es.unex.cheapgamesv2.data.model.Usuario;
import es.unex.cheapgamesv2.data.model.Videogame;
import es.unex.cheapgamesv2.ui.detail.DetailVideogameActivity;

public class VideogameAdapter extends RecyclerView.Adapter<VideogameAdapter.MyViewHolder> {
    private final Context mContex;
    private static List<Videogame> mVideojuegosLista;

    public VideogameAdapter(Context context, List<Videogame> items) {
        mContex = context;
        mVideojuegosLista = items;
    }

    @Override
    public VideogameAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.videogame_list, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        Glide.with(mContex).load(mVideojuegosLista.get(position).getThumb()).into(holder.img);
        holder.mItem = mVideojuegosLista.get(position);
        holder.mNombreView.setText(mVideojuegosLista.get(position).getExternal());
        holder.mPrecioView.setText(mVideojuegosLista.get(position).getCheapest()+"€");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailVideogameActivity.class)
                        .putExtra("videojuego", holder.mItem);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mVideojuegosLista.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public final TextView mNombreView;
        public final TextView mPrecioView;
        public Videogame mItem;
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
        if(mVideojuegosLista!=null){
            if(mVideojuegosLista.size()>0){
                return mVideojuegosLista.get(position);
            }
        }
        return null;
    }

    public void swap(List<Videogame> dataset){
        mVideojuegosLista = dataset;
        notifyDataSetChanged();
    }

    public void clear() {
        mVideojuegosLista.clear();
        notifyDataSetChanged();
    }
}
