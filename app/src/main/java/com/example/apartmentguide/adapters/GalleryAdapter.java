package com.example.apartmentguide.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apartmentguide.R;
import com.example.apartmentguide.models.ApartmentBuilding;
import com.example.apartmentguide.models.FloorPlan;
import com.squareup.picasso.Picasso;

public class GalleryAdapter extends ArrayAdapter<ApartmentBuilding> {

    public GalleryAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_content, parent, false);

            holder.image = convertView.findViewById(R.id.grid_image);
            holder.priceFrom = convertView.findViewById(R.id.grid_price_from);
            holder.cityZip = convertView.findViewById(R.id.grid_city_zip);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(getContext()).load(getItem(position).getImages()[0]).noFade().fit().into(holder.image);
        holder.priceFrom.setText(getPriceFrom(position));
        holder.cityZip.setText(getCityAndZip(position));

        return convertView;
    }

    private String getCityAndZip(int position) {
        String address = getItem(position).getAddress();
        String[] addArr = address.split(",");
        if(addArr.length < 2)
            return null;
        String cityAndZip = addArr[addArr.length - 2] + ", " + addArr[addArr.length - 1];
        return cityAndZip;
    }

    private String getPriceFrom(int position) {
        String priceFrom = null;
        for (FloorPlan fp : getItem(position).getFloorPlans()) {
            if (priceFrom == null) {
                priceFrom = fp.getPriceFrom();
            } else {
                if (Integer.valueOf(fp.getPriceFrom()) < Integer.valueOf(priceFrom))
                    priceFrom = fp.getPriceFrom();
            }
        }
        return priceFrom == null ? "N/A" : ("From $" + priceFrom);
    }

    private class ViewHolder{
        ImageView image;
        TextView priceFrom;
        TextView cityZip;
    }
}
