package com.example.apartmentguide.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ApartmentBuilding implements Parcelable {

    private String name;
    private String address;
    private String website;
    private String[] images;
    private FloorPlan[] floorPlans;

    public ApartmentBuilding() {
    }

    public ApartmentBuilding(String name, String address, String website, String[] images, FloorPlan[] floorPlans) {
        this.name = name;
        this.address = address;
        this.website = website;
        this.images = images;
        this.floorPlans = floorPlans;
    }

    protected ApartmentBuilding(Parcel in) {
        name = in.readString();
        address = in.readString();
        website = in.readString();
        images = in.createStringArray();
        floorPlans = in.createTypedArray(FloorPlan.CREATOR);
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
        return floorPlans;
    }

    public void setFloorPlans(FloorPlan[] floorPlans) {
        this.floorPlans = floorPlans;
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
        dest.writeTypedArray(floorPlans, flags);
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
        if (floorPlans == null)
            floorPlans = new FloorPlan[0];
    }
}
