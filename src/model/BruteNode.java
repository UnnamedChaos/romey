package model;

import java.util.ArrayList;
import java.util.List;

public class BruteNode {
    private List<Brick> bricks = new ArrayList<>();
    private List<Brick> left;
    private List<List<Brick>> blocks = new ArrayList<>();
    private List<BruteNode> leafs = new ArrayList<>();
    private BruteNode parent;
    private Move bestMove;
    private Brick played;

    public Brick getPlayed() {
        return played;
    }

    public void setPlayed(Brick played) {
        this.played = played;
    }

    public Move getBestMove() {
        return bestMove;
    }

    public void setBestMove(Move bestMove) {
        this.bestMove = bestMove;
    }

    public List<List<Brick>> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<List<Brick>> blocks) {
        this.blocks = blocks;
    }


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

    @Override
    public String toString() {
        String s = this.hashCode() + "\n";
        for (List<Brick> block : blocks) {
            s += "\t" + block.toString() + " \n";
        }
        return s;
    }
}
