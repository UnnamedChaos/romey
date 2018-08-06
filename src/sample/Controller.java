package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.BlockAddButton;
import model.BlockRemoveButton;
import model.Brick;
import model.BrickColor;
import model.RainBowButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Controller {

    @FXML
    private VBox table;
    @FXML
    private Button yellow;
    @FXML
    private Button black;
    @FXML
    private Button green;
    @FXML
    private Button red;
    @FXML
    private TextField numberInput;
    @FXML
    private ListView<Brick> handContainer = new ListView<>();

    @FXML
    private TextField blockNumber;

    public void initialize() {
        handContainer.setCellFactory(list -> new BrickCell()
        );
        ObservableList<Brick> oBricks = FXCollections.observableArrayList(new ArrayList<>());
        oBricks.add(BrickFactory.generateBrick("4", BrickColor.BLACK).get());
        oBricks.add(BrickFactory.generateBrick("1", BrickColor.RED).get());
        oBricks.add(BrickFactory.generateBrick("1", BrickColor.GREEN).get());

        handContainer.setItems(oBricks);

        //sampledata
        List<Brick> bricks = new ArrayList<>();
        bricks.add(BrickFactory.generateBrick("1", BrickColor.BLACK).get());
        bricks.add(BrickFactory.generateBrick("2", BrickColor.BLACK).get());
        bricks.add(BrickFactory.generateBrick("3", BrickColor.BLACK).get());
        generateTableBlock(bricks);
    }


    @FXML
    public void addBlackBlock(ActionEvent event) {
        generateTableBlock(
                Collections.singletonList(BrickFactory.generateBrick(blockNumber.getText(), BrickColor.BLACK).get()));
    }

    @FXML
    public void addRedBlock(ActionEvent event) {
        generateTableBlock(
                Collections.singletonList(BrickFactory.generateBrick(blockNumber.getText(), BrickColor.RED).get()));
    }

    @FXML
    public void addGreenBlock(ActionEvent event) {
        generateTableBlock(
                Collections.singletonList(BrickFactory.generateBrick(blockNumber.getText(), BrickColor.GREEN).get()));
    }

    @FXML
    public void addYellowBlock(ActionEvent event) {
        generateTableBlock(
                Collections.singletonList(BrickFactory.generateBrick(blockNumber.getText(), BrickColor.YELLOW).get()));
    }

    @FXML
    public void addYellow(ActionEvent event) {
        Brick b = BrickFactory.generateBrick(numberInput.getText(), BrickColor.YELLOW).get();
        handContainer.getItems().add(b);

    }

    @FXML
    public void addBlack(ActionEvent event) {
        Brick b = BrickFactory.generateBrick(numberInput.getText(), BrickColor.BLACK).get();
        handContainer.getItems().add(b);
    }

    @FXML
    public void addGreen(ActionEvent event) {
        Brick b = BrickFactory.generateBrick(numberInput.getText(), BrickColor.GREEN).get();
        handContainer.getItems().add(b);
    }

    @FXML
    public void addRed(ActionEvent event) {
        Brick b = BrickFactory.generateBrick(numberInput.getText(), BrickColor.RED).get();
        handContainer.getItems().add(b);
    }

    public void generateRainbow() {
        HBox box = generateHBox();

        ListView<Brick> block = generateBlock();
        block.setItems(FXCollections.observableArrayList());
        table.getChildren().add(block);
        box.getChildren().add(block);

        RainBowButton black = new RainBowButton(block, BrickColor.BLACK, blockNumber.getText());
        RainBowButton red = new RainBowButton(block, BrickColor.RED, blockNumber.getText());
        RainBowButton green = new RainBowButton(block, BrickColor.GREEN, blockNumber.getText());
        RainBowButton yellow = new RainBowButton(block, BrickColor.YELLOW, blockNumber.getText());

        box.getChildren().add(black);
        box.getChildren().add(red);
        box.getChildren().add(green);
        box.getChildren().add(yellow);

        table.getChildren().add(box);
    }

    public void generateTableBlock(List<Brick> blocks) {
        HBox box = generateHBox();

        ListView<Brick> block = generateBlock();
        table.getChildren().add(block);


        BlockAddButton addL = new BlockAddButton(block, false);
        addL.setText("+");
        addL.setPrefWidth(40);
        box.getChildren().add(addL);

        BlockRemoveButton removeL = new BlockRemoveButton(block, false);
        removeL.setText("-");
        removeL.setPrefWidth(40);
        box.getChildren().add(removeL);

        box.getChildren().add(block);

        BlockAddButton addR = new BlockAddButton(block, true);
        addR.setText("+");
        addR.setPrefWidth(40);
        box.getChildren().add(addR);

        BlockRemoveButton removeR = new BlockRemoveButton(block, true);
        removeR.setText("-");
        removeR.setPrefWidth(40);
        box.getChildren().add(removeR);

        Button remove = new Button();
        remove.setText("Remove");
        remove.setOnAction(event -> table.getChildren().remove(box));
        box.getChildren().add(remove);

        table.getChildren().add(box);
        blocks.forEach(s -> block.getItems().add(s));

    }

    @FXML
    private void process() {
        List<List<Brick>> list = getTableBlocks();
        KI.process(handContainer.getItems(), list);
    }

    private List<List<Brick>> getTableBlocks() {
        List<List<Brick>> list = new ArrayList<>();
        table.getChildren().forEach(node -> {
            ListView<Brick> l = (ListView) node.lookup("#block");
            list.add(l.getItems());
        });
        return list;
    }

    private ListView<Brick> generateBlock() {
        ListView<Brick> block = new ListView<>();
        block.setCellFactory(param -> new BlockBrickCell(this));
        ObservableList<Brick> oBricks = FXCollections.observableArrayList(new ArrayList<>());
        block.setId("block");
        block.setItems(oBricks);
        block.setPrefWidth(600);
        block.setOrientation(Orientation.HORIZONTAL);
        block.setFixedCellSize(45);
        block.setPadding(new Insets(0));
        return block;
    }

    private HBox generateHBox() {
        HBox box = new HBox();
        box.setMaxHeight(40);
        box.setMinHeight(40);
        box.setMinWidth(800);
        box.setSpacing(5);
        box.setAlignment(Pos.CENTER);
        return box;
    }
}
