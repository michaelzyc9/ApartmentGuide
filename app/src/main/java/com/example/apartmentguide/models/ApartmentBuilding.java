package com.example.apartmentguide.models;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

public class ApartmentBuilding implements Parcelable {

    private String name;
    private String address;
    private String website;
    private String[] images;
    private FloorPlan[] floor_plans;

    public ApartmentBuilding() {
    }

    public ApartmentBuilding(String name, String address, String website, String[] images, FloorPlan[] floor_plans) {
        this.name = name;
        this.address = address;
        this.website = website;
        this.images = images;
        this.floor_plans = floor_plans;
    }

    protected ApartmentBuilding(Parcel in) {
        name = in.readString();
        address = in.readString();
        website = in.readString();
        images = in.createStringArray();
        floor_plans = in.createTypedArray(FloorPlan.CREATOR);
    }

    public static final Creator<ApartmentBuilding> CREATOR = new Creator<ApartmentBuilding>() {
        @Override
        public ApartmentBuilding createFromParcel(Parcel in) {
            return new ApartmentBuilding(in);
        }

        @Override
        public ApartmentBuilding[] newArray(int size) {
            return new ApartmentBuilding[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public FloorPlan[] getFloorPlans() {
        return floor_plans;
    }

    public void setFloor_plans(FloorPlan[] floor_plans) {
        this.floor_plans = floor_plans;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        applyDefaultValues();
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(website);
        dest.writeStringArray(images);
        dest.writeTypedArray(floor_plans, flags);
    }

    private void applyDefaultValues() {
        if (name == null)
            name = "";
        if (address == null)
            address = "";
        if (website == null)
            website = "";
        if (images == null)
            images = new String[0];
        if (floor_plans == null)
            floor_plans = new FloorPlan[0];
    }

    public String getPriceFrom(){
        int priceFrom = Integer.MAX_VALUE;
        for(FloorPlan fp: floor_plans){
            int fpPriceFrom = Integer.valueOf(fp.getPriceFrom());
            if(fpPriceFrom < priceFrom)
                priceFrom = fpPriceFrom;
        }
        return String.valueOf(priceFrom);
    }
}
