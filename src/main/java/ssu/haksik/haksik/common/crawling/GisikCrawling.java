package ssu.haksik.haksik.common.crawling;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class GisikCrawling {
    public static String gisikCrawling(EatingDay eatingday, GisikEatingTime eatingTime) throws IOException {
        String URL = "https://ssudorm.ssu.ac.kr:444/SShostel/mall_main.php?viewform=B0001_foodboard_list&board_no=1";
        Document document = Jsoup.connect(URL).get();
        Elements elements = document.getElementsByAttributeValue("class", "boxstyle02");
        Element element = elements.get(0);

        int day = eatingday.ordinal()+1;
        int time = eatingTime.ordinal();

        Elements tr = element.select("tr");
        Elements td = tr.get(day).getElementsByTag("td");
        Element timeElement = td.get(time);
        String result= timeElement.html().replace("<br>","\n").replace(" ","").replace("&amp;","&");
        return result;
    }
}
