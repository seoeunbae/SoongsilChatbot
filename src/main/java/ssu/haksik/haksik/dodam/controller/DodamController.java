package ssu.haksik.haksik.dodam.controller;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ssu.haksik.haksik.common.enums.EatingTime;
import ssu.haksik.haksik.common.response.*;
import ssu.haksik.haksik.dodam.service.DodamService;

@RestController
@RequestMapping("/haksik/dodam")
@RequiredArgsConstructor
public class DodamController {

    private final DodamService dodamService;

    @PostMapping("/eatingTime/{eatingTime}") // 카카오 API에서 post 방식만 지원함
    public FoodResponse getDodamFood(@PathVariable(value = "eatingTime") EatingTime eatingTime){
        return dodamService.getDodamFood(eatingTime);
    }

    @PostMapping() // 카카오 API에서 post 방식만 지원함
    public void saveDodamFood() throws IOException {
        dodamService.saveDodamFoodMenu();
    }
}
