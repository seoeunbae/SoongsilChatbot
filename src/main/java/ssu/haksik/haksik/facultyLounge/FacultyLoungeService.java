package ssu.haksik.haksik.facultyLounge;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ssu.haksik.haksik.common.response.FoodResponse;
import ssu.haksik.haksik.dodam.Dodam;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FacultyLoungeService {

    private final FacultyLoungeRepository facultyLoungeRepository;

    public FoodResponse getFacultyHaksik(){
        Optional<FacultyLounge> facultyLunchOptional = facultyLoungeRepository.findById(1L);
        FacultyLounge facultyLunch = facultyLunchOptional.get();
        String facultyLunchFoods = facultyLunch.getFoods();
        StringBuilder foods = new StringBuilder();
        String foodsAndDay = foods.append(makeToday()).append("\n\n").append(facultyLunchFoods).toString();

        return new FoodResponse(foodsAndDay);
    }

    public String makeToday(){
        LocalDate now = LocalDate.now();
        String yyyymmdd = now.toString();
        String day = now.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN);

        return yyyymmdd + " ("+day+")";
    }
}
