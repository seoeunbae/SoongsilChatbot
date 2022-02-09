package ssu.haksik.haksik.gisik.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssu.haksik.haksik.common.response.FoodResponse;
import ssu.haksik.haksik.gisik.service.GisikService;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/gisik")
@RequiredArgsConstructor
public class GisikController {

    private final GisikService gisikService;

    @PostMapping("/week") // 카카오 API에서 post 방식만 지원함
    public FoodResponse getThisWeekGisik() {
        String foods = gisikService.getThisWeekGisik();
        return new FoodResponse(foods);
    }

    @PostMapping("/today") // 카카오 API에서 post 방식만 지원함
    public FoodResponse getTodayGisik() {
        String foods = gisikService.getGisikofDay(LocalDateTime.now().getDayOfWeek());
        return new FoodResponse(foods);
    }
}