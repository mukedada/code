package com.circle.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


/**
 * 爬虫工具类
 */
public class CrawlerUtil {

    public static void get_url(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            Element content = doc.getElementById("iframeResult");
            System.out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CrawlerUtil.get_url("http://kns.cnki.net/kns/brief/default_result.aspx");
    }
}
