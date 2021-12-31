package ssu.haksik.haksik.common.response;

import lombok.Getter;

@Getter
public class FoodResponse {
    private String version;
    private Template template;

    public FoodResponse(String foods) {
        Template template = new Template();
        template.getOutputs().add(new Outputs(new SimpleText(foods)));

        this.version = "2.0";
        this.template = template;
    }
}
