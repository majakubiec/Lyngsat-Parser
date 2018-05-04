package com.satelite;

import java.util.ArrayList;
import java.util.List;

public class TVChannel {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEncryption() {
        return encryption;
    }

    public void setEncryption(String encryption) {
        this.encryption = encryption;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setSatelites(List<Satelite> satelites) {
        this.satelites = satelites;
    }


    public List<Satelite> getSatelites() {
        return satelites;
    }

    public void addSatelite(Satelite satelite){
        satelites.add(satelite);
    }

    private int id;
    private int frequency;
    private String name;
    private String encryption;
    private String url;
    private String lang;
    private List<Satelite> satelites = new ArrayList<>();

}
