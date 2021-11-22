package ssu.haksik.haksik.common.response;

import lombok.Getter;
import ssu.haksik.haksik.common.response.Data;

@Getter
public class FoodResponse {
    public FoodResponse(String version, Data data){
        this.version = version;
        this.data = data;
    }

    private String version;
    private Data data;
}
