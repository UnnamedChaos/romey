package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import model.Brick;
import model.BruteNode;
import model.Move;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class KI {

    public static int nodes;

    public static void process(List<Brick> hand, List<List<Brick>> table) {
        if (isBoardValid(table)) {

        }
    }

    public static void process2(List<Brick> hand, List<List<Brick>> table) {
        if (isBoardValid(table)) {
            nodes = 0;
            BruteNode start = new BruteNode();
            start.setLeft(getAll(hand, table));
            start = recBrute(start);
            List<BruteNode> tableGone = new ArrayList<>();
            recTableBrute(table, hand, tableGone, start);
            BruteNode bestHand = getBestHand(tableGone);
            System.out.println(bestHand);
            //openTreeWindow(table, hand, start);
            System.out.println(nodes);
        }
    }

    private static BruteNode getBestHand(List<BruteNode> tableGone) {
        BruteNode bestHand = null;
        for (BruteNode node : tableGone) {
            if (bestHand == null || node.getBestMove().getCount() < bestHand.getBestMove().getCount()) {
                bestHand = node;
            }
        }
        return bestHand;
    }

    private static void openTreeWindow(List<List<Brick>> table, List<Brick> hand, BruteNode startNode) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Controller.class.getResource("tree.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1000, 1000);
            Stage stage = new Stage();
            stage.setTitle("Result TreeController");
            stage.setScene(scene);
            stage.show();
            TreeController treeController = fxmlLoader.getController();
            treeController.setBlocks(table);
            treeController.setHand(hand);
            treeController.setStartNode(startNode);
            treeController.process();
        } catch (IOException e) {
            System.err.println("Window cannot open");
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
        if (true) {
            for (Brick l : parent.getLeft()) {
                List<Brick> remaining = new ArrayList<>(parent.getLeft());
                remaining.remove(l);
                BruteNode newBrute = getNewBrute(parent, remaining);
                newBrute.setPlayed(l);
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
                            getMatchNode.setPlayed(l);
                            getMatchNode.getBlocks().stream().filter(bricks -> bricks.containsAll(matches)).findFirst().get()
                                    .add(l);
                            parent.getLeafs().add(recBrute(getMatchNode));
                            /**if (remaining.size() > 0) {
                             } else {
                             parent.getLeafs().add(getMatchNode);
                             }**/
                            matched = true;
                        }
                    }
                    if (! matched) {
                        newBrute.getBricks().add(l);
                        parent.getLeafs().add(recBrute(newBrute));
                    }
                }
            }
        }
        return parent;
    }

    private static BruteNode getNewBrute(BruteNode parent, List<Brick> remaining) {
        BruteNode newBrute = new BruteNode();
        KI.nodes++;
        System.out.println(KI.nodes + "");
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
