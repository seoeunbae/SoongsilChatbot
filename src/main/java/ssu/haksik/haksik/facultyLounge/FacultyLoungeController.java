package ssu.haksik.haksik.facultyLounge;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssu.haksik.haksik.facultyLounge.Crawling.FacultyHaksikCrawling;
import ssu.haksik.haksik.facultyLounge.JsonForOpenBuilder.Data;
import ssu.haksik.haksik.facultyLounge.JsonForOpenBuilder.FoodResponse;

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
