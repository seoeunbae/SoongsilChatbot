package ssu.haksik.haksik.common.crawling;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HaksikCrawling {

    public static String crawling(String url, EatingTime eatingTime) throws IOException {

        LocalDateTime date = LocalDateTime.now();
        DayOfWeek day = date.getDayOfWeek();
        String today = day.toString();
        if(today.equals("SUNDAY") || today.equals("SATURDAY")){
            return "주말은 운영하지 않습니다.";
        }

        int time = eatingTime.ordinal()-1;
        System.out.println(time);
        String formatDate = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String todayUrl = url.concat(formatDate);
        Document document = Jsoup.connect(todayUrl).get();
        Elements elements = document.getElementsByAttributeValue("class", "menu_list");
        Element element = elements.get(time);
        System.out.println("element = " + element);
        Elements div = element.getElementsByTag("div");
        System.out.println("div = " + div);
        int size = div.size();

        boolean start = false;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < size; i++){
            if(div.get(i).getElementsContainingText("*").hasText() || div.get(i).getElementsContainingText("5.0").hasText()){
                start = true;
            }

            if(start){
                if (div.get(i).getElementsContainingText("알러지").hasText()) {
                    break;
                }

                if (div.get(i).hasText()) {
                    String foods = div.get(i).text();
                    if(foods.charAt(1)>=65 && foods.charAt(1)<=122){
                        continue;
                    }
                    if(foods.contains("-6.0")){
                        foods = foods.replace("-6.0","");
                    }
                    if(foods.contains(" - 6.0")){
                        foods = foods.replace(" - 6.0","");
                    }
                    if(foods.contains("-5.0")){
                        foods = foods.replace("-5.0","");
                    }
                    if(foods.contains(" - 5.0")){
                        foods = foods.replace(" - 5.0","");
                    }
                    sb.append(foods+"\n");
                }
            }
        }
        System.out.println(sb.toString());
        String foods = sb.toString();
        String menuBoard = "<오늘의 메뉴>\n\n".concat(foods);
        return menuBoard;
    }
}
