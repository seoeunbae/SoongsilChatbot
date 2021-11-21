package ssu.haksik.haksik.facultyLounge;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/faculty")
public class FacultyLoungeController {

    @PostMapping("/haksik")
    public String facultyHaksik() throws IOException {
        FacultyHaksikCrawling facultyHaksikCrawling = new FacultyHaksikCrawling();
        String foods = facultyHaksikCrawling.crawling();
        return foods;
    }
}
