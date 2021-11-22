package ssu.haksik.haksik.facultyLounge.JsonForOpenBuilder;

import lombok.Getter;

@Getter
public class FoodResponse {
    public FoodResponse(String version, Data data){
        this.version = version;
        this.data = data;
    }

    private String version;
    private Data data;
}
