package ssu.haksik.haksik.gisik.crawling;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.Optional;

@Component
public class GisikCrawling {
    public Optional<Elements> crawling() throws IOException {
        String URL = "https://ssudorm.ssu.ac.kr:444/SShostel/mall_main.php?viewform=B0001_foodboard_list&board_no=1";
        Document document = Jsoup.connect(URL).get();
        Elements elements = document.getElementsByAttributeValue("class", "boxstyle02");
        Element element = elements.get(0);
        Elements menus = element.select("tr");
        return Optional.ofNullable(menus);
    }
}