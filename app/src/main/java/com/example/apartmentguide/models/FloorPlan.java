package com.example.apartmentguide.models;

import android.os.Parcel;
import android.os.Parcelable;

public class FloorPlan implements Parcelable {

    private String bed;
    private String bath;
    private String price_from;

    public FloorPlan() {
    }

    public FloorPlan(String bed, String bath, String price_from) {
        this.bed = bed;
        this.bath = bath;
        this.price_from = price_from;
    }

    protected FloorPlan(Parcel in) {
        bed = in.readString();
        bath = in.readString();
        price_from = in.readString();
    }

    public static final Creator<FloorPlan> CREATOR = new Creator<FloorPlan>() {
        @Override
        public FloorPlan createFromParcel(Parcel in) {
            return new FloorPlan(in);
        }

        @Override
        public FloorPlan[] newArray(int size) {
            return new FloorPlan[size];
        }
    };

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    public String getBath() {
        return bath;
    }

    public void setBath(String bath) {
        this.bath = bath;
    }

    public String getPrice_from() {
        return price_from;
    }

    public void setPrice_from(String price_from) {
        this.price_from = price_from;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        applyDefaultValues();
        dest.writeString(bed);
        dest.writeString(bath);
        dest.writeString(price_from);
    }

    private void applyDefaultValues() {
        if (bed == null)
            bed = "";
        if (bath == null)
            bath = "";
        if (price_from == null)
            price_from = "";
    }
}

