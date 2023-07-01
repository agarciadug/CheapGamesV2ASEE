package es.unex.cheapgamesv2.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.util.Log;
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
import es.unex.cheapgamesv2.data.model.Tienda;
import es.unex.cheapgamesv2.data.model.VideogameDeal;

public class DealVideogameAdapter extends RecyclerView.Adapter<DealVideogameAdapter.MyViewHolder>{

    private final Context mContex;
    private static List<VideogameDeal> mVideogameDeals;
    private static List<Tienda> mListTienda;

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
        holder.mPrecioBView.setText(mVideogameDeals.get(position).getPrice()+"€");
        Log.v("Precio oferta",mVideogameDeals.get(position).getPrice()+"€");
        holder.mPrecioOView.setText(mVideogameDeals.get(position).getRetailPrice()+"€");
        String[] parts = mVideogameDeals.get(position).getSavings().split("[.]");
        Log.v("parts", parts[0]);
        Log.v("parts", parts[1]);
        if(parts[0].equals("0")){
            holder.mSavingsView.setText("");
        }
        else{
            holder.mSavingsView.setText(parts[0]+"%");
            holder.mPrecioOView.setPaintFlags(holder.mPrecioOView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        boolean encontrada = false;
        int i=0;
        Log.v("Busq tienda", String.valueOf(Tienda.listaTiendas.size()));
        while(!encontrada && i<Tienda.listaTiendas.size()){
            Log.v("Busq tienda: LisTienda", Tienda.listaTiendas.get(i).getStoreID());
            Log.v("Busq tienda: ListVDeals", mVideogameDeals.get(position).getStoreID());
            if(Tienda.listaTiendas.get(i).getStoreID().equals(mVideogameDeals.get(position).getStoreID())){
                holder.mNomTienda.setText(Tienda.listaTiendas.get(i).getStoreName());
                Log.v("Busq tienda", "tienda encontrada");
                encontrada=true;
            }
            else{
                i++;
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.cheapshark.com/redirect?dealID="+holder.mItem.getDealID()));
                holder.itemView.getContext().startActivity(browserIntent);
            }
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
        public final TextView mSavingsView;
        public VideogameDeal mItem;
        public ImageView mLogoTienda;

        public MyViewHolder(View v) {
            super(v);
            mView = v;
            mNomTienda = v.findViewById(R.id.nom_tienda);
            mPrecioBView = v.findViewById(R.id.precio_oferta);
            mPrecioOView = v.findViewById(R.id.precio_original);
            mLogoTienda = v.findViewById(R.id.logo_tienda);
            mSavingsView = v.findViewById(R.id.savings);
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
