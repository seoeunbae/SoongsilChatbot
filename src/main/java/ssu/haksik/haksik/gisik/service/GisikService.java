package ssu.haksik.haksik.gisik.service;

import lombok.RequiredArgsConstructor;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssu.haksik.haksik.gisik.crawling.GisikCrawling;
import ssu.haksik.haksik.gisik.entity.Gisik;
import ssu.haksik.haksik.gisik.enums.GisikEatingTime;
import ssu.haksik.haksik.gisik.repository.GisikRepository;
import java.io.IOException;
import java.time.DayOfWeek;

@Service
@RequiredArgsConstructor
public class GisikService {
    private final GisikRepository gisikRepository;
    private final GisikCrawling gisikCrawling;

    @Transactional
    @Scheduled(cron = "0 0 1 * * 1")
    public void saveGisik() throws IOException {
        Elements dateAndFoodsElements = this.gisikCrawling.crawling();
        for (DayOfWeek day : DayOfWeek.values()) {
            Elements foodsByDay = dateAndFoodsElements.get(day.getValue()).getElementsByTag("td");
            String date = dateAndFoodsElements.get(day.getValue()).getElementsByTag("a").text();
            for (GisikEatingTime eatingTime : GisikEatingTime.values()) {
                String foodsByEatingTime = foodsByDay.get(eatingTime.ordinal()).html().replace("<br>", "\n").replace(" ", "").replace("&amp;", "&");
                Gisik existGisik = this.gisikRepository.findByEatingTimeAndDay(eatingTime, day);

                if (existGisik == null) {
                    Gisik gisik = new Gisik(date+"\n"+foodsByEatingTime, eatingTime, day);
                    this.gisikRepository.save(gisik);
                } else {
                    existGisik.changeFoods(date+"\n"+foodsByEatingTime);
                }
            }
        }
    }

    @Transactional(readOnly = true)
    public String getThisWeekGisik(){
        StringBuilder foods = new StringBuilder();
        for (DayOfWeek day : DayOfWeek.values()){
            foods.append(getGisikofDay(day));
        }
        return foods.toString();
    }

    @Transactional(readOnly = true)
    public String getGisikofDay(DayOfWeek day){
        StringBuilder foods = new StringBuilder();
        for (GisikEatingTime eatingTime : GisikEatingTime.values()) {
            Gisik gisik = this.gisikRepository.findByEatingTimeAndDay(eatingTime, day);
            foods.append(gisik.getFoods()+"\n");
        }
        return foods.toString();
    }
}
