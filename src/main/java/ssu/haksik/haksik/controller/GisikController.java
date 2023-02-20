package ssu.haksik.haksik.controller;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssu.haksik.haksik.common.response.FoodResponse;
import ssu.haksik.haksik.service.GisikService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@RestController
@RequestMapping("/gisik")
@RequiredArgsConstructor
public class GisikController {

    private final GisikService gisikService;

    @PostMapping("/weekMenu") // 카카오 API에서 post 방식만 지원함
    public FoodResponse getThisWeekGisik() {
        String foods = gisikService.getThisWeekGisik();
        return new FoodResponse(foods);
    }

    @PostMapping("/todayMenu") // 카카오 API에서 post 방식만 지원함
    public FoodResponse getTodayGisik() {
        String foods = gisikService.getGisikofDay(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        return new FoodResponse(foods);
    }

    @PostMapping()
    public void saveGisik() throws IOException {
        gisikService.saveGisik();
    }
}