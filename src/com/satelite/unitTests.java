package com.satelite;


import java.sql.ResultSet;

public class unitTests {
    void test(){
        SateliteParser parser = new SateliteParser();
        SateliteDataBase db = new SateliteDataBase();

        db.clearTables();
        for (TVChannel ch : parser.getTvChannels()){
            db.addChannel(ch.getName(), ch.getUrl(), ch.getLang());
        }
//        ResultSet rs = db.getChannels();

    }
}
