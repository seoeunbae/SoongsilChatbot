package ssu.haksik.haksik.dodam.controller;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ssu.haksik.haksik.common.enums.EatingTime;
import ssu.haksik.haksik.common.response.*;
import ssu.haksik.haksik.dodam.service.DodamService;

@RestController
@RequestMapping("/haksik/dodam")
@RequiredArgsConstructor
public class DodamController {

    private final DodamService dodamService;

    @PostMapping() // 카카오 API에서 post 방식만 지원함
    public FoodResponse getDodamFood(@RequestParam EatingTime eatingTime){
        return dodamService.getDodamFood(eatingTime);
    }

    @PostMapping("/save") // 카카오 API에서 post 방식만 지원함
    public void saveDodamFood() throws IOException {
        dodamService.saveDodamFoodMenu();
    }
}
