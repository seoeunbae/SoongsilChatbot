package ssu.haksik.haksik.dodam.crawling;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import ssu.haksik.haksik.common.enums.EatingTime;
import ssu.haksik.haksik.dodam.exception.DodamDinnerNotExistException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static ssu.haksik.haksik.common.enums.EatingTime.*;


@Component
public class DodamCrawling {

    public String makeTodayFood(String url, EatingTime eatingTime) throws IOException {

        if(isWeekend()){return "주말은 운영하지 않습니다.";}

        Elements lunchAndDinnerMenuListElements = crawling(url, eatingTime);

        Elements menuListElementsByEatingTime = lunchAndDinnerMenuListElements.get(eatingTime.ordinal()).getElementsByTag("div"); // 점심 식단과 저녁식단을 구분한 후 div를 기준으로 elements를 생성
        int menuListSize = menuListElementsByEatingTime.size();

        String foods = makeFoodList(menuListSize, menuListElementsByEatingTime);
        return foods;
    }

    private Elements crawling(String url, EatingTime eatingTime) throws IOException{

        String todayUrl = url.concat(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        Elements lunchAndDinnerMenuListElements = Jsoup.connect(todayUrl).get().getElementsByAttributeValue("class", "menu_list");

        if(lunchAndDinnerMenuListElements.size()==1 && eatingTime== DINNER){
            throw new DodamDinnerNotExistException("저녁 식단을 운영하지 않습니다.");
        }
        return lunchAndDinnerMenuListElements;
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

            if(menuListElement.getElementsContainingText("*").hasText() || menuListElement.getElementsContainingText("5.0").hasText()){
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

        if(food.isEmpty() || (food.charAt(1)>=65 && food.charAt(1)<=122)){
            return "";
        }

        if(food.contains("-5.0") || food.contains(" - 5.0")){
            food = food.replace("-5.0","")
                    .replace(" - 5.0","");
        }

        return food;
    }
}
