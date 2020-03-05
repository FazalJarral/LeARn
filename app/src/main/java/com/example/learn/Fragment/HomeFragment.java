package com.example.learn.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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
import com.example.learn.Helper.ItemSelect;
import com.example.learn.Network.PolyApi;
import com.example.learn.R;
import com.example.learn.bean.Asset;
import com.example.learn.bean.Format;
import com.example.learn.bean.ModelList;
import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment implements ItemSelect {
    private CustomArFragment fragment;
    ItemClick itemClick = null;
    String BASE_URL = "https://poly.googleapis.com/v1/";
    String API_KEY = "AIzaSyCAYctVXe-YtbJLj6See3kNmFRVGBeXedo";
    androidx.appcompat.widget.SearchView searchView;
    PolyApi polyApi;
    RecyclerView recyclerView;
    AssetAdapter adapter;
    Anchor anchor;
    String url = null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        searchView = view.findViewById(R.id.search_view);
        recyclerView = view.findViewById(R.id.recyclerView);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragment = (CustomArFragment) getChildFragmentManager().findFragmentById(R.id.sceneform_fragment);
        fragment.getPlaneDiscoveryController().hide();
        fragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
            anchor = hitResult.createAnchor();

        });

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
        Log.e("query", query);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        polyApi = retrofit.create(PolyApi.class);

        Call<ModelList> models = polyApi.getModel(query, API_KEY);
        Log.e("query", models.request().toString());

        models.enqueue(new Callback<ModelList>() {
            @Override
            public void onResponse(Call<ModelList> call, Response<ModelList> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Successful", Toast.LENGTH_SHORT).show();


                    List<Asset> assetList = response.body().getAssets();
                    List<Asset> gilfList = new ArrayList<>();
//error concurrentModificationException
                for (Asset myAsset : assetList){

                        Log.e("Asset"  , myAsset.getFormatList().toString());
                       for (Format formats : myAsset.getFormatList()){
                           if (formats.getFormatType().equalsIgnoreCase("obj")){
                               assetList.remove(myAsset);
                           }
                       }
                  }

                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new AssetAdapter(getContext(), assetList, itemClick);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ModelList> call, Throwable t) {
                Log.e("ErrorMessage", t.getLocalizedMessage());

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ItemClick) {
            itemClick = (ItemClick) context;

        } else throw new RuntimeException(context.toString() + "implement listner");
    }


    @Override
    public void onItemSelect(Asset asset) {
        Toast.makeText(getContext(), "Selected", Toast.LENGTH_LONG).show();
        recyclerView.setVisibility(View.GONE);
        placeAsset(asset);
    }

    private void placeAsset(Asset asset) {
        List<Format> formatLists = asset.getFormatList();
        for (Format format : formatLists) {

            // url = format.getFormatRoot().getUrl();
            url = "https://poly.googleapis.com/downloads/fp/1582664015931565/69ejysWdDXG/2Gi-kna7W1j/Sun_01.gltf";
            Log.e("url" , url);

        }
        ModelRenderable.builder()
                .setSource(getContext(), RenderableSource.builder().setSource(
                        getContext(),
                        Uri.parse(url),
                        RenderableSource.SourceType.GLTF2)
                        .setScale(0.00025f)  // Scale the original model to 50%.
                        .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                        .build())
                .setRegistryId(url)
                .build()
                .thenAccept(modelRenderable -> placeModel(modelRenderable, anchor))
                .exceptionally(
                        throwable -> {
                            Toast.makeText(getContext(), "Unable to load" + url, Toast.LENGTH_SHORT).show();
                            return null;
                        });
    }
    private void placeModel(ModelRenderable modelRenderable, Anchor anchor) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setRenderable(modelRenderable);
        fragment.getArSceneView().getScene().addChild(anchorNode);
        TransformableNode node = new TransformableNode(fragment.getTransformationSystem());
        node.setParent(anchorNode);
        node.setRenderable(modelRenderable);
        node.select();
    }
}
