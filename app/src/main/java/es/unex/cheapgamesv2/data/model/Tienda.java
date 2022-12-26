package es.unex.cheapgamesv2.data.model;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "tienda")
public class Tienda {

    @Ignore
    public final static String STORE_ID = "storeID";
    @Ignore
    public final static String STORE_NAME = "storeName";


    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "storeID")
    private String mStoreID = new String();
    @ColumnInfo(name = "storeName")
    private String mStoreName= new String();

    public Tienda(String storeID, String storeName) {
        this.mStoreID = storeID;
        this.mStoreName = storeName;
    }

    @Ignore
    Tienda(Intent intent) {
        mStoreID = intent.getStringExtra(Tienda.STORE_ID);
        mStoreName = intent.getStringExtra(Tienda.STORE_NAME);
    }

    public String getStoreID() { return mStoreID; }

    public void setStoreId(String storeID) { this.mStoreID = storeID; }

    public String getStoreName() {
        return mStoreName;
    }

    public void setStoreName(String storeName) {
        mStoreName = storeName;
    }

    public static void packageIntent(Intent intent, String storeID, String storeName) {

        intent.putExtra(Tienda.STORE_ID, storeID);
        intent.putExtra(Tienda.STORE_NAME, storeName);

    }
}
