package ssu.haksik.haksik.dodam;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssu.haksik.haksik.common.response.FoodResponse;

import javax.print.DocFlavor;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

import static ssu.haksik.haksik.common.crawling.HaksikCrawling.crawling;

@Service
@RequiredArgsConstructor
public class DodamService {

    private final DodamRepository dodamRepository;

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
//    @Scheduled(cron = "*/50 * * * * *")
    @Scheduled(cron = "0 */2 * * * *")
    public void saveDodamFoodMenu() throws IOException {
        String url = "http://m.soongguri.com/m_req/m_menu.php?rcd=2&sdt=";
        for (int eatingTime=0; eatingTime<2; eatingTime++) {
            String newDodamFoodMenu = crawling(url, eatingTime);
            Dodam dodamFoodMenuByTime = dodamRepository.findByEatingTime(eatingTime);
            if(dodamFoodMenuByTime == null){
                dodamRepository.save(new Dodam(newDodamFoodMenu, eatingTime));
                return;
            }else{
                dodamFoodMenuByTime.setFoods(newDodamFoodMenu);
                dodamRepository.save(dodamFoodMenuByTime);
            }
        }
    }

}
