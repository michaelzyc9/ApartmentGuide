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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ListAdapter extends ArrayAdapter<ApartmentBuilding> {
    public ListAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_row, parent, false);

            holder.thumbnail = convertView.findViewById(R.id.list_view_thumbnail);
            holder.name = convertView.findViewById(R.id.list_view_name);
            holder.address = convertView.findViewById(R.id.list_view_address);
            holder.fpInfo = convertView.findViewById(R.id.list_view_floorplans);
            holder.priceFrom = convertView.findViewById(R.id.list_view_price_from);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(getContext()).load(getItem(position).getImages()[0]).noFade().fit().into(holder.thumbnail);
        holder.name.setText(getItem(position).getName());
        holder.address.setText(getItem(position).getAddress());
        setFloorplanTextView(holder.fpInfo, getItem(position).getFloorPlans());
        holder.priceFrom.setText(getPriceFrom(position));

        return convertView;
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

    private void setFloorplanTextView(TextView textView, FloorPlan[] floorPlans) {
        Arrays.sort(floorPlans);
        String text = "";
        Map<String, Boolean> distinctBedNumber = new HashMap<>();
        for (int i = 0; i < floorPlans.length; i++) {
            String bedNumber = floorPlans[i].getBed();
            if (i == 0) {
                distinctBedNumber.put(bedNumber, true);
                text = "  " + bedNumber;
            } else if (!distinctBedNumber.containsKey(bedNumber)) {
                text += ", " + floorPlans[i].getBed();
                distinctBedNumber.put(bedNumber, true);
            }
        }
        textView.setText(text);
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bed, 0, 0, 0);
    }

    class ViewHolder {
        ImageView thumbnail;
        TextView name;
        TextView address;
        TextView fpInfo;
        TextView priceFrom;
    }
}
