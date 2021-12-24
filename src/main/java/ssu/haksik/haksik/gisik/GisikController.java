package ssu.haksik.haksik.gisik;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ssu.haksik.haksik.common.crawling.*;
import ssu.haksik.haksik.common.response.FoodResponse;
import ssu.haksik.haksik.common.response.Outputs;
import ssu.haksik.haksik.common.response.SimpleText;
import ssu.haksik.haksik.common.response.Template;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/gisik")
@RequiredArgsConstructor
public class GisikController {
    private final GisikCrawling gisikCrawling;

//    public GisikController(GisikCrawling gisikCrawling) {
//        this.gisikCrawling = gisikCrawling;
//    }

    @PostMapping("/week")//일주일 식단
    public FoodResponse getThisWeekGisik() throws IOException {
        String foods = gisikCrawling.getThisWeekGisik();
        SimpleText simpleText = new SimpleText(foods);
        Outputs outputs = new Outputs(simpleText);
        Template template = new Template();
        template.getOutputs().add(outputs);

        FoodResponse foodResponse = new FoodResponse("2.0", template);
        System.out.println(foodResponse);
        return foodResponse;
    }

    @PostMapping("/today")
    public FoodResponse getTodayGisik() throws IOException{
        String foods = gisikCrawling.getTodayGisik();
        SimpleText simpleText = new SimpleText(foods);
        Outputs outputs = new Outputs(simpleText);
        Template template = new Template();
        template.getOutputs().add(outputs);
        FoodResponse foodResponse = new FoodResponse("2.0", template);
        return foodResponse;
    }
}