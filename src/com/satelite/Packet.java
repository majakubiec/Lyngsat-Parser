package com.satelite;

import java.util.ArrayList;
import java.util.List;

public class Packet {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<TVChannel> getTvchannels() {
        return tvchannels;
    }

    public void addTVChannel(TVChannel tvChannel){
        tvchannels.add(tvChannel);
    }


    private int id;
    private String name;
    private String url;
    private List<TVChannel> tvchannels = new ArrayList<>();

}
