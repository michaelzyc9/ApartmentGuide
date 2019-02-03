package com.example.apartmentguide.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.apartmentguide.R;
import com.example.apartmentguide.activities.AptDetailActivity;
import com.example.apartmentguide.adapters.ListAdapter;
import com.example.apartmentguide.models.ApartmentBuilding;
import com.example.apartmentguide.utils.GetApartmentsInterface;
import com.example.apartmentguide.utils.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFragment extends android.support.v4.app.ListFragment {

    private OnFragmentInteractionListener mListener;
    private ListAdapter mAdapter;

    public ListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAdapter = new ListAdapter(getActivity(), 0);
        GetApartmentsInterface service = RetrofitClientInstance.getRetrofitInstance(getContext())
                .create(GetApartmentsInterface.class);
        Call<List<ApartmentBuilding>> call = service.getApartments();
        call.enqueue(new Callback<List<ApartmentBuilding>>() {
            @Override
            public void onResponse(Call<List<ApartmentBuilding>> call, Response<List<ApartmentBuilding>> response) {
                if (response == null)
                    return;
                for (ApartmentBuilding ap : response.body()) {
                    Log.e("Apartment: ", ap.getName());
                    mAdapter.add(ap);
                }
                mAdapter.notifyDataSetChanged();
                setListAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<List<ApartmentBuilding>> call, Throwable t) {
                Log.e("GetApartments failed: ", t.getMessage());
            }
        });
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(getActivity(), AptDetailActivity.class);
        intent.putExtra(getContext().getString(R.string.apt_data_field), mAdapter.getItem(position));
        startActivity(intent);
    }
}
