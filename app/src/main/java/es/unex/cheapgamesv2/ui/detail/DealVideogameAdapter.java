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

import es.unex.cheapgamesv2.AppExecutors;
import es.unex.cheapgamesv2.R;
import es.unex.cheapgamesv2.data.model.Tienda;
import es.unex.cheapgamesv2.data.model.VideogameDeal;
import es.unex.cheapgamesv2.data.room.CheapGamesDB;
import es.unex.cheapgamesv2.data.room.TiendaDao;

public class DealVideogameAdapter extends RecyclerView.Adapter<DealVideogameAdapter.MyViewHolder>{

    private final Context mContex;
    private static List<VideogameDeal> mVideogameDeals;

    public DealVideogameAdapter(Context context, List<VideogameDeal> items) {
        mContex = context;
        mVideogameDeals = items;
    }


    @NonNull
    @Override
    public DealVideogameAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.deal_list, parent, false);

        return new DealVideogameAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        int idLogo = Integer.parseInt(mVideogameDeals.get(position).getStoreID())-1;
        Glide.with(mContex).load("https://www.cheapshark.com/img/stores/icons/"+idLogo+".png").into(holder.mLogoTienda);
        holder.mItem = mVideogameDeals.get(position);
        holder.mPrecioBView.setText(mVideogameDeals.get(position).getPrice());
        holder.mPrecioOView.setText(mVideogameDeals.get(position).getRetailPrice());
        CheapGamesDB cheapGamesDB = CheapGamesDB.getInstance(mContex);
        TiendaDao tiendaDao = cheapGamesDB.tiendaDao();
        AppExecutors.getInstance().diskIO().execute(() -> {
            Tienda t = tiendaDao.getTiendaByID(mVideogameDeals.get(position).getStoreID());
            holder.mNomTienda.setText(t.getStoreName());
        });


    }

    @Override
    public int getItemCount() {
        return mVideogameDeals.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public final TextView mNomTienda;
        public final TextView mPrecioBView;
        public final TextView mPrecioOView;
        public VideogameDeal mItem;
        public ImageView mLogoTienda;

        public MyViewHolder(View v) {
            super(v);
            mView = v;
            mNomTienda = v.findViewById(R.id.nom_tienda);
            mPrecioBView = v.findViewById(R.id.precio_oferta);
            mPrecioOView = v.findViewById(R.id.precio_original);
            mLogoTienda = v.findViewById(R.id.logo_tienda);
        }

    }

    public static VideogameDeal getSelectedVideogameDeal(int position){
        if(mVideogameDeals!=null){
            if(mVideogameDeals.size()>0){
                return mVideogameDeals.get(position);
            }
        }
        return null;
    }

    public void swap(List<VideogameDeal> dataset){
        mVideogameDeals = dataset;
        notifyDataSetChanged();
    }

    public void clear(){
        mVideogameDeals.clear();
        notifyDataSetChanged();
    }
}
