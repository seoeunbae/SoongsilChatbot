package ssu.haksik.haksik.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssu.haksik.haksik.common.response.FoodResponse;
import ssu.haksik.haksik.common.crawling.FacultyLoungeCrawling;
import ssu.haksik.haksik.entity.FacultyLounge;
import ssu.haksik.haksik.repository.FacultyLoungeRepository;
import java.io.IOException;
import java.time.LocalDate;

import static java.time.format.TextStyle.*;
import static java.util.Locale.*;
import static ssu.haksik.haksik.common.enums.EatingTime.*;

@Service
@RequiredArgsConstructor
public class FacultyLoungeService {

    private final FacultyLoungeRepository facultyLoungeRepository;
    private final FacultyLoungeCrawling facultyLoungeCrawling;

    @Transactional(readOnly = true)
    public FoodResponse getFacultyLoungeFood(){
        String facultyLunchFoods = facultyLoungeRepository.findByEatingTime(LUNCH).getFoods();

        return new FoodResponse(makeTodayDateFormat()+"\n\n"+facultyLunchFoods);
    }

    private String makeTodayDateFormat(){
        LocalDate now = LocalDate.now();
        String yyyymmdd = now.toString();
        String day = now.getDayOfWeek().getDisplayName(SHORT, KOREAN);

        return yyyymmdd + " ("+day+")";
    }

    @Transactional
    @Scheduled(cron = "0 0 1 * * 1")
    public void saveFacultyFoodMenu() throws IOException {
        String url = "http://m.soongguri.com/m_req/m_menu.php?rcd=7&sdt=";
        String newFacultyFoodMenu = facultyLoungeCrawling.makeTodayFood(url);
        FacultyLounge existFacultyLoungeFood  = facultyLoungeRepository.findByEatingTime(LUNCH);
        if(existFacultyLoungeFood == null){
            facultyLoungeRepository.save(new FacultyLounge(newFacultyFoodMenu, LUNCH));
            return;
        }else{
            existFacultyLoungeFood.changeFood(newFacultyFoodMenu);
        }
    }
}
