package com.example.kalozteka;

public class VideoModel {
    private String title;
    private String kep;

    private String url;

    public VideoModel(String title, String thumbnailUrl,String url) {
        this.title = title;
        this.kep = thumbnailUrl;
        this.url=url;
    }

    public String getTitle() {
        return title;
    }

    public String getKep() {
        return kep;
    }

    public String getUrl() {
        return url;
    }
}
