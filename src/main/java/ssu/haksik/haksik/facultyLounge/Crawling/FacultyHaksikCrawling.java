package ssu.haksik.haksik.facultyLounge.Crawling;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FacultyHaksikCrawling {

    public static String crawling() throws IOException {

        LocalDateTime date = LocalDateTime.now();
        DayOfWeek day = date.getDayOfWeek();
        String today = day.toString();
        if(today.equals("SUNDAY") || today.equals("SATURDAY")){
            return "주말은 운영하지 않습니다.";
        }
        String formatDate = date.format(DateTimeFormatter.ofPattern("yyyyMMdd")); // 20190513으로 출력 System.out.println(formatDate);

        String URL = "http://m.soongguri.com/m_req/m_menu.php?rcd=7&sdt="+formatDate;
        Document document = Jsoup.connect(URL).get();
        Elements elements = document.getElementsByAttributeValue("class", "menu_list");
        Element element = elements.get(0);
        Elements div = element.getElementsByTag("div");
        int size = div.size();

        boolean start = false;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < size; i++){
            if(div.get(i).getElementsContainingText("*").hasText() || div.get(i).getElementsContainingText("6.0").hasText()){
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
                    sb.append(foods+"\n");
                }
            }
        }
        String foods = sb.toString();
        return foods;
    }
}