package com.example.apartmentguide.models;

import android.os.Parcel;
import android.os.Parcelable;

public class FloorPlan implements Parcelable {

    private String bed;
    private String bath;
    private String priceFrom;

    public FloorPlan() {
    }

    public FloorPlan(String bed, String bath, String priceFrom) {
        this.bed = bed;
        this.bath = bath;
        this.priceFrom = priceFrom;
    }

    protected FloorPlan(Parcel in) {
        bed = in.readString();
        bath = in.readString();
        priceFrom = in.readString();
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

    public String getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(String priceFrom) {
        this.priceFrom = priceFrom;
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
        dest.writeString(priceFrom);
    }

    private void applyDefaultValues() {
        if (bed == null)
            bed = "";
        if (bath == null)
            bath = "";
        if (priceFrom == null)
            priceFrom = "";
    }
}

