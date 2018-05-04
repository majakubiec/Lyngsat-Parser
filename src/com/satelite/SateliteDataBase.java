package com.satelite;

import java.sql.*;
import java.util.List;

class SateliteDataBase {

    private Connection conn = null;

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

    void addChannel(String name, String url, String lang){
        executeUpdate("INSERT INTO channels(name, freq, url, enc, lang) VALUES (" +
                "" + name +
                "" + url +
                "" + lang +
                ")");
    }

    void addSatelite(String name, String freq, String url, String enc, String lang){
        executeUpdate("INSERT INTO channels(name, freq, url, enc, lang) VALUES (" +
                "" + name +
                "" + freq +
                "" + url +
                "" + enc +
                "" + lang +
                ")");
    }

    void updateChannel(TVChannel channel){
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
        // todo implement deleting entries for channels
        // (dont modify arguments)
        // dont care how (same as with updateChannel() )
    }

    void deleteSatelite(Satelite satelite){
        // todo implement deleting entries for sats
        // (dont modify arguments)
        // dont care how (same as with updateChannel() )
    }

    int getChannelId(TVChannel channel){
        // todo implement geting channel id
        // (dont modify arguments)
        // dont care how (same as with updateChannel() )
        return -1;
    }

    int getSateliteId(Satelite satelite){
        // todo implement geting sat id
        // (dont modify arguments)
        // dont care how (same as with updateChannel() )
        return -1;
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
        // todo implement insetting channels list all at once (dont modify arguments)
        // dont care how
        // as an argument takes a list or set of Channel objects
    }

    List<Satelite> getAllSateilites() {
        // todo implement geting list of all satelite entries (dont modify arguments)
        // dont care how
        // returns an ArrayList of TVChannel objects
        return null;
    }

    void insertAllSatelites(List<Satelite> sats) {
        // todo implement inserting whole list of sats at once (dont modify arguments)
        // dont care how
        // as an argument takes a list or set of Satelite objects
    }
}
