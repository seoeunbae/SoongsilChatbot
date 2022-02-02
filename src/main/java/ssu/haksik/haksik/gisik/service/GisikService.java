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
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GisikService {
    private final GisikRepository gisikRepository;
    private final GisikCrawling gisikCrawling;

    @Transactional
//    @Scheduled(cron = "0 0 1 * * 1")
    @Scheduled(cron = "0 */2 * * * *")
    void saveGisik() throws IOException {
        Optional<Elements> crawledMenus= this.gisikCrawling.crawling();
        crawledMenus.orElseThrow(() -> new NoSuchElementException());
        for (int day=1;day<8;day++) {
            Elements MenusByDay = crawledMenus.get().get(day).getElementsByTag("td");
            String date = crawledMenus.get().get(day).getElementsByTag("a").text();
            for (int time =0; time<3; time++) {
                Element timeElement = MenusByDay.get(time);
                String MenusByTime = timeElement.html().replace("<br>", "\n").replace(" ", "").replace("&amp;", "&");
                String result = date+"\n\n".concat(MenusByTime).concat("\n\n");
                Optional<Gisik> gisikExist = this.gisikRepository.findByEatingTimeAndDay(time, day);

                if (!gisikExist.isPresent()) {
                    Gisik gisik = new Gisik(result, day, time);
                    this.gisikRepository.save(gisik);
                } else {
                    this.gisikRepository.updateGisikFoods(result, day, time);
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
            Optional<Gisik> gisik = this.gisikRepository.findByEatingTimeAndDay(time, day);
            gisik.orElseThrow(() -> new NoSuchElementException() );
            result.append(gisik.get().getFoods());
        }
        return result.toString();
    }
}