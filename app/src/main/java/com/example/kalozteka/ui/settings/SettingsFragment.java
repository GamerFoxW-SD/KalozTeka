package com.example.kalozteka.ui.settings;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.kalozteka.CurrentUser;
import com.example.kalozteka.R;
import com.example.kalozteka.databinding.FragmentSettingsBinding;
import com.example.kalozteka.models.UserModel;
import com.google.firebase.firestore.FirebaseFirestore;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private String eredetiNev = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        EditText nevt = root.findViewById(R.id.editnevtext);
        Button mentesGomb = root.findViewById(R.id.button);
        UserModel user = CurrentUser.getUser();

        if (user != null) {
            eredetiNev = user.getNev();
            nevt.setText(eredetiNev);
            mentesGomb.setEnabled(false);

            // 🔁 Szöveg változás figyelése
            nevt.addTextChangedListener(new TextWatcher() {
                @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String ujNev = s.toString().trim();
                    mentesGomb.setEnabled(!ujNev.isEmpty() && !ujNev.equals(eredetiNev));
                }
                @Override public void afterTextChanged(Editable s) {}
            });

            // ✅ Gomb esemény: csak egyszer regisztrálva
            mentesGomb.setOnClickListener(v -> {
                String ujNev = nevt.getText().toString().trim();

                if (!ujNev.isEmpty() && !ujNev.equals(eredetiNev)) {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    String userId = user.getId();

                    db.collection("User").document(userId)
                            .update("nev", ujNev)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(root.getContext(), "Név frissítve", Toast.LENGTH_SHORT).show();
                                // 🔄 Frissítjük a globális user-t is
                                CurrentUser.setUser(new UserModel(ujNev, userId));
                                eredetiNev = ujNev;
                                mentesGomb.setEnabled(false);
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(root.getContext(), "Hiba történt: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                }
            });
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
