package model;

import java.util.ArrayList;
import java.util.List;

public class BruteNode {
    private List<Brick> bricks = new ArrayList<>();
    private List<Brick> left;
    private List<List<Brick>> blocks = new ArrayList<>();
    private int lowestHandCount;

    public int getLowestHandCount() {
        return lowestHandCount;
    }

    public void setLowestHandCount(int lowestHandCount) {
        this.lowestHandCount = lowestHandCount;
    }

    public List<List<Brick>> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<List<Brick>> blocks) {
        this.blocks = blocks;
    }

    private List<BruteNode> leafs = new ArrayList<>();
    private BruteNode parent;

    public List<Brick> getBricks() {
        return bricks;
    }

    public void setBricks(List<Brick> bricks) {
        this.bricks = bricks;
    }

    public List<Brick> getLeft() {
        return left;
    }

    public void setLeft(List<Brick> left) {
        this.left = left;
    }

    public List<BruteNode> getLeafs() {
        return leafs;
    }

    public void setLeafs(List<BruteNode> leafs) {
        this.leafs = leafs;
    }

    public BruteNode getParent() {
        return parent;
    }

    public void setParent(BruteNode parent) {
        this.parent = parent;
    }
}
