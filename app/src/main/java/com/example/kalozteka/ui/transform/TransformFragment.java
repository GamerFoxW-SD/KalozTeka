package com.example.kalozteka.ui.transform;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kalozteka.R;
import com.example.kalozteka.VideoAdapter;
import com.example.kalozteka.VideoModel;
import com.example.kalozteka.databinding.FragmentTransformBinding;
import com.example.kalozteka.databinding.ItemTransformBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Fragment that demonstrates a responsive layout pattern where the format of the content
 * transforms depending on the size of the screen. Specifically this Fragment shows items in
 * the [RecyclerView] using LinearLayoutManager in a small screen
 * and shows items using GridLayoutManager in a large screen.
 */
public class TransformFragment extends Fragment {

    private FragmentTransformBinding binding;
    private RecyclerView recyclerView;
    private VideoAdapter videoAdapter;
    private List<VideoModel> videoList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transform, container, false);




        recyclerView = view.findViewById(R.id.videoRecyclerView);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE); // Eltünteti a Toolbart



        videoList = new ArrayList<>();
        videoList.add(new VideoModel("Hogyan rohanj a veszTEDbe", "https://videa.hu/static/still/1.13.8.2782560.2313266.3?md5=y3zXQeuHjPb3w6vZIWocBQ&expires=1776108165","https://videa.hu/player?v=nhMJdweTdcfMBIkE"));
        videoList.add(new VideoModel("Cím 2", "",""));
        videoList.add(new VideoModel("Cím 3", "",""));


        videoAdapter = new VideoAdapter(getContext(), videoList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(videoAdapter);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}