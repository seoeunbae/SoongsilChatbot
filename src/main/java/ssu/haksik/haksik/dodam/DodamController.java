package ssu.haksik.haksik.dodam;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ssu.haksik.haksik.common.crawling.EatingTime;
import ssu.haksik.haksik.common.crawling.HaksikCrawling;
import ssu.haksik.haksik.common.response.*;

import java.io.IOException;

@RestController
@RequestMapping("/haksik/dodam")
@RequiredArgsConstructor
public class DodamController {

    private final DodamService dodamService;

    @PostMapping()
    public FoodResponse getDodam(@RequestParam int eatingTime){
        FoodResponse DodamLunchFoods = dodamService.getDodam(eatingTime);
        return DodamLunchFoods;
    }
}
