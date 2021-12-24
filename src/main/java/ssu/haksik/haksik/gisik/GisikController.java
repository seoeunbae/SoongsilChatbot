package ssu.haksik.haksik.gisik;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssu.haksik.haksik.common.response.FoodResponse;
import ssu.haksik.haksik.common.response.Outputs;
import ssu.haksik.haksik.common.response.SimpleText;
import ssu.haksik.haksik.common.response.Template;


@RestController
@RequestMapping("/gisik")
@RequiredArgsConstructor
public class GisikController {

    private final GisikService gisikService;

    private FoodResponse makeResponse(String foods) {
        Template template = new Template();
        template.getOutputs().add(new Outputs(new SimpleText(foods)));
        FoodResponse foodResponse = new FoodResponse("2.0",template);
        return foodResponse;
    }

    @PostMapping("/week")
    public FoodResponse getThisWeekGisik() {
        String foods = gisikService.getThisWeekGisik();
        return makeResponse(foods);
    }

    @PostMapping("/today")
    public FoodResponse getTodayGisik() {
        String foods = gisikService.getTodayGisik();
        return makeResponse(foods);
    }


}