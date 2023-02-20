package ssu.haksik.haksik.common.crawling;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import ssu.haksik.haksik.common.enums.EatingTime;
import ssu.haksik.haksik.common.exception.DodamDinnerNotExistException;
import java.io.IOException;
import java.time.LocalDateTime;

import static ssu.haksik.haksik.common.enums.EatingTime.*;

@Component
public class DodamCrawling {

    public String makeTodayFood(String url, EatingTime eatingTime) throws IOException {

        if(isWeekend()){
            return "주말은 운영하지 않습니다.";
        }

        Elements lunchAndDinnerMenuListElements = crawling(url, eatingTime);
        Elements menuElements = lunchAndDinnerMenuListElements.get(eatingTime.ordinal()).getElementsByTag("div"); // 점심 식단과 저녁식단을 구분한 후 div를 기준으로 elements를 생성
        List<Element> menuList = new ArrayList();

        for(int i = 0; i < menuElements.size(); i++) {
            menuList.addAll(makeMenuList(menuElements.get(i)));
        }

        int menuListSize = menuList.size();

        String foods = makeFoodList(menuListSize, menuList);
        return foods;
    }

    private static List<Element> makeMenuList(Element menuElement) {
        List<Element> menuList = new ArrayList();

        if (menuElement.getElementsByTag("div").size() == 1 || menuElement.getElementsByTag("div").size() == 0) {
            menuList.add(menuElement);
            return menuList;
        }

        Elements elements = menuElement.getElementsByTag("div");
        for(int i=1; i< elements.size(); i++) {
            Element div = elements.get(i);
            menuList.addAll(makeMenuList(div));
        }

        return menuList;
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

    private String makeFoodList(int menuListSize, List<Element> menuList){

        boolean isAboutFood = false; // 음식과 관련된 element만 stringbuilder에 추가하기 위해 사용
        StringBuilder foodList = new StringBuilder();

        while(!menuList.isEmpty()) {
            Element menu = menuList.remove(0);

            if(menu.getElementsContainingText("5.0").hasText()){
                isAboutFood = true;
            }

            if(isAboutFood){
                if (menu.getElementsContainingText("알러지").hasText() || menu.getElementsContainingText("원산지").hasText() ) {
                    break;
                }

                String food = extractFoodFromElements(menu);
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

        if(food.contains("5.0")){
            food = food.replace("-5.0","")
                    .replace("- 5.0","");
        }

        return food;
    }
}
