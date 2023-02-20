package ssu.haksik.haksik.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssu.haksik.haksik.common.enums.EatingTime;
import ssu.haksik.haksik.common.response.FoodResponse;
import ssu.haksik.haksik.common.crawling.DodamCrawling;
import ssu.haksik.haksik.entity.Dodam;
import ssu.haksik.haksik.repository.DodamRepository;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;

import static java.time.format.TextStyle.*;
import static java.util.Locale.*;
import static ssu.haksik.haksik.common.enums.EatingTime.DINNER;
import static ssu.haksik.haksik.common.enums.EatingTime.LUNCH;

@Service
@RequiredArgsConstructor
public class DodamService {

    private final DodamRepository dodamRepository;
    private final DodamCrawling dodamCrawling;

    @Transactional(readOnly = true)
    public FoodResponse getDodamFood(EatingTime eatingTime){
        String dodamFoods = dodamRepository.findByEatingTime(eatingTime).getFoods();

        return new FoodResponse(makeTodayDateFormat()+"\n\n"+dodamFoods);
    }

    private String makeTodayDateFormat(){
        LocalDate now = LocalDate.now();
        String yyyymmdd = now.toString();
        String day = now.getDayOfWeek().getDisplayName(SHORT, KOREAN);

        return yyyymmdd + " ("+day+")";
    }

    @Transactional
    @Scheduled(cron = "0 0 1 * * 1")
    public void saveDodamFoodMenu() throws IOException {
        String url = "http://m.soongguri.com/m_req/m_menu.php?rcd=2&sdt=";

        for (EatingTime eatingTime : Arrays.asList(LUNCH, DINNER)) {
            String newDodamFoodMenu = dodamCrawling.makeTodayFood(url, eatingTime);
            Dodam existDodamFood = dodamRepository.findByEatingTime(eatingTime);
            if (existDodamFood == null) {
                dodamRepository.save(new Dodam(newDodamFoodMenu, eatingTime));
                return;
            } else {
                existDodamFood.changeFoods(newDodamFoodMenu);
            }
        }
    }
}

