package control;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import model.Brick;
import model.BruteNode;
import model.Matches;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class KI {

    public static void process2(List<Brick> hand, List<List<Brick>> table, Controller controller) {
        if (isBoardValid(table)) {
            processSimpleMatch(hand, table, controller);
        }
    }

    private static void processSimpleMatch(List<Brick> hand, List<List<Brick>> table, Controller controller) {
        boolean restart = true;

        while (restart) {
            restart = false;
            if (! restart) {
                restart = Logic.isPickMatchHand(hand, table);
            }
            if (! restart) {
                restart = Logic.isConnectMatch(hand, table);
            }
            if (! restart) {
                restart = Logic.isSplitMatch(hand, table);
            }
        }
        Matches simpleMatch = new Matches();
        simpleMatch.setHandLeft(hand);
        simpleMatch.setMatches(table);
        controller.updateTable(simpleMatch);
    }

    public static void process(List<Brick> hand, List<List<Brick>> table, Controller controller) {
        if (isBoardValid(table)) {

            processSimpleMatch(hand, table, controller);


            List<Brick> tableAll = getAll(Collections.emptyList(), table);
            List<Brick> playAbleHand = getPlayableHand(hand, tableAll);
            List<Brick> notPlayableHand = new ArrayList<>(hand);
            notPlayableHand.removeAll(playAbleHand);

            if (playAbleHand.size() > 0) {
                int threadCount = 2;
                List<Thread> threads = new ArrayList<>();
                for (int th = 0; th < threadCount; th++) {
                    Thread t = new Thread(() -> {
                        System.out.println("Started thread " + Thread.currentThread().toString());
                        List<Brick> shuffledTable = new ArrayList<>(tableAll);
                        Collections.shuffle(shuffledTable);
                        Matches matches = buildPermutations(playAbleHand, shuffledTable, controller);
                        if (matches != null) {
                            System.out.println(Thread.currentThread().toString() + " found best solution.");
                            matches.getHandLeft().addAll(notPlayableHand);
                            synchronized (controller) {
                                if (! threads.isEmpty()) {
                                    System.out.println("Interrupt all other threads");
                                    for (Thread otherThread : threads) {
                                        if (otherThread != Thread.currentThread()) {
                                            otherThread.interrupt();
                                        }
                                    }
                                }
                                threads.clear();
                            }
                        }
                    });
                    threads.add(t);
                    t.start();
                }
            }

        }
    }

    private static List<Brick> getPlayableHand(List<Brick> hand, List<Brick> table) {
        List<Brick> playableHand = new ArrayList<>();
        for (Brick b : hand) {
            List<Brick> subBrickHand = new ArrayList<>(hand);
            subBrickHand.remove(b);
            List<Brick> tableHandAll = getAllSimple(subBrickHand, table);
            if (Logic.isHandBrickColorPlayable(b, tableHandAll) || Logic.isHandBrickAscendingPlayable(b, tableHandAll)) {
                playableHand.add(b);
            } else {
                System.err.println(b.getValue() + " " + b.getColor() + " is not playable.");
            }
        }
        return playableHand;
    }

    private static void showNewTable(Matches matches) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Controller.class.getResource("nextMove.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 1000);
        Stage stage = new Stage();
        stage.setTitle("Next Move");
        stage.setScene(scene);
        stage.show();
    }

    private static List<Brick> getMatchList(Matches matches, List<Brick> bricks) {
        int offset = 0;
        int length = 3;
        List<Brick> posMatch = new ArrayList<>(bricks.subList(offset, length));
        if (Logic.isValid(posMatch)) {
            while (Logic.isValid(posMatch)) {
                length++;
                if (length <= bricks.size()) {
                    posMatch = bricks.subList(offset, length);
                } else {
                    break;
                }
            }
            if (! Logic.isValid(posMatch)) {
                posMatch = posMatch.subList(0, posMatch.size() - 1);
            }
            matches.getMatches().add(posMatch);
            List<Brick> returnList = new ArrayList<>(posMatch);
            if (bricks.size() - posMatch.size() >= 3) {
                returnList.addAll(getMatchList(matches, bricks.subList(length - 1, bricks.size())));
            }
            return returnList;
        }
        return Collections.emptyList();
    }


    private static Matches buildPermutations(List<Brick> hand, List<Brick> table, Controller controller) {
        List<Brick> bricks = getAllSimple(hand, table);
        Matches matches = null;
        if (! hand.isEmpty()) {
            long permutations = factorial(bricks.size());
            System.out.println("Start permutations: " + permutations);
            matches = iterate_combinations(new ArrayList<>(), bricks, table, hand, matches, bricks.size(), controller);
            if (matches != null) {
                controller.updateTable(matches);
            }
            System.out.println("Finished permutations");
        } else {
            System.err.println("Playable hand is empty. No need to permutate.");
        }
        return matches;
    }

    public static long factorial(int n) {
        if (n > 20) {
            throw new IllegalArgumentException(n + " is out of range");
        }
        return (1 > n) ? 1 : n * factorial(n - 1);
    }

    public static int node = 0;

    public static Matches iterate_combinations(List<Brick> combination, List<Brick> bricks, List<Brick> table,
            List<Brick> hand, Matches matches, int maxStones, Controller controller) {
        if (Thread.interrupted()) {
            System.out.println("Thread " + Thread.currentThread().toString() + " was interrupted.");
            return null;
        }
        for (Brick b : bricks) {
            List<Brick> posComb = new ArrayList<>(combination);
            posComb.add(b);
            List<Brick> left = new ArrayList<>(bricks);
            left.remove(b);
            matches = iterate_combinations(posComb, left, table, hand, matches, maxStones, controller);
            if (Thread.interrupted()) {
                System.out.println("Thread " + Thread.currentThread().toString() + " was interrupted.");
                return null;
            }
            if (matches != null && maxStones == matches.getMatchSize()) {
                break;
            }
        }
        if (Thread.interrupted()) {
            System.out.println("Thread " + Thread.currentThread().toString() + " was interrupted.");
            return null;
        }
        if (matches != null && maxStones == matches.getMatchSize()) {
            return matches;
        }
        if (combination.size() >= maxStones) {
            Matches posBetterMatch = new Matches();
            List<Brick> posBest = getMatchList(posBetterMatch, combination);
            //System.out.println("N: " + KI.node + " PosS: " + posBest.size());
            //KI.node++;
            if (posBest.size() > table.size() && posBest.containsAll(table)) {
                if (matches == null || matches.getMatchSize() < posBest.size()) {
                    //Hand Left
                    List<Brick> all = new ArrayList<>(table);
                    all.addAll(hand);
                    for (List<Brick> match : posBetterMatch.getMatches()) {
                        all.removeAll(match);
                    }
                    posBetterMatch.getHandLeft().addAll(all);

                    System.out.println(Thread.currentThread().toString() + " Found better solution");
                    System.out.println(posBetterMatch.getMatches());
                    System.out.println(maxStones + " / " + posBetterMatch.getMatchSize());
                    return posBetterMatch;
                } else {
                    return matches;
                }
            }
        }
        return matches;
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

    private static List<Brick> getAll(List<Brick> hand, List<List<Brick>> table) {
        List<Brick> all = new ArrayList<>();
        table.forEach(bricks -> all.addAll(bricks));
        hand.forEach(brick -> all.add(brick));
        return all;
    }

    private static List<Brick> getAllSimple(List<Brick> hand, List<Brick> table) {
        List<Brick> all = new ArrayList<>(hand);
        all.addAll(table);
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
