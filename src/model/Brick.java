package model;

import java.util.Objects;

public class Brick {

    private int value;
    private BrickColor color;
    private boolean isJoker = false;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public BrickColor getColor() {
        return color;
    }

    public void setColor(BrickColor color) {
        this.color = color;
    }

    public boolean isJoker() {
        return isJoker;
    }

    public void setJoker(boolean joker) {
        isJoker = joker;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Brick brick = (Brick) o;
        return (value == brick.value &&
                color == brick.color) || (isJoker && isJoker == brick.isJoker);
    }

    @Override
    public int hashCode() {

        return Objects.hash(value, color);
    }

    @Override
    public String toString() {
        return "Brick{" +
                "value=" + value +
                ", color=" + color +
                '}';
    }
}
