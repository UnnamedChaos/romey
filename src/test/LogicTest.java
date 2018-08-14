package test;

import control.BrickFactory;
import control.Logic;
import model.Brick;
import model.BrickColor;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LogicTest {
    public LogicTest() { }

    @Test
    void testIsValidRainbow() {
        List<Brick> list = new ArrayList<Brick>();
        list.add(BrickFactory.generateBrick("1", BrickColor.BLACK).get());
        list.add(BrickFactory.generateBrick("1", BrickColor.RED).get());
        list.add(BrickFactory.generateBrick("1", BrickColor.YELLOW).get());
        Assertions.assertTrue(Logic.isValid(list));
        list.add(BrickFactory.generateBrick("1", BrickColor.GREEN).get());
        Assertions.assertTrue(Logic.isValid(list));
    }

    @Test
    void testIsValidRainbowWithJoker() {
        List<Brick> list = new ArrayList<Brick>();
        list.add(BrickFactory.generateBrick("8", BrickColor.RED).setJoker().get());
        Assertions.assertFalse(Logic.isValid(list));
        list.add(BrickFactory.generateBrick("1", BrickColor.RED).get());
        list.add(BrickFactory.generateBrick("1", BrickColor.YELLOW).get());
        Assertions.assertTrue(Logic.isValid(list));
        list.add(BrickFactory.generateBrick("1", BrickColor.GREEN).get());
        Assertions.assertTrue(Logic.isValid(list));
    }

    @Test
    void testDoubleColorRainbow() {
        List<Brick> list = new ArrayList<Brick>();
        list.add(BrickFactory.generateBrick("1", BrickColor.BLACK).get());
        list.add(BrickFactory.generateBrick("1", BrickColor.RED).get());
        list.add(BrickFactory.generateBrick("1", BrickColor.RED).get());
        Assertions.assertFalse(Logic.isValid(list));
    }

    @Test
    void testMaxRainbow() {
        List<Brick> list = new ArrayList<Brick>();
        list.add(BrickFactory.generateBrick("1", BrickColor.BLACK).get());
        list.add(BrickFactory.generateBrick("1", BrickColor.RED).get());
        list.add(BrickFactory.generateBrick("1", BrickColor.YELLOW).get());
        list.add(BrickFactory.generateBrick("1", BrickColor.GREEN).get());
        list.add(BrickFactory.generateBrick("1", BrickColor.RED).get());
        Assertions.assertFalse(Logic.isValid(list));
    }

    @Test
    void testEnd() {
        List<Brick> list = new ArrayList<Brick>();
        list.add(BrickFactory.generateBrick("11", BrickColor.BLACK).get());
        list.add(BrickFactory.generateBrick("12", BrickColor.BLACK).get());
        list.add(BrickFactory.generateBrick("13", BrickColor.BLACK).get());
        Assertions.assertTrue(Logic.isValid(list));
    }

    @Test
    void testValueRainbow() {
        List<Brick> list = new ArrayList<Brick>();
        list.add(BrickFactory.generateBrick("1", BrickColor.BLACK).get());
        list.add(BrickFactory.generateBrick("2", BrickColor.RED).get());
        list.add(BrickFactory.generateBrick("1", BrickColor.YELLOW).get());
        Assertions.assertFalse(Logic.isValid(list));
    }

    @Test
    void testIsValid() {
        List<Brick> list = new ArrayList<Brick>();
        list.add(BrickFactory.generateBrick("1", BrickColor.BLACK).get());
        list.add(BrickFactory.generateBrick("2", BrickColor.BLACK).get());
        Assertions.assertFalse(Logic.isValid(list));
        list.add(BrickFactory.generateBrick("3", BrickColor.BLACK).get());
        Assertions.assertTrue(Logic.isValid(list));
        list.add(BrickFactory.generateBrick("4", BrickColor.BLACK).get());
        list.add(BrickFactory.generateBrick("5", BrickColor.RED).get());
        Assertions.assertFalse(Logic.isValid(list));
        list.remove(BrickFactory.generateBrick("5", BrickColor.RED).get());
        Assertions.assertTrue(Logic.isValid(list));
        list.add(BrickFactory.generateBrick("6", BrickColor.BLACK).get());
        Assertions.assertFalse(Logic.isValid(list));
        list.add(4, BrickFactory.generateBrick("5", BrickColor.BLACK).get());
        list.add(0, BrickFactory.generateBrick("13", BrickColor.BLACK).get());
        Assertions.assertTrue(Logic.isValid(list));
    }

    @Test
    void testAscendingJoker() {
        List<Brick> list = new ArrayList<Brick>();
        list.add(BrickFactory.generateBrick("8", BrickColor.GREEN).setJoker().get());
        //Joker alone
        Assertions.assertFalse(Logic.isValid(list));
        list.add(BrickFactory.generateBrick("2", BrickColor.BLACK).get());
        //Just 2
        Assertions.assertFalse(Logic.isValid(list));
        list.add(BrickFactory.generateBrick("3", BrickColor.BLACK).get());
        //joker in front
        Assertions.assertTrue(Logic.isValid(list));
        list.add(BrickFactory.generateBrick("5", BrickColor.BLACK).get());
        //joker is only once available
        Assertions.assertFalse(Logic.isValid(list));
        list.remove(0);
        //no joker
        Assertions.assertFalse(Logic.isValid(list));
        list.remove(list.size() - 1);
        list.add(BrickFactory.generateBrick("8", BrickColor.GREEN).setJoker().get());
        list.add(BrickFactory.generateBrick("5", BrickColor.BLACK).get());
        //Joker between
        Assertions.assertTrue(Logic.isValid(list));

        //Wrong order
        list = new ArrayList<Brick>();
        list.add(BrickFactory.generateBrick("4", BrickColor.BLACK).get());
        list.add(BrickFactory.generateBrick("7", BrickColor.RED).setJoker().get());
        list.add(BrickFactory.generateBrick("2", BrickColor.BLACK).get());
        Assertions.assertFalse(Logic.isValid(list));
    }
}