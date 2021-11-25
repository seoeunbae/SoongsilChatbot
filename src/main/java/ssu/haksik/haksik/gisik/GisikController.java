package ssu.haksik.haksik.gisik;

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

@RestController
@RequestMapping("/gisik")
public class GisikController {
    @PostMapping()
    public FoodResponse gisik(@RequestParam("time") EatingTime eatingTime) throws IOException {
        String foods = GisikCrawling.gisikCrawling(eatingTime);
        SimpleText simpleText = new SimpleText(foods);
        Outputs outputs = new Outputs(simpleText);
        Template template = new Template();
        template.getOutputs().add(outputs);

        FoodResponse foodResponse = new FoodResponse("2.0", template);
        return foodResponse;
    }
}
