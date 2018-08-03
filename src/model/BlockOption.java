package model;

import java.util.ArrayList;
import java.util.List;

class BlockOption {
    private Brick brick;
    private List<List<Brick>> option = new ArrayList<>();

    public Brick getBrick() {
        return brick;
    }

    public void setBrick(Brick brick) {
        this.brick = brick;
    }

    public List<List<Brick>> getOption() {
        return option;
    }

    public void setOption(List<List<Brick>> option) {
        this.option = option;
    }
}
