package ssu.haksik.haksik.common.response;

import lombok.Getter;

@Getter
public class Data {
    private String foods;

    public Data(String foods){
        this.foods = foods;
    }
}
