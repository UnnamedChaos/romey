package control;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.Brick;
import model.BruteNode;

import java.util.List;

public class TreeController {

    @FXML
    private HBox box;

    private List<Brick> hand;
    private List<List<Brick>> blocks;
    private BruteNode startNode;

    public void initialize() {

    }

    public void process() {
        showNode(startNode, box);
    }

    private void showNode(BruteNode node, HBox mainBox) {
        VBox playerBlock = new VBox();
        playerBlock.setBorder(buildBorder(Color.BLUE));
        playerBlock.getChildren().add(buildLabel("PLAY"));
        if (node.getPlayed() != null) {
            Label text = buildLabel(node.getPlayed().getValue() + "");
            playerBlock.getChildren().add(text);
        } else {
            Label text = buildLabel("EMPTY");
            playerBlock.getChildren().add(text);
        }
        VBox box = buildBlock();
        HBox infoBlock = buildInfoBlock(node, playerBlock);
        box.getChildren().add(infoBlock);

        mainBox.getChildren().add(box);
        HBox leafBox = new HBox();
        leafBox.setAlignment(Pos.CENTER);
        box.getChildren().add(leafBox);

        for (BruteNode leaf : node.getLeafs()) {
            showNode(leaf, leafBox);
        }

    }

    private HBox buildInfoBlock(BruteNode node, VBox inlineBlock) {
        HBox vert = new HBox();
        vert.setBorder(buildBorder(Color.YELLOW));

        VBox brickBlock = generateBrickList(node.getBricks(), "Bricks");

        HBox matchBlock = new HBox();
        matchBlock.setBorder(buildBorder(Color.BLUEVIOLET));
        for (List<Brick> match : node.getBlocks()) {
            VBox matchBox = generateBrickList(match, "Match");
            matchBlock.getChildren().add(matchBox);
        }

        VBox leftBlock = generateBrickList(node.getLeft(), "Left");
        leftBlock.setBorder(buildBorder(Color.RED));

        vert.getChildren().add(leftBlock);
        vert.getChildren().add(inlineBlock);
        vert.getChildren().add(brickBlock);
        vert.getChildren().add(matchBlock);
        vert.setAlignment(Pos.TOP_CENTER);
        return vert;
    }

    private VBox generateBrickList(List<Brick> brickList, String header) {
        VBox brickBlock = new VBox();
        brickBlock.getChildren().add(buildLabel(header));
        for (Brick brick : brickList) {
            Label brickLabel = buildLabel(brick.getValue() + "");
            brickBlock.getChildren().add(brickLabel);
        }
        return brickBlock;
    }

    private Label buildLabel(String content) {
        Label text = new Label();
        text.setAlignment(Pos.CENTER);
        if (content != null && ! content.isEmpty()) {
            text.setText(content);
        } else {
            text.setText("empty");
        }
        text.setMinWidth(50);
        return text;
    }

    private VBox buildBlock() {
        VBox box = new VBox();
        Border b = buildBorder(Color.BLACK);
        box.setBorder(b);
        box.setAlignment(Pos.TOP_CENTER);
        return box;
    }

    private Border buildBorder(Color color) {
        BorderStroke stroke = new BorderStroke(color, BorderStrokeStyle.SOLID, new CornerRadii(1),
                new BorderWidths(1));
        return new Border(stroke);
    }

    public List<Brick> getHand() {
        return hand;
    }

    public void setHand(List<Brick> hand) {
        this.hand = hand;
    }

    public List<List<Brick>> getBlocks() {
        return blocks;
    }

    public BruteNode getStartNode() {
        return startNode;
    }

    public void setStartNode(BruteNode startNode) {
        this.startNode = startNode;
    }

    public void setBlocks(List<List<Brick>> blocks) {
        this.blocks = blocks;
    }
}
