package com.example.kalozteka;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
        String videoId = getIntent().getStringExtra("video_id");
        if (videoId != null && !videoId.isEmpty()) {
            String html = "<html><body style='margin:0;padding:0;'>"
                    + "<iframe width=\"100%\" height=\"100%\" "
                    + "src=\"" + videoId + "\" "
                    + "frameborder=\"0\" allowfullscreen></iframe>"
                    + "</body></html>";
            webView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
        } else {
            Toast.makeText(this, "Nincs videó ID megadva", Toast.LENGTH_SHORT).show();
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
}
