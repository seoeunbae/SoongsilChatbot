package ssu.haksik.haksik.common.crawling;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import java.io.IOException;


@Component
public class GisikCrawling {
    public Elements crawling() throws IOException {
        String URL = "https://ssudorm.ssu.ac.kr:444/SShostel/mall_main.php?viewform=B0001_foodboard_list&board_no=1";
        return Jsoup.connect(URL).get().getElementsByAttributeValue("class", "boxstyle02").get(0).select("tr");
    }
}