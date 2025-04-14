package com.example.kalozteka;


import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UjvideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ujvideo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // Vissza gomb megjelenítése
            getSupportActionBar().setHomeButtonEnabled(true);        // A gomb aktívvá tétele
        }


        EditText editCim = findViewById(R.id.editCim);
        EditText editUrl = findViewById(R.id.editUrl);
        EditText editKepUrl = findViewById(R.id.editKepUrl);
        Button btnMentes = findViewById(R.id.btnMentes);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        btnMentes.setOnClickListener(v -> {
            String cim = editCim.getText().toString().trim();
            String url = editUrl.getText().toString().trim();
            String kepUrl = editKepUrl.getText().toString().trim();
            String userId = auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : "ismeretlen";

            // Ellenőrzés (opcionális)
            if (cim.isEmpty() || url.isEmpty()) {
                Toast.makeText(this, "A cím és az URL megadása kötelező", Toast.LENGTH_SHORT).show();
                return;
            }

            // Új videó objektum létrehozása
            Map<String, Object> video = new HashMap<>();
            video.put("cim", cim);
            video.put("url", url);
            video.put("kep_url", kepUrl);
            video.put("user_id", userId);

            // Dokumentum mentése a „videok” kollekcióba
            db.collection("videok")
                    .add(video)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(this, "Videó mentve!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Hiba a mentés során: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    });
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Vissza a korábbi oldalra
            onBackPressed();  // Automatikusan a megfelelő oldalra ugrik
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}