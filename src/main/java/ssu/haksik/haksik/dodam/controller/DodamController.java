package ssu.haksik.haksik.dodam.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ssu.haksik.haksik.common.EatingTime;
import ssu.haksik.haksik.common.response.*;
import ssu.haksik.haksik.dodam.service.DodamService;

@RestController
@RequestMapping("/haksik/dodam")
@RequiredArgsConstructor
public class DodamController {

    private final DodamService dodamService;

    @PostMapping()
    public FoodResponse getDodam(@RequestParam EatingTime eatingTime){
        FoodResponse DodamLunchFoods = dodamService.getDodam(eatingTime);
        return DodamLunchFoods;
    }
}
