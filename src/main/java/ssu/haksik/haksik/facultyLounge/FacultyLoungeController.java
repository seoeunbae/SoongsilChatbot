package ssu.haksik.haksik.facultyLounge;

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
public class FacultyLoungeController {

    @PostMapping()
    public FoodResponse facultyHaksik() throws IOException {

        String url = "http://m.soongguri.com/m_req/m_menu.php?rcd=7&sdt=";

        String menuBoard = HaksikCrawling.crawling(url, EatingTime.lunch);

        SimpleText simpleText = new SimpleText(menuBoard);
        Outputs outputs = new Outputs(simpleText);
        Template template = new Template();
        template.getOutputs().add(outputs);

        FoodResponse foodResponse = new FoodResponse("2.0", template);
        return foodResponse;
    }

    @PostMapping("/test")
    public FoodResponse facultyTest() throws IOException{
        String url = "http://m.soongguri.com/m_req/m_menu.php?rcd=7&sdt=";

        String menuBoard = HaksikCrawling.crawling(url, EatingTime.lunch);

        SimpleText simpleText = new SimpleText(menuBoard);
        Outputs outputs = new Outputs(simpleText);
        Template template = new Template();
        template.getOutputs().add(outputs);

        FoodResponse foodResponse = new FoodResponse("2.0", template);
        return foodResponse;
    }
}
