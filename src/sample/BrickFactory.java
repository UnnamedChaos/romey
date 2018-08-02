package sample;

import model.Brick;
import model.BrickColor;

public class BrickFactory {

    public static class PreBrick{
        public Brick brick;

        public Brick setJoker(){
            brick.setJoker(true);
            return brick;
        }
        public Brick setJoker(boolean value){
            brick.setJoker(value);
            return brick;
        }

        public Brick get(){
            return brick;
        }
    }

    public static PreBrick generateBrick(String value, BrickColor color){
        try{
            Brick brick = new Brick();
            brick.setValue(Integer.valueOf(value));
            brick.setColor(color);
            PreBrick pre = new PreBrick();
            pre.brick = brick;
            return pre;
        }catch (NumberFormatException e){

        }
        return null;
    }
}
