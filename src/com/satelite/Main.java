package com.satelite;


import java.util.List;

public class Main {
    public static void main(String[] args) {

        SateliteParser parser = new SateliteParser();

        // here you have list of a few parsed channels
        List<TVChannel> channels = parser.getTvChannels();
        new JtreeGUI(channels);

/*******THIS IS YOUR PLAYGROUND @MARICN*************************/
        // now you start db connection
        //SateliteDataBase db = new SateliteDataBase();


//        db.addChannel(channels); // to be implmented, and many more =D



/***************************************************************/
        // shows list of channels saved in memory
//        parser.showMeWhatYouGot();
    }
}
