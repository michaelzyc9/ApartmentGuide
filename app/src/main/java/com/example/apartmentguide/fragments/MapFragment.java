package com.example.apartmentguide.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apartmentguide.R;
import com.example.apartmentguide.activities.AptDetailActivity;
import com.example.apartmentguide.models.ApartmentBuilding;
import com.example.apartmentguide.models.GoogleMapApiResponse;
import com.example.apartmentguide.utils.GetApartmentsInterface;
import com.example.apartmentguide.utils.GoogleMapAPIs;
import com.example.apartmentguide.utils.GoogleMapApiClient;
import com.example.apartmentguide.utils.RetrofitClientInstance;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapFragment extends Fragment
        implements OnMapReadyCallback {

    private MapView mMapView;
    private GoogleMap mMap;
    private OnFragmentInteractionListener mListener;

    private static final LatLng MOUNTAIN_VIEW = new LatLng(37.4, -122.1);

    public MapFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mMapView = (MapView) view.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        mMapView.getMapAsync(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(MOUNTAIN_VIEW)
                .zoom(10)
                .bearing(0)
                .tilt(0)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.getUiSettings().setZoomControlsEnabled(true);

        GetApartmentsInterface service = RetrofitClientInstance.getRetrofitInstance(getContext())
                .create(GetApartmentsInterface.class);
        Call<List<ApartmentBuilding>> call = service.getApartments();
        call.enqueue(new Callback<List<ApartmentBuilding>>() {
            @Override
            public void onResponse(Call<List<ApartmentBuilding>> call, Response<List<ApartmentBuilding>> response) {
                if (response == null)
                    return;
                displayAptOnMap(response.body());
            }

            @Override
            public void onFailure(Call<List<ApartmentBuilding>> call, Throwable t) {
                Log.e("GetApartments failed: ", t.getMessage());
            }
        });
    }

    private void displayAptOnMap(final List<ApartmentBuilding> aptData) {
        GoogleMapAPIs googleMapAPIs = GoogleMapApiClient.getClient(getContext()).create(GoogleMapAPIs.class);

        for (final ApartmentBuilding ap : aptData) {

            Call<GoogleMapApiResponse> call = googleMapAPIs.getGeocodingResponse(ap.getAddress(),
                    getContext().getString(R.string.google_maps_key));
            call.enqueue(new Callback<GoogleMapApiResponse>() {
                @Override
                public void onResponse(Call<GoogleMapApiResponse> call, Response<GoogleMapApiResponse> response) {

                    Double lat = response.body().getResults().get(0).getGeometry().getLocation().getLat();
                    Double lng = response.body().getResults().get(0).getGeometry().getLocation().getLng();

                    if (lat == null || lng == null) {
                        Log.e("MapFragment", "lat or lng is NULL");
                    } else {
                        MarkerOptions options = new MarkerOptions().position(new LatLng(lat, lng));
                        options.title("From $" + ap.getPriceFrom());
                        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                        mMap.addMarker(options).setTag(ap);
                        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                            @Override
                            public void onInfoWindowClick(Marker marker) {
                                Intent intent = new Intent(getActivity(), AptDetailActivity.class);
                                intent.putExtra(getContext().getString(R.string.apt_data_field), (ApartmentBuilding) marker.getTag());
                                startActivity(intent);
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<GoogleMapApiResponse> call, Throwable t) {
                    Log.e("MapFragment", "API call failed");
                }
            });
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
