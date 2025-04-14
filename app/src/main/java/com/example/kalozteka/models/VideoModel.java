package com.example.kalozteka.models;

import kotlin.text.UStringsKt;

public class VideoModel {
    private String title;
    private String kep;

    private String url;

    private String uid;

    private String id;

    public VideoModel(String title, String thumbnailUrl,String url) {
        this.title = title;
        this.kep = thumbnailUrl;
        this.url=url;

    }

    public VideoModel(String title, String thumbnailUrl,String url,String uid) {
        this(title,thumbnailUrl,url);
        this.uid=uid;
    }

    public VideoModel(String title, String thumbnailUrl,String url,String uid,String id) {
        this(title,thumbnailUrl,url,uid);
        this.id=id;
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

    public String getUid(){
        return this.uid;
    }

    public String getId(){
        return this.id;
    }

    public VideoModel get(){
        return this;
    }
}
