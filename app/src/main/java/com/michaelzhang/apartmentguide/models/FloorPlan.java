
package com.michaelzhang.apartmentguide.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FloorPlan implements Parcelable, Comparable<FloorPlan> {

    @SerializedName("bed")
    @Expose
    private Double bed;
    @SerializedName("bath")
    @Expose
    private Double bath;
    @SerializedName("priceFrom")
    @Expose
    private Integer priceFrom;
    public final static Parcelable.Creator<FloorPlan> CREATOR = new Creator<FloorPlan>() {


        @SuppressWarnings({
                "unchecked"
        })
        public FloorPlan createFromParcel(Parcel in) {
            return new FloorPlan(in);
        }

        public FloorPlan[] newArray(int size) {
            return (new FloorPlan[size]);
        }

    };

    protected FloorPlan(Parcel in) {
        this.bed = ((Double) in.readValue((Double.class.getClassLoader())));
        this.bath = ((Double) in.readValue((Double.class.getClassLoader())));
        this.priceFrom = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public FloorPlan() {
    }

    public Double getBed() {
        return bed;
    }

    public void setBed(Double bed) {
        this.bed = bed;
    }

    public Double getBath() {
        return bath;
    }

    public void setBath(Double bath) {
        this.bath = bath;
    }

    public Integer getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(Integer priceFrom) {
        this.priceFrom = priceFrom;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(bed);
        dest.writeValue(bath);
        dest.writeValue(priceFrom);
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public int compareTo(FloorPlan o) {
        if (this.bed > o.getBed()) return 1;
        else if (this.bed < o.getBed()) return -1;
        else return 0;
    }
}
