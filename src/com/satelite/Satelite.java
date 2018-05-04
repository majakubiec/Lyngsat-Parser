package com.satelite;

class Satelite {
    int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    String getLink() {
        return link;
    }

    void setLink(String link) {
        this.link = link;
    }

    String getPosition() {
        return position;
    }

    void setPosition(String position) {
        this.position = position;
    }

    String getBand() {
        return band;
    }

    void setBand(String band) {
        this.band = band;
    }

    String getFreq() {
        return freq;
    }

    void setFreq(String freq) {
        this.freq = freq;
    }

    String getLang() {
        return lang;
    }

    void setLang(String lang) {
        this.lang = lang;
    }


    private int id;
    private String freq;
    private String name;
    private String link;
    private String position;
    private String band;
    private String lang;

    Satelite(){
        id = -1;
        name = null;
        link = null;
        position = null;
        band = null;
    }

}
