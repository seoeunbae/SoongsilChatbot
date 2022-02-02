package ssu.haksik.haksik.facultyLounge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssu.haksik.haksik.common.response.FoodResponse;
import ssu.haksik.haksik.facultyLounge.crawling.FacultyLoungeCrawling;
import ssu.haksik.haksik.facultyLounge.entity.FacultyLounge;
import ssu.haksik.haksik.facultyLounge.repository.FacultyLoungeRepository;
import java.io.IOException;
import java.time.LocalDate;
import static java.time.format.TextStyle.*;
import static java.util.Locale.*;
import static ssu.haksik.haksik.common.EatingTime.*;

@Service
@RequiredArgsConstructor
public class FacultyLoungeService {

    private final FacultyLoungeRepository facultyLoungeRepository;
    private final FacultyLoungeCrawling facultyLoungeCrawling;

    public FoodResponse getFacultyHaksik(){
        FacultyLounge facultyLoungeLunch = facultyLoungeRepository.findByEatingTime(LUNCH);
        String facultyLoungeLunchFoods = facultyLoungeLunch.getFoods();
        StringBuilder foods = new StringBuilder();
        String foodsAndDay = foods.append(makeToday()).append("\n\n").append(facultyLoungeLunchFoods).toString();

        return new FoodResponse(foodsAndDay);
    }

    public String makeToday(){
        LocalDate now = LocalDate.now();
        String yyyymmdd = now.toString();
        String day = now.getDayOfWeek().getDisplayName(SHORT, KOREAN);

        return yyyymmdd + " ("+day+")";
    }

    @Transactional
    @Scheduled(cron = "0 0 1 * * *")
    public void saveFacultyFoodMenu() throws IOException {
        String url = "http://m.soongguri.com/m_req/m_menu.php?rcd=7&sdt=";
        String newFacultyFoodMenu = facultyLoungeCrawling.crawling(url);
        FacultyLounge facultyFoodMenuByTime  = facultyLoungeRepository.findByEatingTime(LUNCH);
        if(facultyFoodMenuByTime == null){
            facultyLoungeRepository.save(new FacultyLounge(newFacultyFoodMenu, LUNCH));
            return;
        }else{
            facultyFoodMenuByTime.setFood(newFacultyFoodMenu);
        }
    }
}
