package com.satelite;

import java.sql.*;
import java.util.List;

class SateliteDataBase {

    private Connection conn = null;
    int id_ch = 1;
    int id_sat=1;

    SateliteDataBase(){
        /* mysql -u java -p java -h <IP> java */

        String dbIpAddr = "18.184.76.189";
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            conn = DriverManager.getConnection("jdbc:mysql://"+dbIpAddr+":3306/java", "java", "java");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void example (){
        /*inserts values into database */
        executeUpdate("INSERT INTO channels VALUES ('kanal1', 'link1')");
        try {
            /*requests data from db */
            ResultSet rs = executeQuery("SELECT * FROM channels");

            /* reads row by row and print data*/
            while (rs.next()) {
                System.out.println(rs.getString("name") + " " + rs.getString("link"));
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        /*deletes all entries from db*/
        clearTables();
    }

    void clearTables(){
        executeUpdate("DELETE FROM channels;");
        executeUpdate("DELETE FROM satelites;");
        executeUpdate("DELETE FROM ch_sat_map;");
    }

    void clearTables(String name){
        executeUpdate("DELETE FROM " + name + ";");
    }


    void executeUpdate(String update){
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(update);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    ResultSet executeQuery(String query){
        ResultSet rs = null;
        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
//                stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    void addChannel(int id, String name, String url, String lang){
        executeUpdate("INSERT INTO channels (ch_id, name, url, lang) VALUES (" +
                "" + id +
                ",'" + name +
                "','" + url +
                "','" + lang +
                "');");

    }

    void addChannel(TVChannel channel){
        executeUpdate("INSERT INTO channels(ch_id, name, url, lang) VALUES (" +
                "" + channel.getId() +
                "," + channel.getName() +
                "," + channel.getUrl() +
                "," + channel.getLang() +
                ");");

    }

    void addSatelite(int sat_id, String name, String freq, String url, String enc, String lang){
        executeUpdate("INSERT INTO satelites(sat_id, name, freq, link, lang) VALUES (" +
                "" + String.valueOf( sat_id ) +
                ",'" + name +
                "','" + freq +
                "','" + url +
                "','" + lang +
                "');");
    }

    void updateChannel(TVChannel channel){
        executeUpdate("UPDATE channels set VALUES (" +
                "" + channel.getName() +
                "," + channel.getUrl() +
                "," + channel.getUrl() +
                ");");

        // todo implement modifying entries (dont modify arguments)
        // dont care how
        // e.g. can first query data>compare> update ony these that differ
        //  or  update all data (easier and maybe even faster, dunno)
        // would be nice to first check if entry exists
    }

    void updateSatelite(Satelite satelite){
        // todo implement modifying entries (dont modify arguments)
        // dont care how (same as with updateChannel() )
    }

    void deleteChannel(TVChannel channel){
        executeUpdate("DELETE FROM channels where ch_id =" +channel.getId() +";") ;

        // todo implement deleting entries for channels
        // (dont modify arguments)
        // dont care how (same as with updateChannel() )
    }

    void deleteSatelite(Satelite satelite){
        executeUpdate("DELETE FROM satelites where sat_id =" +satelite.getId() +";") ;
        // todo implement deleting entries for sats
    }

    int getChannelId(TVChannel channel){
        return channel.getId();
        // todo implement geting channel id
        // (dont modify arguments)
        // dont care how (same as with updateChannel() )
//        return -1;
    }

    int getSateliteId(Satelite satelite){
        return satelite.getId();
        // todo implement geting sat id
        // (dont modify arguments)
        // dont care how (same as with updateChannel() )
//        return -1;
    }

/* COMMENTED BECAUSE DON'T NEED TO  BE IMPLEMENTED NOW
    [possibly never (to be deteted in such case) - we'll see xD]

    void getChannelId(String name){
        // todo implement modifying entries (dont modify arguments)
        // dont care how (same as with updateChannel() )
    }

    void getSateliteId(String name){
        // todo implement modifying entries (dont modify arguments)
        // dont care how (same as with updateChannel() )
    }
*/
    List<TVChannel> getAllChannels() {
        // todo implement getting all channel entries (dont modify arguments)
        // dont care how
        // returns an ArrayList of TVChannel objects
        return null;
    }

    void insertAllChannels(List<TVChannel> chls) {
        for (TVChannel ch: chls )
        {
            addChannel(id_ch,
                    ch.getName(),
                    ch.getUrl(),
                    ch.getLang());

            for(Satelite sat: ch.getSatelites())
            {
                addSatelite(id_sat, sat.getName(), sat.getFreq(), sat.getLink(),sat.getBand(), sat.getLang());
                mapSatToCh(id_sat,id_ch);
                id_sat++;
            }
            id_ch++;

        }
        // todo implement insetting channels list all at once (dont modify arguments)
    }

    List<Satelite> getAllSateilites() {
        // todo implement geting list of all satelite entries (dont modify arguments)
        // dont care how
        // returns an ArrayList of TVChannel objects
        return null;
    }

    void insertAllSatelites(List<Satelite> sats) {
        for (Satelite sat : sats )
        {
            addSatelite(id_sat,
                    sat.getName(),
                    sat.getFreq(),
                    sat.getLink(),
                    sat.getBand(), // todo powinno być enc ale jest band xD
                    sat.getLang());
            id_sat++;

        }
        // todo implement inserting whole list of sats at once (dont modify arguments)
    }

    void mapSatToCh(int sat_id, int cha_id){
        executeUpdate("INSERT INTO ch_sat_map(sat_id, ch_id) VALUES (" +
                "" + String.valueOf(sat_id )+
                "," + String.valueOf(cha_id )+
                ");");
    }

    String getChannelDescription(String name){

        String chinfo = "";

        try {
            /*requests data from db */
            ResultSet rs = executeQuery("SELECT * from channels where name like \'" + name + "\';");

            /* reads row by row and print data*/
            while (rs.next()) {
                chinfo = rs.getString("name") + " " + rs.getString("url");
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chinfo;
    }

    String getSateliteDescription(String name) {

        String chinfo = "";

        try {
            /*requests data from db */
            ResultSet rs = executeQuery("SELECT * from satelites where name like \'" + name + "\';");

            /* reads row by row and print data*/
            while (rs.next()) {
                chinfo = rs.getString("name") + " " + rs.getString("link");
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chinfo;
    }

}
