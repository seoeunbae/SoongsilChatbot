package ssu.haksik.haksik.common.crawling;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class FacultyLoungeCrawling {

    public String makeTodayFood(String url) throws IOException {

        if(isWeekend()){return "주말은 운영하지 않습니다.";}

        Elements menuListElements = crawling(url);

        Elements menuListElementsByEatingTime = menuListElements.get(0).getElementsByTag("div"); // 점심 식단과 저녁식단을 구분한 후 div를 기준으로 elements를 생성
        int menuListSize = menuListElementsByEatingTime.size();

        String foods = makeFoodList(menuListSize, menuListElementsByEatingTime);
        return foods;
    }

    public Elements crawling(String url) throws IOException {

        String todayUrl = url.concat(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        Elements menuListElements = Jsoup.connect(todayUrl).get().getElementsByAttributeValue("class", "menu_list");

        return menuListElements;
    }

    private Boolean isWeekend(){
        String todayString = LocalDateTime.now().getDayOfWeek().toString();
        if(todayString.equals("SUNDAY") || todayString.equals("SATURDAY")){
            return true;
        }
        return false;
    }

    private String makeFoodList(int menuListSize, Elements menuListElements){

        boolean isAboutFood = false; // 음식과 관련된 element만 stringbuilder에 추가하기 위해 사용
        StringBuilder foodList = new StringBuilder();

        for (int i = 0; i < menuListSize; i++){
            Element menuListElement = menuListElements.get(i);

            if(menuListElement.getElementsContainingText("*").hasText() || menuListElement.getElementsContainingText("6.0").hasText()){
                isAboutFood = true;
            }

            if(isAboutFood){
                if (menuListElement.getElementsContainingText("알러지").hasText()) {
                    break;
                }

                String food = extractFoodFromElements(menuListElement);
                if(!food.isEmpty()){
                    foodList.append(food+"\n");
                }
            }
        }
        return foodList.toString();
    }

    private String extractFoodFromElements(Element menuListElement){
        String food = menuListElement.text();

        if(food.isEmpty() ||(food.charAt(1)>=65 && food.charAt(1)<=122)){
            return "";
        }

        if(food.contains("-6.0") || food.contains(" - 6.0")){
            food = food.replace("-6.0","")
                    .replace(" - 6.0","");
        }

        return food;
    }
}
