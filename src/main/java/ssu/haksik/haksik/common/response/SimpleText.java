package ssu.haksik.haksik.common.response;

import lombok.Getter;

@Getter
public class SimpleText {

    public SimpleText(String text) {
        this.text = text;
    }
    String text;
}
