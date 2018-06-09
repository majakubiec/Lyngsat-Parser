package com.satelite;


import com.mysql.jdbc.exceptions.jdbc4.MySQLDataException;

import java.sql.ResultSet;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        SateliteParser parser = new SateliteParser();

        // here you have list of a few parsed channels
        List<TVChannel> channels = parser.getTvChannels();

/*******THIS IS YOUR PLAYGROUND @MARICN*************************/
        // now you start db connection
        SateliteDataBase db = new SateliteDataBase();

        db.clearTables();
        db.clearTables1();
        db.clearTables2();
//    try {
        db.insertAllChannels(channels); // to be implmented, and many more =D
//    }catch (MySQLDataException ) {}
        for (TVChannel ch: channels) {
            db.getInfoCh(ch.getName());
        }



/***************************************************************/
        // shows list of channels saved in memory
//        parser.showMeWhatYouGot();
    }
}
