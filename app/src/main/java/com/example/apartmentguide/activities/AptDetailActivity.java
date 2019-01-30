package com.example.apartmentguide.activities;

import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apartmentguide.R;
import com.example.apartmentguide.models.ApartmentBuilding;
import com.squareup.picasso.Picasso;

public class AptDetailActivity extends AppCompatActivity {

    public static final String APT_DATA = "extra_apt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apt_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        ApartmentBuilding apt = getIntent().getExtras().getParcelable(APT_DATA);

        ImageView image = findViewById(R.id.list_detail_image);
        TextView name = findViewById(R.id.list_detail_name);
        TextView address = findViewById(R.id.list_detail_address);
        TextView website = findViewById(R.id.list_detail_website);

        Picasso.get().load(apt.getImages()[0]).noFade().fit().into(image);
        name.setText(apt.getName());
        address.setText(apt.getAddress());
        website.setText(apt.getWebsite());


        //TODO Floorplan section




    }
}
