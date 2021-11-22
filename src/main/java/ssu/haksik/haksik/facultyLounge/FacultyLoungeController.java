package ssu.haksik.haksik.facultyLounge;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssu.haksik.haksik.common.crawling.FacultyHaksikCrawling;
import ssu.haksik.haksik.common.response.Data;
import ssu.haksik.haksik.common.response.FoodResponse;

import java.io.IOException;

@RestController
@RequestMapping("/faculty")
public class FacultyLoungeController {

    @PostMapping("/haksik")
    public FoodResponse facultyHaksik() throws IOException {
        String foods = FacultyHaksikCrawling.crawling();
        Data data = new Data(foods);
        FoodResponse foodResponse = new FoodResponse("2.0", data);
        return foodResponse;
    }
}
