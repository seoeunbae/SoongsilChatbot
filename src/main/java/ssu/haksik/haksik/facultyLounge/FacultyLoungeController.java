package ssu.haksik.haksik.facultyLounge;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssu.haksik.haksik.common.crawling.FacultyHaksikCrawling;
import ssu.haksik.haksik.common.response.Data;
import ssu.haksik.haksik.common.response.FoodResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/haksik/faculty")
public class FacultyLoungeController {

    @PostMapping()
    public FoodResponse facultyHaksik() throws IOException {

        String formatDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")); // 20190513으로 출력 System.out.println(formatDate);
        String url = "http://m.soongguri.com/m_req/m_menu.php?rcd=7&sdt="+formatDate;
        String foods = FacultyHaksikCrawling.crawling(url);
        Data data = new Data(foods);
        FoodResponse foodResponse = new FoodResponse("2.0", data);
        return foodResponse;
    }
}
