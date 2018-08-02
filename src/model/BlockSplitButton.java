package model;

import com.sun.istack.internal.NotNull;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import sample.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BlockSplitButton extends Button {
    private final Brick brick;
    private ListView<Brick> block;

    public BlockSplitButton(ListView<Brick> block, @NotNull Brick brick, Controller controller) {
        this.block = block;
        this.brick = brick;
        this.setOnAction(event -> {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Choose a operation.");
            alert.setHeaderText("Choose a operation to execute for the selected brick.");
            alert.setContentText("Choose your operation.");

            ButtonType buttonTypeJoker = new ButtonType("Joker");
            ButtonType buttonTypeSplit = new ButtonType("Split");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeJoker, buttonTypeSplit, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeJoker){
                brick.setJoker(!brick.isJoker());
                if(brick.isJoker()){
                    setText(brick.getValue() + "J");
                }
                else{
                    setText(brick.getValue()+"");
                }
            } else if (result.get() == buttonTypeSplit) {
                int index = block.getItems().indexOf(brick);
                List<Brick> newBlock = new ArrayList<>(block.getItems().subList(index, block.getItems().size()));
                block.getItems().remove(index,block.getItems().size());


                controller.generateTableBlock(newBlock);
            } else {
                // ... user chose CANCEL or closed the dialog
            }


        });
    }
}
