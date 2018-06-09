package com.satelite;


import java.util.List;

public class Main {
    public static void main(String[] args) {

//        SateliteParser parser = new SateliteParser();
        SateliteDataBase db = new SateliteDataBase();

//         here you have list of a few parsed channels
//        List<TVChannel> channels = parser.getTvChannels();
        List<TVChannel> channels = db.getAllChannels();

        System.out.println(">> " + channels.size());

        new JtreeGUI(channels);

        // now you start db connection

//        db.clearTables();

//        System.out.println(">> inserting all channels");
//        db.insertAllChannels(channels);

//        for(Satelite sat : db.getSatsForChannelId( 1 )){
//            System.out.println(">> " + sat.getName() + " " + sat. getLink());
//        }
//
//        System.out.println("DEBUG INFO >> channels demo ");
//        for (TVChannel ch: channels) {
//            System.out.println( ">>" + db.getChannelDescription(ch.getName()) );
//            for (Satelite sat: ch.getSatelites()) {
//                System.out.println( "-- >>" + db.getSateliteDescription(sat.getName()) );
//            }
//        }
//



/***************************************************************/
        // shows list of channels saved in memory
//        parser.showMeWhatYouGot();
    }
}
