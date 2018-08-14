package model;

import java.util.ArrayList;
import java.util.List;

public class Matches {
    List<List<Brick>> matches = new ArrayList<>();
    List<Brick> handLeft = new ArrayList<>();


    public List<List<Brick>> getMatches() {
        return matches;
    }

    public List<Brick> getHandLeft() {
        return handLeft;
    }

    public void setHandLeft(List<Brick> handLeft) {
        this.handLeft = handLeft;
    }

    public void setMatches(List<List<Brick>> matches) {
        this.matches = matches;
    }

    public int getMatchSize() {
        int matchsize = 0;
        for (List<Brick> bricks : matches) {
            matchsize += bricks.size();
        }
        return matchsize;
    }

}
