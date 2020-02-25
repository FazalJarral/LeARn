package com.example.learn.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learn.Adapter.AssetAdapter;
import com.example.learn.Helper.ItemClick;
import com.example.learn.Network.PolyApi;
import com.example.learn.R;
import com.example.learn.bean.Asset;
import com.example.learn.bean.ModelList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {
    private CustomArFragment fragment;
    ItemClick itemClick = null;
    String BASE_URL = "https://poly.googleapis.com/v1/";
    String API_KEY = "AIzaSyCAYctVXe-YtbJLj6See3kNmFRVGBeXedo";
    androidx.appcompat.widget.SearchView searchView;
    PolyApi polyApi;
    RecyclerView recyclerView;
    AssetAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home , container ,false);
        searchView = view.findViewById(R.id.search_view);
        recyclerView = view.findViewById(R.id.recyclerView);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragment = (CustomArFragment) getChildFragmentManager().findFragmentById(R.id.sceneform_fragment);
        fragment.getPlaneDiscoveryController().hide();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                updateModelList(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }


        });





    }

    private void updateModelList(String query) {
       Log.e("query" , query);
        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        polyApi = retrofit.create(PolyApi.class);

        Call<ModelList> models = polyApi.getModel(query  , API_KEY );
        Log.e("query" , models.request().toString());

       models.enqueue(new Callback<ModelList>() {
           @Override
           public void onResponse(Call<ModelList> call, Response<ModelList> response) {
              if (response.isSuccessful()){
                  Toast.makeText(getContext(), "Successful", Toast.LENGTH_SHORT).show();


                  List <Asset> assetList = response.body().getAssets();
                  List<Asset> gilfList = new ArrayList<>();

                /*  for (Asset myAsset : assetList){
                      if (myAsset.getFormat().getFormatType().contains("GLTF")){
                          gilfList.add(myAsset);
                      }
                  }*/

                  recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                  adapter = new AssetAdapter(getContext() , assetList , itemClick);
                  recyclerView.setAdapter(adapter);
                  recyclerView.setVisibility(View.VISIBLE);
              }
           }

           @Override
           public void onFailure(Call<ModelList> call, Throwable t) {
               Log.e("ErrorMessage" , t.getLocalizedMessage());

           }
       });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ItemClick){
            itemClick = (ItemClick) context;

        }else throw new RuntimeException(context.toString() + "implement listner");
    }




}
