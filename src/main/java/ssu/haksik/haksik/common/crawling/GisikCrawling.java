package ssu.haksik.haksik.common.crawling;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class GisikCrawling {
    public static String gisikCrawling(EatingTime eatingTime) throws IOException {
        String URL = "https://ssudorm.ssu.ac.kr:444/SShostel/mall_main.php?viewform=B0001_foodboard_list&board_no=1";
        Document document = Jsoup.connect(URL).get();
        Elements elements = document.getElementsByAttributeValue("class", "boxstyle02");
        Element element = elements.get(0);

        DayOfWeek dayOfWeek = LocalDateTime.now().getDayOfWeek();
        int day = dayOfWeek.getValue();
        int time = eatingTime.ordinal();

        Elements tr = element.select("tr");
        Elements td = tr.get(day).getElementsByTag("td");
        Element timeElement = td.get(time);
        String foods= timeElement.html().replace("<br>","\n").replace(" ","").replace("&amp;","&");
        String result = "<오늘의 메뉴>\n\n".concat(foods);
        return result;
    }
}
