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
import java.util.List;

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
            String dateAndDay = dateAndFoodsElements.get(day.getValue()).getElementsByTag("a").text();
            String dayOfWeek = dateAndDay.substring(12,13);
            String date = dateAndDay.substring(0,10);
            System.out.println(date);
            for (GisikEatingTime eatingTime : GisikEatingTime.values()) {
                String foodsByEatingTime = foodsByDay.get(eatingTime.ordinal()).html().replace("<br>", "\n").replace(" ", "").replace("&amp;", "&");
                Gisik existGisik = this.gisikRepository.findByEatingTimeAndDate(eatingTime, date);
                if (existGisik == null) {
                    Gisik gisik = new Gisik(foodsByEatingTime, eatingTime, date , dayOfWeek);
                    gisikRepository.save(gisik);
                } else {
                    existGisik.changeFoods(foodsByEatingTime);
                }
            }
        }

    }

    @Transactional(readOnly = true)
    public String getThisWeekGisik(){
        StringBuilder foods = new StringBuilder();
        List<Gisik> all = gisikRepository.findAll();
        for (Gisik gisik: all){
            foods.append(gisik.getDate()+" ").append(gisik.getDayOfWeek()+" ").append(" ["+gisik.getEatingTime()+"]"+"\n").append(gisik.getFoods()+"\n");
        }
        return foods.toString();
    }

    @Transactional(readOnly = true)
    public String getGisikofDay(String date){
        StringBuilder foods = new StringBuilder();
        for (GisikEatingTime eatingTime : GisikEatingTime.values()) {
            Gisik gisik = this.gisikRepository.findByEatingTimeAndDate(eatingTime, date);
            foods.append(gisik.getDate()+" ").append(gisik.getDayOfWeek() +" ["+gisik.getEatingTime()+"]"+"\n").append(gisik.getFoods()+"\n");
        }
        return foods.toString();
    }
}
