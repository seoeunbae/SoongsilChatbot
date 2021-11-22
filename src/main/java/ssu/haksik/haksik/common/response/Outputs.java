package ssu.haksik.haksik.common.response;

import lombok.Getter;

@Getter
public class Outputs {

    private SimpleText simpleText;

    public Outputs(SimpleText simpleText){
        this.simpleText = simpleText;
    }
}
