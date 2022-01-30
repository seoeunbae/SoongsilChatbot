package ssu.haksik.haksik.facultyLounge.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssu.haksik.haksik.common.response.FoodResponse;
import ssu.haksik.haksik.facultyLounge.service.FacultyLoungeService;

import java.io.IOException;

@RestController
@RequestMapping("/haksik/faculty")
@RequiredArgsConstructor
public class FacultyLoungeController {

    private final FacultyLoungeService facultyLoungeService;

    @PostMapping()
    public FoodResponse getFacultyHaksik(){
        FoodResponse facultyHaksik = facultyLoungeService.getFacultyHaksik();
        return facultyHaksik;
    }

}
