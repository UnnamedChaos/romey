package model;

import java.util.ArrayList;
import java.util.List;

public class Matches {
    List<List<Brick>> matches = new ArrayList<>();

    public List<List<Brick>> getMatches() {
        return matches;
    }

    public void setMatches(List<List<Brick>> matches) {
        this.matches = matches;
    }
}
