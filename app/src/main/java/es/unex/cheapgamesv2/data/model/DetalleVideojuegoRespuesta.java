
package es.unex.cheapgamesv2.data.model;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetalleVideojuegoRespuesta implements Parcelable
{

    @SerializedName("info")
    @Expose
    private Info info;
    @SerializedName("cheapestPriceEver")
    @Expose
    private CheapestPriceEver cheapestPriceEver;
    @SerializedName("deals")
    @Expose
    private List<Deal> deals = new ArrayList<>();
    public final static Creator<DetalleVideojuegoRespuesta> CREATOR = new Creator<DetalleVideojuegoRespuesta>() {

        @SuppressWarnings({
            "unchecked"
        })
        public DetalleVideojuegoRespuesta createFromParcel(android.os.Parcel in) {
            return new DetalleVideojuegoRespuesta(in);
        }

        public DetalleVideojuegoRespuesta[] newArray(int size) {
            return (new DetalleVideojuegoRespuesta[size]);
        }

    }
    ;

    protected DetalleVideojuegoRespuesta(android.os.Parcel in) {
        this.info = ((Info) in.readValue((Info.class.getClassLoader())));
        this.cheapestPriceEver = ((CheapestPriceEver) in.readValue((CheapestPriceEver.class.getClassLoader())));
        in.readList(this.deals, (Deal.class.getClassLoader()));
    }

    public DetalleVideojuegoRespuesta() {
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public CheapestPriceEver getCheapestPriceEver() {
        return cheapestPriceEver;
    }

    public void setCheapestPriceEver(CheapestPriceEver cheapestPriceEver) {
        this.cheapestPriceEver = cheapestPriceEver;
    }

    public List<Deal> getDeals() {
        return deals;
    }

    public void setDeals(List<Deal> deals) {
        this.deals = deals;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(info);
        dest.writeValue(cheapestPriceEver);
        dest.writeList(deals);
    }

    public int describeContents() {
        return  0;
    }

}
