package com.michaelzhang.apartmentguide.fragments;

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
import android.widget.AdapterView;
import android.widget.GridView;

import com.michaelzhang.apartmentguide.R;
import com.michaelzhang.apartmentguide.activities.AptDetailActivity;
import com.michaelzhang.apartmentguide.adapters.GalleryAdapter;
import com.michaelzhang.apartmentguide.models.Apartment;
import com.michaelzhang.apartmentguide.responses.AgServiceResponse;
import com.michaelzhang.apartmentguide.utils.AgServiceAPI;
import com.michaelzhang.apartmentguide.utils.AgServiceClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GalleryFragment extends Fragment {

    private GridView mGridView;
    private GalleryAdapter mAdapter;
    private OnFragmentInteractionListener mListener;

    public GalleryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGridView = view.findViewById(R.id.gallery_grid);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAdapter = new GalleryAdapter(getActivity(), 0);
        mGridView.setAdapter(mAdapter);

        AgServiceAPI service = AgServiceClient.getServiceClient(getContext())
                .create(AgServiceAPI.class);
        Call<AgServiceResponse> call = service.getAll();
        call.enqueue(new Callback<AgServiceResponse>() {
            @Override
            public void onResponse(Call<AgServiceResponse> call, Response<AgServiceResponse> response) {
                if (response == null || response.body() == null || !isAdded())
                    return;
                List<Apartment> apartments = response.body().getEmbedded().getApartments();
                for (Apartment ap : apartments) {
                    Log.e("Apartment: ", ap.getName());
                    mAdapter.add(ap);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<AgServiceResponse> call, Throwable t) {
                Log.e("GetApartments failed: ", t.getMessage());
            }
        });

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), AptDetailActivity.class);
                intent.putExtra(getContext().getString(R.string.apt_data_field), mAdapter.getItem(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gallery, container, false);
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
}
