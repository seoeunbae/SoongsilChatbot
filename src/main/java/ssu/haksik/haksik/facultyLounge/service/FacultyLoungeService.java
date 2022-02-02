package ssu.haksik.haksik.facultyLounge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssu.haksik.haksik.common.response.FoodResponse;
import ssu.haksik.haksik.facultyLounge.entity.FacultyLounge;
import ssu.haksik.haksik.facultyLounge.repository.FacultyLoungeRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

import static ssu.haksik.haksik.common.crawling.HaksikCrawling.crawling;

@Service
@RequiredArgsConstructor
public class FacultyLoungeService {

    private final FacultyLoungeRepository facultyLoungeRepository;

    public FoodResponse getFacultyHaksik(){
        FacultyLounge facultyLoungeLunch = facultyLoungeRepository.findByEatingTime(1);
        String facultyLoungeLunchFoods = facultyLoungeLunch.getFoods();
        StringBuilder foods = new StringBuilder();
        String foodsAndDay = foods.append(makeToday()).append("\n\n").append(facultyLoungeLunchFoods).toString();

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
    public void saveFacultyFoodMenu() throws IOException {
        String url = "http://m.soongguri.com/m_req/m_menu.php?rcd=7&sdt=";
        String newFacultyFoodMenu = crawling(url, 0);
        FacultyLounge facultyFoodMenuByTime  = facultyLoungeRepository.findByEatingTime(1);
        if(facultyFoodMenuByTime == null){
            facultyLoungeRepository.save(new FacultyLounge(newFacultyFoodMenu, 1));
            return;
        }else{
            facultyFoodMenuByTime.setFood(newFacultyFoodMenu);
            facultyLoungeRepository.save(facultyFoodMenuByTime);
        }
    }
}
