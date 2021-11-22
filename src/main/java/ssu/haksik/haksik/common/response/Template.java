package ssu.haksik.haksik.common.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
public class Template {

    private ArrayList<Outputs> outputs = new ArrayList<Outputs>();

    public Template() {
    }
}
