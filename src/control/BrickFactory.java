package control;

import model.Brick;
import model.BrickColor;

public class BrickFactory {

    public static class PreBrick {
        Brick brick;

        public PreBrick setJoker() {
            brick.setJoker(true);
            return this;
        }

        public PreBrick setJoker(boolean value) {
            brick.setJoker(value);
            return this;
        }

        public Brick get() {
            return brick;
        }
    }

    public static PreBrick generateBrick(String value, BrickColor color) {
        try {
            Brick brick = new Brick();
            int val = Integer.valueOf(value);
            if (val > 13) {
                val = val % 13;
            }
            if (val <= 0) {
                val = 13 + val;
            }
            brick.setValue(val);
            brick.setColor(color);
            PreBrick pre = new PreBrick();
            pre.brick = brick;
            return pre;
        } catch (NumberFormatException e) {

        }
        return null;
    }
}
