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

public class ListViewAdapter extends ArrayAdapter<ApartmentBuilding> {
    public ListViewAdapter(Context context, int resource) {
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

        Picasso.get().load(getItem(position).getImages()[0]).noFade().fit().into(holder.thumbnail);
        holder.name.setText(getItem(position).getName());
        holder.address.setText(getItem(position).getAddress());
        holder.fpInfo.setText("Floor plan section");
        holder.priceFrom.setText(getPriceFrom(position));

        return convertView;
    }

    private String getPriceFrom(int position) {
        String priceFrom = null;
        for(FloorPlan fp: getItem(position).getFloorPlans()){
            if(priceFrom == null){
                priceFrom = fp.getPriceFrom();
            } else {
                if(Integer.valueOf(fp.getPriceFrom()) < Integer.valueOf(priceFrom))
                    priceFrom = fp.getPriceFrom();
            }
        }
        return priceFrom == null ? "N/A" : ("From $" + priceFrom);
    }

    private TextView getFpInfoText(View convertView) {
        TextView test = new TextView(getContext());
        test.setText("TEST FP INFO SECTION");
        return test;
    }

    class ViewHolder {
        ImageView thumbnail;
        TextView name;
        TextView address;
        TextView fpInfo;
        TextView priceFrom;
    }
}
