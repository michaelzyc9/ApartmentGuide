
package com.mz.apartmentguide.models;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Apartment implements Parcelable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    @SerializedName("floorPlans")
    @Expose
    private List<FloorPlan> floorPlans = null;

    public final static Parcelable.Creator<Apartment> CREATOR = new Creator<Apartment>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Apartment createFromParcel(Parcel in) {
            return new Apartment(in);
        }

        public Apartment[] newArray(int size) {
            return (new Apartment[size]);
        }

    };

    protected Apartment(Parcel in) {
        this.images = new ArrayList<>();
        this.floorPlans = new ArrayList<>();
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.website = ((String) in.readValue((String.class.getClassLoader())));
        this.address = ((Address) in.readValue((Address.class.getClassLoader())));
        in.readList(this.images, (com.mz.apartmentguide.models.Image.class.getClassLoader()));
        in.readList(this.floorPlans, (com.mz.apartmentguide.models.FloorPlan.class.getClassLoader()));
    }

    public Apartment() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<FloorPlan> getFloorPlans() {
        return floorPlans;
    }

    public void setFloorPlans(List<FloorPlan> floorPlans) {
        this.floorPlans = floorPlans;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(website);
        dest.writeValue(address);
        dest.writeList(images);
        dest.writeList(floorPlans);
    }

    public int describeContents() {
        return 0;
    }

    public String getPriceFrom() {
        int priceFrom = Integer.MAX_VALUE;
        for (FloorPlan fp : floorPlans) {
            int fpPriceFrom = Integer.valueOf(fp.getPriceFrom());
            if (fpPriceFrom < priceFrom)
                priceFrom = fpPriceFrom;
        }
        return String.valueOf(priceFrom);
    }
}
