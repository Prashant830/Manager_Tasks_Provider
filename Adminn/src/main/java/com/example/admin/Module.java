package com.example.admin;

public class Module {
    String songUrl,songName;

    public String getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public Module(String songUrl, String songName) {
        this.songUrl = songUrl;
        this.songName = songName;
    }
    public Module(){

    }
}
