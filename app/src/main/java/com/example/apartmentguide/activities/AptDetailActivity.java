package com.example.apartmentguide.activities;

import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.apartmentguide.R;
import com.example.apartmentguide.models.ApartmentBuilding;
import com.example.apartmentguide.models.FloorPlan;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class AptDetailActivity extends AppCompatActivity
        implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private SliderLayout imageSlider;

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apt_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        ApartmentBuilding apt = getIntent().getExtras().getParcelable(getString(R.string.apt_data_field));

        TextView name = findViewById(R.id.list_detail_name);
        TextView address = findViewById(R.id.list_detail_address);
        TextView website = findViewById(R.id.list_detail_website);
        website.setMovementMethod(LinkMovementMethod.getInstance());

        setUpImageSlider(apt);
        name.setText(apt.getName());
        address.setText(apt.getAddress());
        website.setText(apt.getWebsite());
        setUpFloorPlanView(apt);

    }

    private void setUpFloorPlanView(ApartmentBuilding apt) {
        LinearLayout floorplanContent = findViewById(R.id.list_detail_floorplan_content);
        Arrays.sort(apt.getFloorPlans());

        for (int i = 0; i < apt.getFloorPlans().length; i++) {
            FloorPlan fp = apt.getFloorPlans()[i];
            RelativeLayout contentRow = new RelativeLayout(this);
            RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            contentRow.setLayoutParams(relativeParams);

            TextView floorplan = new TextView(this);
            floorplan.setId((new Random().nextInt(Integer.MAX_VALUE)) + 1);
            TextView priceFrom = new TextView(this);

            floorplan.setText(fp.getBed() + " bed, " + fp.getBath() + " bath");
            priceFrom.setText("From $" + fp.getPriceFrom());

            contentRow.addView(floorplan);
            contentRow.addView(priceFrom);

            //formatting layouts
            RelativeLayout.LayoutParams floorplanParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            floorplan.setLayoutParams(floorplanParams);

            RelativeLayout.LayoutParams priceParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            priceParams.addRule(RelativeLayout.RIGHT_OF, floorplan.getId());
            priceFrom.setLayoutParams(priceParams);
            priceFrom.setGravity(Gravity.RIGHT);

            floorplanContent.addView(contentRow);
        }
    }

    private void setUpImageSlider(ApartmentBuilding apt) {
        imageSlider = findViewById(R.id.list_detail_image_slider);

        HashMap<String, String> urlMap = new HashMap<String, String>();
        urlMap.put(getString(R.string.image_exterior_filename), apt.getImages()[0]);
        urlMap.put(getString(R.string.image_common_area_filename), apt.getImages()[1]);
        urlMap.put(getString(R.string.image_interior_filename), apt.getImages()[2]);

        HashMap<String, String> descriptionMap = new HashMap<String, String>();
        descriptionMap.put(getString(R.string.image_exterior_filename),
                getString(R.string.image_exterior_description));
        descriptionMap.put(getString(R.string.image_common_area_filename),
                getString(R.string.image_common_area_description));
        descriptionMap.put(getString(R.string.image_interior_filename),
                getString(R.string.image_interior_description));

        for (String name : urlMap.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            textSliderView
                    .description(descriptionMap.get(name))
                    .image(urlMap.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            imageSlider.addSlider(textSliderView);
        }
        imageSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        imageSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        imageSlider.setCustomAnimation(new DescriptionAnimation());
        imageSlider.setDuration(4000);
        imageSlider.addOnPageChangeListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
