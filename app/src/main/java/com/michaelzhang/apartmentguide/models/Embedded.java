
package com.michaelzhang.apartmentguide.models;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Embedded implements Parcelable
{

    @SerializedName("apartments")
    @Expose
    private List<Apartment> apartments = null;
    public final static Parcelable.Creator<Embedded> CREATOR = new Creator<Embedded>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Embedded createFromParcel(Parcel in) {
            return new Embedded(in);
        }

        public Embedded[] newArray(int size) {
            return (new Embedded[size]);
        }

    }
    ;

    protected Embedded(Parcel in) {
        in.readList(this.apartments, (com.michaelzhang.apartmentguide.models.Apartment.class.getClassLoader()));
    }

    public Embedded() {
    }

    public List<Apartment> getApartments() {
        return apartments;
    }

    public void setApartments(List<Apartment> apartments) {
        this.apartments = apartments;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(apartments);
    }

    public int describeContents() {
        return  0;
    }

}
