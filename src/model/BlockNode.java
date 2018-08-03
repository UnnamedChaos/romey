package model;

import java.util.ArrayList;
import java.util.List;

public class BlockNode {
    private List<Brick> block = new ArrayList<>();
    private BlockNode parent;

    public BlockNode getParent() {
        return parent;
    }

    public void setParent(BlockNode parent) {
        this.parent = parent;
    }

    private List<BlockNode> leafs = new ArrayList<>();

    public List<Brick> getBlock() {
        return block;
    }

    public void setBlock(List<Brick> block) {
        this.block = block;
    }

    public List<BlockNode> getLeafs() {
        return leafs;
    }

    public void setLeafs(List<BlockNode> leafs) {
        this.leafs = leafs;
    }

    @Override
    public String toString() {
        return "Node: " + block + "\n" + leafs.size() + "\n ++++++++++++++++++\n";
    }
}
