package com.satelite;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import java.io.IOException;


class SateliteParser {
    //    private HashSet<TVChannel> tvChannels = new HashSet<>(1000);
    private List<TVChannel> tvChannels = new ArrayList<>(1000);
//    private HashSet<Packet> packets = new HashSet<>(1000);

    public int siteCounter = 0;

    SateliteParser(){
        System.out.println("Starting Parser");
        HashSet<String> sateliteUrlSet = getSateliteUrls();

        /* for testing purpouses -- to bo later removed and dont forget to uncomment ^^look up^^ */
//        HashSet<String> sateliteUrlSet = new HashSet<>();
//        sateliteUrlSet.add("https://www.lyngsat.com/Eutelsat-Hot-Bird-13E.html");

        System.out.println("Having " + sateliteUrlSet.size() + " sats to parse, lets do it ;)\n");
        for (String satUrl : sateliteUrlSet){
            parseUrl(satUrl);
        }

    }

    private HashSet<String> getSateliteUrls(){
        HashSet<String> sateliteUrlSet = new HashSet<>(100);
        String url = "https://www.lyngsat.com/europe.html";
        Document doc = null;
        List<Element> trsList = new ArrayList<>();

        doc = fetchWebsite(url);
        if (doc == null) return null;

        Elements trs = doc.select("tr");
        for( Element tr : trs ){
            if (tr.select("td").size() == 5){
                trsList.add(tr);
            }
        }
        for( Element tr : trsList ){
            Elements td = tr.select("td");

            String link = td.get(2).select("a[href]").attr("href");
            sateliteUrlSet.add(link);
        }

        return sateliteUrlSet;
    }



    private void parseUrl(String url){
        if (url.contains("/tvchannels/")){
            parseChannelUrl(url);
        } else if (url.contains("/packages/")){
//            parsePacketUrl(url);
        } else if (url.contains("/providers/")){
//            parseProviderUrl(url);
        } else if (url.contains("/radiochannels/")){
            // todo (maybe in a future)
        } else{
            parseSateliteUrl(url);
        }
    }

    public void parseSateliteUrl(String url){
        // contains information about channels or/and packets
        System.out.println("Parsing sat: " + extractNameFromUrl(url));
        Document doc = null;

        HashSet<String> tvChannelUrls = new HashSet<>();
        HashSet<String> packetUrls = new HashSet<>();

        doc = fetchWebsite(url);
        if (doc == null) return;

        for (Element href : doc.select("a[href]")) {
            String lnk = href.attr("href");
            String name = href.text();
            if (lnk.contains("/tvchannels/") && !name.isEmpty()) {
                tvChannelUrls.add(lnk);

            } else if (lnk.contains("/packages/") && !name.isEmpty()){
                packetUrls.add(lnk);

            }
        }
        System.out.println(">> Has " + tvChannelUrls.size() + " channel urls");
        for (String chUrl : tvChannelUrls) {
//            parseChannelUrl(chUrl);
            parseUrl(chUrl);
        }
        for (String paUrl : packetUrls) {
//            parsePacketUrl(paUrl);
            parseUrl(paUrl);
        }
    }

    public void parseChannelUrl(String url){
        // contains information about satelites
        System.out.println(">> Parsing channel: " + url);
        if (!url.contains("/tvchannels/")){
            return;
        }

        Document doc = null;

        TVChannel tvChannel = new TVChannel();
        tvChannel.setName(extractNameFromUrl(url));
        tvChannel.setUrl(url);

        doc = fetchWebsite(url);
        if (doc == null) return;

        Elements trs = doc.select("tr");
        List<Element> e = new ArrayList<>();
        for (Element tr : trs){
            if (tr.select("td").size() == 11 ){
                e.add(tr);
            }
        }
        if (e.size() == 0){
            return;
        }

        e.remove(0); // remove column desctiption
        for (Element tr : e) {
            Elements td = tr.select("td");
            String position = td.get(1).text();
            String name = td.get(2).text();
            String freq = td.get(4).text();
            String lang = td.get(9).text();
            String lnk = td.get(2).select("a[href]").attr("href");

            Satelite satelite = new Satelite();
            satelite.setPosition( position );
            satelite.setName(name);
            satelite.setLink(lnk);
            satelite.setFreq(freq);
            satelite.setLang(lang);

            tvChannel.addSatelite(satelite);

        }
        tvChannels.add(tvChannel);
    }

    private String extractNameFromUrl(String url){
        String[] linkSplit = url.split("/");
        String name = linkSplit[linkSplit.length-1].replace(".html", "");
        return name.replace("-", " ");
    }

    public void parsePacketUrl(String url){
        // contains information about channels
        System.out.println("Parsing pack: " + extractNameFromUrl(url));

        Document doc = null;

        Packet packet = new Packet();
        packet.setName(extractNameFromUrl(url));
        packet.setUrl(url);

        doc = fetchWebsite(url);

        Elements trs = doc.select("tr");
        List<Element> e = new ArrayList<>();
        for (Element tr : trs){
            int trSize = tr.select("td").size();
            if ( trSize == 9 || trSize == 10){
                boolean isNotDesc = !tr.select("td").get(2).text().contains("Channel");
                boolean isNotBalnk = !tr.select("a[href]").attr("href").equals("");
                if (isNotDesc && isNotBalnk){
                    e.add(tr);
                }
            }
        }

        System.out.println("> Having " + e.size() + " channel links to parse");
        for (Element tr : e) {
            Elements td = tr.select("td");

            int offset = 0;
            if (td.size() == 9) {
                offset = -1;
            } else if (td.size() == 10) {
                offset = 0;
            }
            String chLink = td.get(2 + offset).select("a[href]").attr("href");

//            String chNo = td.get(5+offset).text();
//            String chSID = td.get(6+offset).text();
//            String chVPID = td.get(7+offset).text();
//            String chAPID = td.get(8+offset).text();

//            parseChannelUrl(chLink);
            if (!chLink.equals("")){
                parseUrl(chLink);
            }
        }
    }

    private void parseProviderUrl(String url){
        // contains information about ???

    }

    public void showMeWhatYouGot(){
        System.out.println("\n\n ---------------------------------------------------------------");
        System.out.println("There are summary " + tvChannels.size() + " chanels:");


        for (TVChannel ch : tvChannels){
            System.out.println("> " + ch.getName() + " - " + ch.getUrl());
            for (Satelite sat : ch.getSatelites()){
                System.out.println(">> " +
                        sat.getName() + ", " +
                        sat.getPosition() + ", " +
                        sat.getFreq() + ", " +
                        sat.getLang());
            }
            System.out.println();
        }
    }
    private Document fetchWebsite(String url){
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
            siteCounter++;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return doc;
    }

    public List<TVChannel> getTvChannels() {
        return tvChannels;
    }
}
