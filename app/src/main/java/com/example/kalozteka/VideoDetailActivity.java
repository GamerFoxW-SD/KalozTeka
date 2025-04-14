package com.example.kalozteka;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kalozteka.models.UserModel;
import com.google.firebase.firestore.FirebaseFirestore;

public class VideoDetailActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);

        // Alapértelmezett ActionBar beállítása
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // Vissza gomb megjelenítése
            getSupportActionBar().setHomeButtonEnabled(true);        // A gomb aktívvá tétele
        }

        // WebView inicializálása
       /* webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);

        // Az Intentből átadott URL kinyerése
        String videoUrl = getIntent().getStringExtra("video_url");

        if (videoUrl != null) {
            // Ha van URL, akkor betöltjük a WebView-ba
            webView.loadData(videoUrl, "text/html", "utf-8");
        } else {
            // Ha nincs URL, egy hibaüzenetet jeleníthetünk meg
            Toast.makeText(this, "No video URL provided", Toast.LENGTH_SHORT).show();
        }*/

        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true); // gyakran kell Videa-nál
        webView.setWebChromeClient(new WebChromeClient()); // videó lejátszáshoz kell


        // Az Intentből átadott URL kinyerése
        String videoUrl = getIntent().getStringExtra("video_url");
        if (videoUrl != null && !videoUrl.isEmpty()) {
            String html = "<html><body style='margin:0;padding:0;'>"
                    + "<iframe width=\"100%\" height=\"100%\" "
                    + "src=\"" + videoUrl + "\" "
                    + "frameborder=\"0\" allowfullscreen></iframe>"
                    + "</body></html>";
            webView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
        } else {
            Toast.makeText(this, "Nincs videó ID megadva", Toast.LENGTH_SHORT).show();
        }

        String cim=getIntent().getStringExtra("video_cim");
        if (cim != null && !cim.isEmpty()){
            TextView c=findViewById(R.id.cim_text);
            c.setText(cim);
        }

        UserModel user = CurrentUser.getUser();
        Button b=findViewById(R.id.buttontorol);
        //b.setVisibility(View.GONE);

        if (user != null) {

            String id = user.getId();


            String UId = getIntent().getStringExtra("video_kuldo_id");

            if ( (UId != null && !UId.isEmpty())) {
                if (UId.equals(id))
                {
                    b.setVisibility(View.VISIBLE);
                    b.setOnClickListener(v -> {
                        String videoId = getIntent().getStringExtra("video_id");
                        if (videoId != null && !videoId.isEmpty()) {
                            torolVideoById(videoId);
                            // Visszalépés MainActivity-be
                            Intent intent = new Intent(this, MainActivity.class); // <-- cseréld ki CurrentActivityName-re
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(this, "Nincs kiválasztott videó azonosító!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    b.setVisibility(View.GONE);
                }
            }
        }


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

    private void torolVideoById(String id) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("videok")
                .document(id)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Log.d("Firestore", "Videó sikeresen törölve: " + id);
                    // Itt frissítheted az UI-t vagy értesítheted a felhasználót
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Hiba a videó törlésekor: " + id, e);
                });
    }

}
