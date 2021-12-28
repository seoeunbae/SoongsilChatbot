package ssu.haksik.haksik.dodam;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssu.haksik.haksik.common.response.FoodResponse;

import javax.print.DocFlavor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

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

}
