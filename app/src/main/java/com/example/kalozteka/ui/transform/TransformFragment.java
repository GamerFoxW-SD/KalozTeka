package com.example.kalozteka.ui.transform;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kalozteka.R;
import com.example.kalozteka.VideoAdapter;
import com.example.kalozteka.models.VideoModel;
import com.example.kalozteka.databinding.FragmentTransformBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
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

        if (getActivity() != null) {
            Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
            if (toolbar != null) {
                toolbar.setVisibility(View.GONE);
            } else {
                Log.w("TransformFragment", "Toolbar nem található.");
            }
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Log.d("Firestore", "FirebaseFirestore példány inicializálva.");

        videoList = new ArrayList<>();
   /*     videoList.add(new VideoModel("Hogyan rohanj a veszTEDbe", "https://videa.hu/static/still/1.13.8.2782560.2313266.3?md5=y3zXQeuHjPb3w6vZIWocBQ&expires=1776108165","https://videa.hu/player?v=nhMJdweTdcfMBIkE"));
        videoList.add(new VideoModel("Cím 2", "","",""));
        videoList.add(new VideoModel("Cím 3", "",""));

    */
        db.collection("videok")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        String cim = document.getString("cim");
                        String kepUrl = document.getString("kep_url");
                        String url = document.getString("url");
                        String userId = document.getString("user_id");
                       /* Log.d("Firestore", "Dokumentum ID: " + document.getId());
                        Log.d("Firestore", "cim: " + document.getString("cim"));
                        Log.d("Firestore", "url: " + document.getString("url"));
                        Log.d("Firestore", "kep_url: " + document.getString("kep_url"));
                        Log.d("Firestore", "user_id: " + document.getString("user_id"));*/

                        VideoModel video = new VideoModel(cim, kepUrl, url, userId,document.getId());
                        videoList.add(video);
                    }
                    videoAdapter = new VideoAdapter(getContext(), videoList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(videoAdapter);

                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Hiba a videók lekérdezésekor", e);
                });

        EditText searchEditText = view.findViewById(R.id.searchEditText);

// A videók listájának betöltése a Firestore-ból ugyanaz marad...

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keresettSzoveg = s.toString().toLowerCase();
                List<VideoModel> szurtLista = new ArrayList<>();

                for (VideoModel video : videoList) {
                    if (video.getTitle() != null && video.getTitle().toLowerCase().contains(keresettSzoveg)) {
                        szurtLista.add(video);
                    }
                }

                videoAdapter.updateList(szurtLista);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}