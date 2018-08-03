package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import model.Brick;
import model.BruteNode;
import model.Move;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class KI {


    public static void process2(List<Brick> hand, List<List<Brick>> table) {
        if (isBoardValid(table)) {
            BruteNode start = new BruteNode();
            start.setLeft(getAll(hand, table));
            start = recBrute(start);
            List<BruteNode> tableGone = new ArrayList<>();
            recTableBrute(table, hand, tableGone, start);
            BruteNode bestHand = null;
            for (BruteNode node : tableGone) {
                if (bestHand == null || node.getBestMove().getCount() < bestHand.getBestMove().getCount()) {
                    bestHand = node;
                }
            }
            System.out.println(bestHand.getBestMove().getNode().toString());
        }
    }

    private static void recTableBrute(List<List<Brick>> table, List<Brick> hand, List<BruteNode> tableGone,
            BruteNode parent) {
        List<Brick> allt = getAll(new ArrayList<>(), table);
        for (List<Brick> block : parent.getBlocks()) {
            allt.removeAll(block);
        }
        if (allt.size() <= 0) {
            tableGone.add(parent);
            Move bestMove = new Move();
            bestMove.setNode(findBestMove(parent));
            bestMove.setCount(bestMove.getNode().getLeft().size() + bestMove.getNode().getBricks().size());
            parent.setBestMove(bestMove);
        } else {
            for (BruteNode leaf : parent.getLeafs()) {
                recTableBrute(table, hand, tableGone, leaf);
            }
        }
    }

    private static BruteNode findBestMove(BruteNode parent) {
        BruteNode node = parent;
        for (BruteNode leaf : parent.getLeafs()) {
            BruteNode bestLeafNode = findBestMove(leaf);
            if (node.getLeft().size() + node.getBricks().size() >
                    bestLeafNode.getLeft().size() + bestLeafNode.getBricks().size()) {
                node = bestLeafNode;
            }
        }
        return node;
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
                    List<Brick> posBlockMatch = new ArrayList<>(matches);
                    posBlockMatch.add(l);
                    if (Logic.isValid(posBlockMatch)) {
                        BruteNode getMatchNode = getNewBrute(parent, remaining);
                        getMatchNode.getBlocks().stream().filter(bricks -> bricks.containsAll(matches)).findFirst().get()
                                .add(l);
                        if (remaining.size() > 0) {
                            parent.getLeafs().add(recBrute(getMatchNode));
                        } else {
                            parent.getLeafs().add(getMatchNode);
                        }
                        matched = true;
                    }
                }
                if (! matched) {
                    newBrute.getBricks().add(l);
                    parent.getLeafs().add(recBrute(newBrute));
                }
            }
        }
        return parent;
    }

    private static BruteNode getNewBrute(BruteNode parent, List<Brick> remaining) {
        BruteNode newBrute = new BruteNode();
        newBrute.setLeft(remaining);
        newBrute.setParent(parent);
        for (List<Brick> block : parent.getBlocks()) {
            newBrute.getBlocks().add(new ArrayList<>(block));
        }
        newBrute.getBricks().addAll(parent.getBricks());
        return newBrute;
    }


    private static List<Brick> getAll(List<Brick> hand, List<List<Brick>> table) {
        List<Brick> all = new ArrayList<>();
        table.forEach(bricks -> all.addAll(bricks));
        hand.forEach(brick -> all.add(brick));
        return all;
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
