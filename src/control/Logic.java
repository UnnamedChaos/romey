package control;

import model.Brick;
import model.BrickColor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Logic {
    public static boolean isValid(List<Brick> list) {
        return isValidAscendingBlock(list) || isValidRainbow(list);
    }

    public static boolean isValidRainbow(List<Brick> list) {
        return isMin(list) && isMax(list) && isRainbow(list) && isMin(list);
    }

    public static boolean isValidAscendingBlock(List<Brick> list) {
        return isMin(list) && isColorCorrect(list) && isAscending(list);
    }

    private static boolean isRainbow(List<Brick> list) {
        if (list.size() > 0) {
            int rainBowValue = - 1;
            HashSet<BrickColor> colors = new HashSet<>();
            for (Brick b : list) {
                if (! b.isJoker()) {
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
            }
            return true;
        }
        return false;
    }

    public static boolean isConnectMatch(List<Brick> hand, List<List<Brick>> table) {
        List<Brick> toRemove = new ArrayList<>();
        for (Brick handBrick : hand) {
            if (isSimpleMatch(handBrick, table)) {
                toRemove.add(handBrick);
                System.out.println("Found connecting match for " + handBrick.getValue() + " " + handBrick.getColor());
                break;
            }
        }
        toRemove.forEach(brick -> hand.remove(brick));
        return toRemove.size() > 0;
    }

    private static boolean isSimpleMatch(Brick handBrick, List<List<Brick>> table) {
        for (List<Brick> block : table) {
            List<Brick> preBlock = new ArrayList<>(block);
            preBlock.add(0, handBrick);
            if (! isValid(preBlock)) {
                preBlock.remove(0);
                preBlock.add(handBrick);
                if (isValid(preBlock)) {
                    int index = table.indexOf(block);
                    table.remove(block);
                    table.add(index, preBlock);
                    return true;
                }
            } else {
                int index = table.indexOf(block);
                table.remove(block);
                table.add(index, preBlock);
                return true;
            }
        }
        return false;
    }

    public static List<List<Brick>> split(List<Brick> toChunk, int size) {
        List<List<Brick>> chunks = new ArrayList<>();
        int index = 0;
        while (index + size <= toChunk.size()) {
            List<Brick> chunk = new ArrayList<>(toChunk.subList(index, index + size));
            chunks.add(chunk);
            index += size;
        }
        if (toChunk.size() % size > 0) {
            List<Brick> subChunk = new ArrayList<>(
                    toChunk.subList(toChunk.size() - (toChunk.size() % size), toChunk.size()));
            chunks.add(subChunk);
        }
        return chunks;
    }

    public static boolean isHandBrickColorPlayable(Brick handBrick, List<Brick> table) {
        int cCounter = 1;
        HashSet<BrickColor> colors = new HashSet<>();
        for (Brick t : table) {
            if (t.isJoker() || t.getColor() != handBrick.getColor() && t.getValue() == handBrick.getValue() && colors.add(
                    t.getColor())) {
                cCounter++;
                colors.add(t.getColor());
            }
            if (cCounter >= 3) {
                return true;
            }
        }
        return false;
    }

    public static boolean isHandBrickAscendingPlayable(Brick handBrick, List<Brick> table) {
        int cCounter = 1;
        for (Brick b : table) {
            if (b.isJoker()) {
                cCounter++;
            }
        }
        if (table.contains(
                BrickFactory.generateBrick(String.valueOf(handBrick.getValue() - 1), handBrick.getColor()).get())) {
            cCounter++;
            if (table.contains(
                    BrickFactory.generateBrick(String.valueOf(handBrick.getValue() - 2), handBrick.getColor()).get())) {
                cCounter++;
            }
        }
        if (table.contains(
                BrickFactory.generateBrick(String.valueOf(handBrick.getValue() + 1), handBrick.getColor()).get())) {
            cCounter++;
            if (table.contains(
                    BrickFactory.generateBrick(String.valueOf(handBrick.getValue() + 2), handBrick.getColor()).get())) {
                cCounter++;
            }
        }
        if (cCounter >= 3) {
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
            if (! b.isJoker()) {
                if (color == null) {
                    color = b.getColor();
                } else {
                    if (color != b.getColor()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static List<Brick> getFreeBricks(List<Brick> block) {
        List<Brick> free = new ArrayList<>();
        if (block.size() >= 4) {
            free.add(block.get(0));
            free.add(block.get(block.size() - 1));
        }
        if (block.size() >= 7) {
            for (int i = 3; i < block.size() - 4; i++) {

            }
        }
        return Collections.emptyList();
    }

    private static boolean isMin(List<Brick> list) {
        return list.size() >= 3;
    }

    private static boolean isAscending(List<Brick> list) {
        for (int i = 0; i < list.size(); i++) {
            if (i + 1 < list.size() && ! list.get(i).isJoker()) {
                if (list.get(i).getValue() == 13) {
                    if (! list.get(i + 1).isJoker() && (list.get(i).getValue() + 1) % 13 != list.get(i + 1).getValue()) {
                        return false;
                    }
                } else {
                    if (! list.get(i + 1).isJoker() && (list.get(i).getValue() + 1) != list.get(i + 1).getValue()) {
                        return false;

                    }
                }
            } else {
                if (i >= 1 && i < list.size() - 1) {
                    if (list.get(0).getValue() > list.get(i + 1).getValue()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean isPickMatchHand(List<Brick> hand, List<List<Brick>> table) {
        List<Brick> toRemove = new ArrayList<>();
        for (Brick handBrick : hand) {
            List<Brick> interestingBricks = findInterestingBricks(table, hand, handBrick);
            for (Brick interestingBrick : interestingBricks) {
                List<Brick> shortMatch = findShortRainbowMatch(handBrick, interestingBrick);
                List<Brick> wideMatch = new ArrayList<>(shortMatch);
                if (isRainbowShortMatch(shortMatch)) {
                    if (findRainbowPickMatch(table, toRemove, handBrick, interestingBricks, interestingBrick, shortMatch,
                            wideMatch)) {
                        toRemove.forEach(brick -> hand.remove(brick));
                        System.out.println(
                                "Found Rainbowpickmatch for " + handBrick.getValue() + " " + handBrick.getColor());
                        return true;
                    }
                }
                if (isAscendingShortMatch(shortMatch)) {
                    if (findAscendingPickMatch(table, hand, toRemove, handBrick, interestingBrick, shortMatch, wideMatch)) {
                        toRemove.forEach(brick -> hand.remove(brick));
                        System.out.println(
                                "Found Ascendingpickmatch for " + handBrick.getValue() + " " + handBrick.getColor());
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean findRainbowPickMatch(List<List<Brick>> table, List<Brick> toRemove, Brick handBrick,
            List<Brick> interestingBricks, Brick interestingBrick, List<Brick> shortMatch, List<Brick> wideMatch) {
        Brick wideBrick = findWideRainbowMatch(interestingBricks, shortMatch, wideMatch);
        if (isValid(wideMatch)) {
            toRemove.add(handBrick);
            cutBlockByInterest(table, interestingBrick, toRemove);
            cutBlockByInterest(table, wideBrick, toRemove);
            table.add(wideMatch);
            return true;
        }
        return false;
    }

    private static boolean findAscendingPickMatch(List<List<Brick>> table, List<Brick> hand, List<Brick> toRemove,
            Brick handBrick,
            Brick interestingBrick, List<Brick> shortMatch, List<Brick> wideMatch) {
        Brick wideBrick = findLowWideAscendingInterestBrick(table, hand, shortMatch);
        if (wideBrick == null) {
            wideBrick = findHighWideAscendingInterestBrick(table, hand, shortMatch);
            if (wideBrick != null) {
                wideMatch.add(wideBrick);
                toRemove.add(handBrick);
                cutBlockByInterest(table, interestingBrick, toRemove);
                cutBlockByInterest(table, wideBrick, toRemove);
                table.add(wideMatch);
                return true;
            }

        } else {
            wideMatch.add(0, wideBrick);
            toRemove.add(handBrick);
            cutBlockByInterest(table, interestingBrick, toRemove);
            cutBlockByInterest(table, wideBrick, toRemove);
            table.add(wideMatch);
            return true;
        }
        return false;
    }

    private static Brick findHighWideAscendingInterestBrick(List<List<Brick>> table, List<Brick> hand,
            List<Brick> shortMatch) {
        List<Brick> highWideInterest = findInterestingBricks(table, hand, shortMatch.get(1));
        Optional<Brick> wideInterestBlock = highWideInterest.stream().filter(brick -> brick.getColor() == shortMatch.get(1)
                .getColor() && brick.getValue() != shortMatch.get(0).getValue()).findFirst();
        return wideInterestBlock.isPresent() ? wideInterestBlock.get() : null;
    }

    private static Brick findLowWideAscendingInterestBrick(List<List<Brick>> table, List<Brick> hand,
            List<Brick> shortMatch) {
        List<Brick> lowWideInterest = findInterestingBricks(table, hand, shortMatch.get(0));
        Optional<Brick> op = lowWideInterest.stream().filter(brick -> brick.getColor() == shortMatch.get(0)
                .getColor() && brick.getValue() != shortMatch.get(1).getValue()).findFirst();
        return op.isPresent() ? op.get() : null;
    }

    private static boolean isAscendingShortMatch(List<Brick> shortMatch) {
        return shortMatch.get(0).getColor() == shortMatch.get(1).getColor() && shortMatch.get(0).getValue() !=
                shortMatch.get(1).getValue();
    }

    private static boolean cutBlockByInterest(List<List<Brick>> table, Brick brickToRemove,
            List<Brick> toRemove) {
        List<List<Brick>> toAdd = new ArrayList<>();
        List<List<Brick>> blockToRemove = new ArrayList<>();
        for (List<Brick> block : table) {
            if (block.contains(brickToRemove) && isBrickFree(block, brickToRemove)) {
                toAdd.add(block.subList(0, block.indexOf(brickToRemove)));
                toAdd.add(block.subList(block.indexOf(brickToRemove) + 1, block.size()));
                blockToRemove.add(block);
                break;
            }
        }
        table.removeAll(blockToRemove);
        toAdd = toAdd.stream().filter(bricks -> bricks.size() > 0).collect(Collectors.toList());
        table.addAll(toAdd);
        if (toAdd.isEmpty()) {
            toRemove.add(brickToRemove);
        }
        return toAdd.size() > 0;
    }

    private static Brick findWideRainbowMatch(List<Brick> interestingBlocks, List<Brick> shortMatch, List<Brick> wideMatch) {
        Brick wideBlock = null;
        for (Brick sInterestingBlock : interestingBlocks) {
            if (sInterestingBlock.getValue() == shortMatch.get(0).getValue() && ! shortMatch.contains(
                    sInterestingBlock)) {
                wideMatch.add(sInterestingBlock);
                wideBlock = sInterestingBlock;
                break;
            }
        }
        return wideBlock;
    }

    private static boolean isRainbowShortMatch(List<Brick> shortMatch) {
        return shortMatch.stream().filter(brick -> brick.getValue() == shortMatch.get(0).getValue()).count() == 2;
    }

    private static List<Brick> findShortRainbowMatch(Brick handBrick, Brick interestingBlock) {
        List<Brick> shortMatch = new ArrayList<>();
        shortMatch.add(interestingBlock);
        shortMatch.add(handBrick);
        shortMatch.sort((o1, o2) -> o1.getValue() == 1 && o2.getValue() == 13 ? - 1 :
                o1.getValue() == 13 && o2.getValue() == 1 ? - 1 :
                        o1.getValue() < o2.getValue() ? - 1 : 1);
        return shortMatch;
    }

    private static List<Brick> findInterestingBricks(List<List<Brick>> table, List<Brick> hand,
            Brick interested) {
        List<Brick> interestingBlocks = new ArrayList<>();
        for (List<Brick> block : table) {
            for (Brick blockBrick : block) {
                if (interested.isInterestedIn(blockBrick) && isBrickFree(block, blockBrick)) {
                    interestingBlocks.add(blockBrick);
                }
            }
        }
        for (Brick handBrick : hand) {
            if (handBrick != interested) {
                if (interested.isInterestedIn(handBrick)) {
                    interestingBlocks.add(handBrick);
                }
            }
        }
        return interestingBlocks;
    }

    public static boolean isBrickFree(List<Brick> block, Brick blockBrick) {
        if (block.size() >= 4) {
            if (block.indexOf(blockBrick) == 0 || block.indexOf(blockBrick) == block.size() - 1 || isRainbow(block)) {
                return true;
            }
        }
        if (block.size() >= 7) {
            if (block.indexOf(blockBrick) >= 3 && block.indexOf(blockBrick) <= block.size() - 1 - 3) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSplitMatch(List<Brick> hand, List<List<Brick>> table) {
        List<Brick> toRemove = new ArrayList<>();
        List<List<Brick>> blocksToRemove = new ArrayList<>();
        List<List<Brick>> blocksToAdd = new ArrayList<>();
        findSplitMatch(hand, table, toRemove, blocksToRemove, blocksToAdd);
        toRemove.forEach(brick -> hand.remove(brick));
        table.removeAll(blocksToRemove);
        table.addAll(blocksToAdd);
        return toRemove.size() > 0;
    }

    private static boolean findSplitMatch(List<Brick> hand, List<List<Brick>> table, List<Brick> toRemove,
            List<List<Brick>> blocksToRemove, List<List<Brick>> blocksToAdd) {
        for (Brick handBrick : hand) {
            for (List<Brick> block : table) {
                if (block.size() >= 5) {
                    if (! findStartSplitMatch(toRemove, blocksToRemove, blocksToAdd, handBrick, block)) {
                        if (findShortEndSplitMatch(toRemove, blocksToRemove, blocksToAdd, handBrick, block)) {
                            return true;
                        }
                    } else {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean findShortEndSplitMatch(List<Brick> toRemove, List<List<Brick>> blocksToRemove,
            List<List<Brick>> blocksToAdd, Brick handBrick, List<Brick> block) {
        List<Brick> shortEnd = new ArrayList<>(block.subList(block.size() - 2, block.size()));
        shortEnd.add(0, handBrick);
        if (isValid(shortEnd)) {
            toRemove.add(handBrick);
            blocksToRemove.add(block);
            blocksToAdd.add(shortEnd);
            blocksToAdd.add(block.subList(0, block.size() - 2));
            return true;
        }
        return false;
    }

    private static boolean findStartSplitMatch(List<Brick> toRemove, List<List<Brick>> blocksToRemove,
            List<List<Brick>> blocksToAdd, Brick handBrick, List<Brick> block) {
        for (int i = 2; i < block.size() - 3; i++) {
            List<Brick> shortBlock = new ArrayList<>(block.subList(0, i));
            shortBlock.add(handBrick);
            if (isValid(shortBlock)) {
                toRemove.add(handBrick);
                blocksToRemove.add(block);
                blocksToAdd.add(shortBlock);
                blocksToAdd.add(block.subList(i, block.size()));
                return true;
            }
        }
        return false;
    }
}
