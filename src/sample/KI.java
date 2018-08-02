package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import model.BlockNode;
import model.BlockOption;
import model.Brick;
import model.BruteNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class KI {


    public static ListView<Brick> process2(List<Brick> hand, List<List<Brick>> table) {
        if (isBoardValid(table)) {
            BruteNode start = new BruteNode();
            start.setLeft(getAll(hand, table));
            start = recBrute(start);
            List<BruteNode> tableGone = new ArrayList<>();
            List<BruteNode> handBest = new ArrayList<>();
            recTableBrute(table, hand, tableGone, handBest, start);
            System.out.println(tableGone);
        }
        return null;
    }

    private static void recTableBrute(List<List<Brick>> table, List<Brick> hand, List<BruteNode> tableGone,
            List<BruteNode> handBest, BruteNode parent) {
        List<Brick> allt = getAll(new ArrayList<>(), table);
        for (List<Brick> block : parent.getBlocks()) {
            allt.removeAll(block);
        }
        if (allt.size() <= 0) {
            tableGone.add(parent);
            /**List<Brick> all = getAll(hand, table);
             parent.getBlocks().forEach(bricks -> all.removeAll(bricks));
             List<BruteNode> newBest = new ArrayList<>();
             for (BruteNode best : handBest) {
             if (best.getLowestHandCount() < all.size()) {
             newBest.add(best);
             } else if (best.getLowestHandCount() == all.size()) {
             newBest.add(best);
             parent.setLowestHandCount(all.size());
             newBest.add(parent);
             } else {
             parent.setLowestHandCount(all.size());
             newBest.add(parent);
             }
             }
             if (newBest.isEmpty()) {
             parent.setLowestHandCount(all.size());
             newBest.add(parent);
             }
             handBest = new ArrayList<>(newBest);
             for (BruteNode leaf : parent.getLeafs()) {
             recTableBrute(table, hand, tableGone, handBest, leaf);
             }**/
        } else {
            for (BruteNode leaf : parent.getLeafs()) {
                recTableBrute(table, hand, tableGone, handBest, leaf);
            }
        }
    }

    private static BruteNode recBrute(BruteNode parent) {
        for (Brick l : parent.getLeft()) {
            List<Brick> remaining = new ArrayList<>(parent.getLeft());
            remaining.remove(l);
            BruteNode newBrute = getNewBrute(parent, remaining);
            List<Brick> posMatch = new ArrayList<>(newBrute.getBricks());
            posMatch.add(l);
            if (Logic.isValid(posMatch)) {
                newBrute.getBlocks().add(posMatch);
                newBrute.setBricks(new ArrayList<>());
                parent.getLeafs().add(recBrute(newBrute));
            } else {
                boolean matched = false;
                for (List<Brick> matches : parent.getBlocks()) {
                    BruteNode getMatchNode = getNewBrute(parent, remaining);
                    List<Brick> match = getMatchNode.getBlocks().stream().filter(bricks -> bricks.containsAll(matches))
                            .findFirst().get();
                    List<Brick> posBlockMatch = new ArrayList<>(match);
                    posBlockMatch.add(l);
                    if (Logic.isValid(posBlockMatch)) {
                        match.add(l);
                        parent.getLeafs().add(recBrute(getMatchNode));
                        matched = true;
                    }
                }
                if (! matched) {
                    newBrute.getBricks().add(l);
                    parent.getLeafs().add(recBrute(newBrute));
                }
            }
        }
        if (parent.getLeft().size() == 0 && parent.getBricks().size() == 0) {
            System.out.println("all done");
            System.out.println(parent.getBlocks());
        }
        return parent;
    }

    private static BruteNode getNewBrute(BruteNode parent, List<Brick> remaining) {
        BruteNode newBrute = new BruteNode();
        newBrute.setLeft(remaining);
        newBrute.setParent(parent);
        newBrute.getBlocks().addAll(parent.getBlocks());
        newBrute.getBricks().addAll(parent.getBricks());
        return newBrute;
    }


    public static ListView<Brick> process(List<Brick> hand, List<List<Brick>> table) {
        if (isBoardValid(table)) {
            List<Brick> all = getAll(hand, table);

            List<Brick> black = new ArrayList<>();
            List<Brick> red = new ArrayList<>();
            List<Brick> green = new ArrayList<>();
            List<Brick> yellow = new ArrayList<>();
            sortColors(hand, table, black, red, green, yellow);
            sort(black, red, green, yellow);

            List<BlockOption> tableOptions = new ArrayList<>();
            generateColorOptions(all, black, red, green, yellow, tableOptions);
            generateRainbowOptions(all, black, red, green, yellow, tableOptions);

            //Remove with no option
            tableOptions.forEach(blockOption -> {
                if (blockOption.getOption().size() <= 0) {
                    all.remove(blockOption.getBrick());
                    System.out.println("Removed a " + blockOption.getBrick().getColor().toString() + "block with number " +
                            blockOption.getBrick().getValue() + "");
                }
            });

            List<List<Brick>> allOptions = new ArrayList<>();
            List<Brick> optAll = new ArrayList<>(all);
            tableOptions.forEach(blockOption -> {
                blockOption.getOption().forEach(bricks -> {
                    if (optAll.containsAll(bricks)) {
                        optAll.removeAll(bricks);
                        allOptions.add(bricks);
                    }
                });
            });
            BlockNode n = new BlockNode();
            n.setParent(null);
            recursive(all, allOptions, 0, n, null);
            System.out.println("test");
        }
        return null;
    }

    private static BlockNode recursive(List<Brick> bricksLeftAtThisPoint, List<List<Brick>> allOptions, int index,
            BlockNode node, BlockNode parent) {
        if (allOptions.size() >= 1) {
            for (List<Brick> option : allOptions) {
                BlockNode n = new BlockNode();
                node.setParent(parent);
                node.setBlock(option);
                if (bricksLeftAtThisPoint.containsAll(option)) {
                    List<Brick> bricksLeftNow = new ArrayList<>(bricksLeftAtThisPoint);
                    bricksLeftNow.removeAll(option);
                    List<List<Brick>> optionsNow = new ArrayList<>(allOptions);
                    optionsNow.remove(option);
                    node.getLeafs().add(recursive(bricksLeftNow, optionsNow, 0, n, node));
                } else {
                    for (Brick b : bricksLeftAtThisPoint) {
                        addToParent(node, b, bricksLeftAtThisPoint);
                    }
                }
            }
            return node;
        }
        return null;
    }

    private static void addToParent(BlockNode parent, Brick b, List<Brick> bricksLeftAtThisPoint) {
        if (parent != null && bricksLeftAtThisPoint.contains(b)) {
            List<Brick> opt = new ArrayList<>(parent.getBlock());
            opt.add(b);
            opt.sort(Comparator.comparingInt(Brick::getValue));
            if (! Logic.isValid(opt)) {
                addToParent(parent.getParent(), b, bricksLeftAtThisPoint);
            } else {
                parent.setBlock(opt);
            }
        }
    }

    private static List<Brick> getAll(List<Brick> hand, List<List<Brick>> table) {
        List<Brick> all = new ArrayList<>();
        table.forEach(bricks -> all.addAll(bricks));
        hand.forEach(brick -> all.add(brick));
        return all;
    }

    private static void generateRainbowOptions(List<Brick> all, List<Brick> black, List<Brick> red, List<Brick> green,
            List<Brick> yellow, List<BlockOption> tableOptions) {
        for (Brick b : all) {
            BlockOption option = tableOptions.stream().filter(blockOption -> blockOption.getBrick() == b).findFirst()
                    .get();
            if (option == null) {
                option = new BlockOption();
                option.setBrick(b);
            }
            List<Brick> block = new ArrayList<>();
            Optional<Brick> bl = black.stream().filter(brick -> brick.getValue() == b.getValue()).findFirst();
            if (bl.isPresent()) {
                block.add(bl.get());
            }
            Optional<Brick> r = red.stream().filter(brick -> brick.getValue() == b.getValue()).findFirst();
            if (r.isPresent()) {
                block.add(r.get());
            }
            Optional<Brick> g = green.stream().filter(brick -> brick.getValue() == b.getValue()).findFirst();
            if (g.isPresent()) {
                block.add(g.get());
            }
            Optional<Brick> y = yellow.stream().filter(brick -> brick.getValue() == b.getValue()).findFirst();
            if (y.isPresent()) {
                block.add(y.get());
            }
            if (block.size() == 4) {
                for (int i = 0; i < 4; i++) {
                    List<Brick> cut = new ArrayList<>(block);
                    cut.remove(i);
                    if (cut.contains(b) && Logic.isValid(cut)) {
                        option.getOption().add(cut);
                    }
                }
            } else {
                if (Logic.isValid(block)) {
                    option.getOption().add(block);
                }
            }
        }
    }

    private static void generateColorOptions(List<Brick> all, List<Brick> black, List<Brick> red, List<Brick> green,
            List<Brick> yellow, List<BlockOption> tableOptions) {
        all.forEach(brick -> {
            BlockOption option = new BlockOption();
            option.setBrick(brick);
            switch (brick.getColor()) {
                case BLACK:
                    option.getOption().addAll(generateColorBlock(black, brick));
                case RED:
                    option.getOption().addAll(generateColorBlock(red, brick));
                case GREEN:
                    option.getOption().addAll(generateColorBlock(green, brick));
                case YELLOW:
                    option.getOption().addAll(generateColorBlock(yellow, brick));
            }
            tableOptions.add(option);
        });
    }

    private static Collection<? extends List<Brick>> generateColorBlock(List<Brick> black, Brick brick) {
        HashSet<Brick> singleton = new HashSet<>(black);
        singleton.add(brick);
        List<Brick> singletonList = new ArrayList<>(singleton);
        singletonList.sort(Comparator.comparingInt(Brick::getValue));
        int index = singletonList.indexOf(brick);

        List<List<Brick>> options = new ArrayList<>();
        if (index >= 0) {
            checkSimpleAscending(singletonList, index, options);
            checkJumpOption(brick, singletonList, index, options);
        }
        return options;
    }

    private static void checkJumpOption(Brick brick, List<Brick> singletonList, int index, List<List<Brick>> options) {
        List<Integer> possibleJumps = Arrays.asList(12, 13, 1, 2);
        if (possibleJumps.contains(brick.getValue())) {
            if (singletonList.get(0).getValue() == 1) {
                if (singletonList.size() >= 3) {
                    List<Brick> jumpOption = new ArrayList<>();
                    jumpOption.add(singletonList.get(0));
                    if (singletonList.get(singletonList.size() - 1).getValue() == 13) {
                        jumpOption.add(0, singletonList.get(singletonList.size() - 1));
                        if (brick.getValue() == 12) {
                            List<Brick> direktJumpOption = new ArrayList<>(jumpOption);
                            direktJumpOption.add(0, brick);
                            if (Logic.isValid(direktJumpOption)) {
                                options.add(direktJumpOption);
                            }
                        }
                        if (brick.getValue() == 2) {
                            List<Brick> indirectJumpOption = new ArrayList<>(jumpOption);
                            indirectJumpOption.add(brick);
                            if (Logic.isValid(indirectJumpOption)) {
                                options.add(indirectJumpOption);
                            }
                        }
                        if (brick.getValue() == 13 || brick.getValue() == 1) {
                            if (singletonList.get(singletonList.size() - 2).getValue() == 12) {
                                List<Brick> indirectJumpOption = new ArrayList<>(jumpOption);
                                indirectJumpOption.add(0, singletonList.get(singletonList.size() - 2));
                                if (Logic.isValid(indirectJumpOption)) {
                                    options.add(indirectJumpOption);
                                }
                            }
                            if (singletonList.get(1).getValue() == 2) {
                                List<Brick> indirectJumpOption = new ArrayList<>(jumpOption);
                                indirectJumpOption.add(singletonList.get(1));
                                if (Logic.isValid(indirectJumpOption)) {
                                    options.add(indirectJumpOption);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static void checkSimpleAscending(List<Brick> singletonList, int index, List<List<Brick>> options) {
        if (index - 2 >= 0) {
            List<Brick> option1 = singletonList.subList(index - 2, index + 1);
            if (Logic.isValid(option1)) {
                options.add(option1);
            }
        }
        if (index - 1 >= 0 && index + 1 <= singletonList.size() - 1) {
            List<Brick> option2 = singletonList.subList(index - 1, index + 2);
            if (Logic.isValid(option2)) {
                options.add(option2);
            }
        }
        if (index + 2 <= singletonList.size() - 1) {
            List<Brick> option3 = singletonList.subList(index, index + 3);
            if (Logic.isValid(option3)) {
                options.add(option3);
            }
        }
    }

    private static void sort(List<Brick> black, List<Brick> red, List<Brick> green, List<Brick> yellow) {
        black.sort(Comparator.comparingInt(Brick::getValue));
        red.sort(Comparator.comparingInt(Brick::getValue));
        green.sort(Comparator.comparingInt(Brick::getValue));
        yellow.sort(Comparator.comparingInt(Brick::getValue));
    }

    private static void sortColors(List<Brick> hand, List<List<Brick>> table, List<Brick> black, List<Brick> red,
            List<Brick> green,
            List<Brick> yellow) {
        table.forEach(bricks -> bricks.forEach(brick -> {
            addToColorTable(black, red, green, yellow, brick);
        }));
        hand.forEach(brick -> {
            addToColorTable(black, red, green, yellow, brick);
        });
    }

    private static void addToColorTable(List<Brick> black, List<Brick> red, List<Brick> green, List<Brick> yellow,
            Brick brick) {
        switch (brick.getColor()) {
            case BLACK:
                black.add(brick);
                break;
            case RED:
                red.add(brick);
                break;
            case GREEN:
                green.add(brick);
                break;
            case YELLOW:
                yellow.add(brick);
                break;
            default:
                System.err.println("Unreachable");
                break;
        }
    }

    private static boolean isBoardValid(List<List<Brick>> table) {
        for (List<Brick> blocks : table) {
            if (! Logic.isValid(blocks)) {
                showInvalidBoardDialog();
                return false;
            }
        }
        return true;
    }

    private static void showInvalidBoardDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Invalid board.");

        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
    }
}
