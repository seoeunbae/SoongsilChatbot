package ssu.haksik.haksik.dodam.crawling;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import ssu.haksik.haksik.common.EatingTime;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static ssu.haksik.haksik.common.EatingTime.*;


@Component
public class DodamCrawling {

    public String crawling(String url, EatingTime eatingTime) throws IOException {

        LocalDateTime todayDateTime = LocalDateTime.now();
        DayOfWeek today = todayDateTime.getDayOfWeek();
        String todayString = today.toString();
        if(todayString.equals("SUNDAY") || todayString.equals("SATURDAY")){
            return "주말은 운영하지 않습니다.";
        }

        String todayUrl = url.concat(todayDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        Elements lunchAndDinnerMenuListElements = Jsoup.connect(todayUrl).get().getElementsByAttributeValue("class", "menu_list");

        if(lunchAndDinnerMenuListElements.size()==1 && eatingTime== DINNER){
            return "식단을 운영하지 않습니다.";
        }

        int time = eatingTime.ordinal();

        Element menuListElementDividedByTime = lunchAndDinnerMenuListElements.get(time);// 점심 식단과 저녁식단을 구분

        Elements menuListElements = menuListElementDividedByTime.getElementsByTag("div"); // for문을 몇 번 수행해야 하는지 정하기 위해 총 Element가 몇 가지의 div로 이루어져 있는지 구한다.
        int size = menuListElements.size();

        String foods = makeFoodList(size, menuListElements);
        return foods;
    }


    private String makeFoodList(int size, Elements menuListElements){

        boolean crawlingPart = false;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < size; i++){
            Element menuListElement = menuListElements.get(i);

            if(menuListElement.getElementsContainingText("*").hasText() || menuListElement.getElementsContainingText("5.0").hasText()){
                crawlingPart = true;
            }

            if(crawlingPart){
                if (menuListElement.getElementsContainingText("알러지").hasText()) {
                    break;
                }

                if (menuListElement.hasText()) {
                    String foods = menuListElement.text();
                    if(foods.charAt(1)>=65 && foods.charAt(1)<=122){
                        continue;
                    }
                    if(foods.contains("-5.0") || foods.contains(" - 5.0")){
                        foods.replace("-5.0","")
                                .replace(" - 5.0","");
                    }
                    sb.append(foods+"\n");
                }
            }
        }
        return sb.toString();
    }
}
