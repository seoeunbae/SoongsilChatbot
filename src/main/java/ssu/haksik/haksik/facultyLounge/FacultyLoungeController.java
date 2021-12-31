package ssu.haksik.haksik.facultyLounge;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssu.haksik.haksik.common.crawling.EatingTime;
import ssu.haksik.haksik.common.crawling.HaksikCrawling;
import ssu.haksik.haksik.common.response.FoodResponse;
import ssu.haksik.haksik.common.response.Outputs;
import ssu.haksik.haksik.common.response.SimpleText;
import ssu.haksik.haksik.common.response.Template;

import java.io.IOException;

@RestController
@RequestMapping("/haksik/faculty")
@RequiredArgsConstructor
public class FacultyLoungeController {

    private final FacultyLoungeService facultyLoungeService;

    @PostMapping()
    public FoodResponse getFacultyHaksik() throws IOException {
        FoodResponse facultyHaksik = facultyLoungeService.getFacultyHaksik();
        return facultyHaksik;
    }

}
