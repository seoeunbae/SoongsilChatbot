package ssu.haksik.haksik.gisik.service;

import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssu.haksik.haksik.gisik.crawling.GisikCrawling;
import ssu.haksik.haksik.gisik.entity.Gisik;
import ssu.haksik.haksik.gisik.repository.GisikRepository;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GisikService {
    private final GisikRepository gisikRepository;
    private final GisikCrawling gisikCrawling;

    @Transactional
//    @Scheduled(cron = "0 0 1 * * 1")
    @Scheduled(cron = "0 */2 * * * *")
    public void saveGisik() throws IOException {
        Elements crawledMenus = this.gisikCrawling.crawling();
        for (int day=1;day<8;day++) {
            Elements menusByDay = crawledMenus.get(day).getElementsByTag("td");
            String date = crawledMenus.get(day).getElementsByTag("a").text();
            for (int time =0; time<3; time++) {
                Element timeElement = menusByDay.get(time);
                String menusByTime = timeElement.html().replace("<br>", "\n").replace(" ", "").replace("&amp;", "&");
                String result = date+"\n\n".concat(menusByTime).concat("\n\n");
                Gisik gisikExist = this.gisikRepository.findByEatingTimeAndDay(time, day);

                if (gisikExist == null) {
                    Gisik gisik = new Gisik(result, day, time);
                    this.gisikRepository.save(gisik);
                } else {
                    gisikExist.setFoods(result);
                }
            }
        }
    }

    @Transactional(readOnly = true)
    public String getThisWeekGisik(){
        StringBuilder result = new StringBuilder();

        List<Gisik> allGisik = this.gisikRepository.findAll();
        allGisik.forEach(gisik->{
            String gisikFoods = gisik.getFoods();
            result.append(gisikFoods);
        });
        return result.toString();
    }

    @Transactional(readOnly = true)
    public String getTodayGisik(){
        int day = LocalDateTime.now().getDayOfWeek().getValue();
        StringBuilder result = new StringBuilder();
        for(int time=0;time<3;time++) {
            Gisik gisik = this.gisikRepository.findByEatingTimeAndDay(time, day);
            result.append(gisik.getFoods());
        }
        return result.toString();
    }
}
