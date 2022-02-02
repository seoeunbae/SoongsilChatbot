package ssu.haksik.haksik.dodam.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssu.haksik.haksik.common.response.FoodResponse;
import ssu.haksik.haksik.dodam.crawling.DodamCrawling;
import ssu.haksik.haksik.dodam.entity.Dodam;
import ssu.haksik.haksik.dodam.repository.DodamRepository;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static ssu.haksik.haksik.common.EatingTime.DINNER;
import static ssu.haksik.haksik.common.EatingTime.LUNCH;

@Service
@RequiredArgsConstructor
public class DodamService {

    private final DodamRepository dodamRepository;
    private final DodamCrawling dodamCrawling;

    public FoodResponse getDodam(int eatingTime){
        Dodam dodamLunch = dodamRepository.findByEatingTime(eatingTime);
        String dodamLunchFoods = dodamLunch.getFoods();
        StringBuilder foods = new StringBuilder();
        String foodsAndDay = foods.append(makeToday()).append("\n\n").append(dodamLunchFoods).toString();

        return new FoodResponse(foodsAndDay);
    }

    public String makeToday(){
        LocalDate now = LocalDate.now();
        String yyyymmdd = now.toString();
        String day = now.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN);

        return yyyymmdd + " ("+day+")";
    }

    @Transactional
//    @Scheduled(cron = "0 0 1 * * *")
    @Scheduled(cron = "0 */2 * * * *")
    public void saveDodamFoodMenu() throws IOException {
        String url = "http://m.soongguri.com/m_req/m_menu.php?rcd=2&sdt=";

        for (Integer eatingTime : Arrays.asList(LUNCH.ordinal(), DINNER.ordinal())) {
            String newDodamFoodMenu = dodamCrawling.crawling(url, eatingTime);
            Dodam dodamFoodMenuByTime = dodamRepository.findByEatingTime(eatingTime);
            if (dodamFoodMenuByTime == null) {
                dodamRepository.save(new Dodam(newDodamFoodMenu, eatingTime));
                return;
            } else {
                dodamFoodMenuByTime.setFoods(newDodamFoodMenu);
            }
        }
    }
}

