package sample;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import model.Brick;
import model.BrickColor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;

class Logic {
    public static boolean isValid(List<Brick> list) {
        return isMin(list) && (isColorCorrect(list) && isAscending(list) || isMax(list) && isRainbow(list));
    }

    private static boolean isRainbow(List<Brick> list) {
        if (list.size() > 0) {
            int rainBowValue = - 1;
            HashSet<BrickColor> colors = new HashSet<>();
            for (Brick b : list) {
                if (rainBowValue == - 1) {
                    rainBowValue = b.getValue();
                }
                if (b.getValue() != rainBowValue) {
                    return false;
                }
                if (! colors.add(b.getColor())) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private static boolean isMax(List<Brick> list) {
        return list.size() <= 4;
    }

    private static boolean isColorCorrect(List<Brick> list) {
        BrickColor color = null;
        for (Brick b : list) {
            if (color == null) {
                color = b.getColor();
            } else {
                if (color != b.getColor()) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isMin(List<Brick> list) {
        return list.size() >= 3;
    }

    private static boolean isAscending(List<Brick> list) {
        for (int i = 0; i < list.size(); i++) {
            if (i + 1 < list.size()) {
                if (list.get(i).getValue() == 13) {
                    if ((list.get(i).getValue() + 1) % 13 != list.get(i + 1).getValue()) {
                        return false;
                    }
                } else {
                    if ((list.get(i).getValue() + 1) != list.get(i + 1).getValue()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Test
    void testIsValidRainbow() {
        List<Brick> list = new ArrayList<>();
        list.add(BrickFactory.generateBrick("1", BrickColor.BLACK).get());
        list.add(BrickFactory.generateBrick("1", BrickColor.RED).get());
        list.add(BrickFactory.generateBrick("1", BrickColor.YELLOW).get());
        assertTrue(isValid(list));
        list.add(BrickFactory.generateBrick("1", BrickColor.GREEN).get());
        assertTrue(isValid(list));
    }

    @Test
    void testDoubleColorRainbow() {
        List<Brick> list = new ArrayList<>();
        list.add(BrickFactory.generateBrick("1", BrickColor.BLACK).get());
        list.add(BrickFactory.generateBrick("1", BrickColor.RED).get());
        list.add(BrickFactory.generateBrick("1", BrickColor.RED).get());
        assertFalse(isValid(list));
    }

    @Test
    void testMaxRainbow() {
        List<Brick> list = new ArrayList<>();
        list.add(BrickFactory.generateBrick("1", BrickColor.BLACK).get());
        list.add(BrickFactory.generateBrick("1", BrickColor.RED).get());
        list.add(BrickFactory.generateBrick("1", BrickColor.YELLOW).get());
        list.add(BrickFactory.generateBrick("1", BrickColor.GREEN).get());
        list.add(BrickFactory.generateBrick("1", BrickColor.RED).get());
        assertFalse(isValid(list));
    }

    @Test
    void testEnd() {
        List<Brick> list = new ArrayList<>();
        list.add(BrickFactory.generateBrick("11", BrickColor.BLACK).get());
        list.add(BrickFactory.generateBrick("12", BrickColor.BLACK).get());
        list.add(BrickFactory.generateBrick("13", BrickColor.BLACK).get());
        assertTrue(isValid(list));
    }

    @Test
    void testValueRainbow() {
        List<Brick> list = new ArrayList<>();
        list.add(BrickFactory.generateBrick("1", BrickColor.BLACK).get());
        list.add(BrickFactory.generateBrick("2", BrickColor.RED).get());
        list.add(BrickFactory.generateBrick("1", BrickColor.YELLOW).get());
        assertFalse(isValid(list));
    }

    @Test
    void testIsValid() {
        List<Brick> list = new ArrayList<>();
        list.add(BrickFactory.generateBrick("1", BrickColor.BLACK).get());
        list.add(BrickFactory.generateBrick("2", BrickColor.BLACK).get());
        assertFalse(isValid(list));
        list.add(BrickFactory.generateBrick("3", BrickColor.BLACK).get());
        assertTrue(isValid(list));
        list.add(BrickFactory.generateBrick("4", BrickColor.BLACK).get());
        list.add(BrickFactory.generateBrick("5", BrickColor.RED).get());
        assertFalse(isValid(list));
        list.remove(BrickFactory.generateBrick("5", BrickColor.RED).get());
        assertTrue(isValid(list));
        list.add(BrickFactory.generateBrick("6", BrickColor.BLACK).get());
        assertFalse(isValid(list));
        list.add(4, BrickFactory.generateBrick("5", BrickColor.BLACK).get());
        list.add(0, BrickFactory.generateBrick("13", BrickColor.BLACK).get());
        assertTrue(isValid(list));
    }
}
