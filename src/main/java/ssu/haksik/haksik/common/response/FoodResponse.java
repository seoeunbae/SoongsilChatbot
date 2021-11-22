package ssu.haksik.haksik.common.response;

import lombok.Getter;

@Getter
public class FoodResponse {
    public FoodResponse(String version, Template template){
        this.version = version;
        this.template = template;
    }

    private String version;
    private Template template;
}
