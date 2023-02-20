package ssu.haksik.haksik.facultyLounge.controller;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssu.haksik.haksik.common.response.FoodResponse;
import ssu.haksik.haksik.facultyLounge.service.FacultyLoungeService;

@RestController
@RequestMapping("/haksik")
@RequiredArgsConstructor
public class FacultyLoungeController {

    private final FacultyLoungeService facultyLoungeService;

    @PostMapping("/facultylounge")// 카카오 API에서 post 방식만 지원함
    public FoodResponse getFacultyLoungeFood(){
        return facultyLoungeService.getFacultyLoungeFood();
    }

    @PostMapping()// 카카오 API에서 post 방식만 지원함
    public void saveFacultyLoungeFood() throws IOException {
        facultyLoungeService.saveFacultyFoodMenu();
    }
}
