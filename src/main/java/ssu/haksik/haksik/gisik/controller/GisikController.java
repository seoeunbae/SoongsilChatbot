package ssu.haksik.haksik.gisik.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssu.haksik.haksik.common.response.FoodResponse;
import ssu.haksik.haksik.gisik.service.GisikService;


@RestController
@RequestMapping("/gisik")
@RequiredArgsConstructor
public class GisikController {

    private final GisikService gisikService;

    @PostMapping("/week")
    public FoodResponse getThisWeekGisik() {
        String foods = gisikService.getThisWeekGisik();
        return new FoodResponse(foods);
    }

    @PostMapping("/today")
    public FoodResponse getTodayGisik() {
        String foods = gisikService.getTodayGisik();
        return new FoodResponse(foods);
    }

}