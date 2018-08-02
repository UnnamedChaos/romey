package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import sample.BrickFactory;

import java.util.List;

public class RainBowButton extends Button {
    private final BrickColor color;
    private ListView<Brick> block;
    private Brick brick;

    public RainBowButton(ListView<Brick> block,BrickColor color, String value) {
        this.color = color;
        this.block = block;
        this.setText(value);
        this.setTextFill(Color.valueOf(color.toString()));
        this.setOnAction(event -> {
            this.block.refresh();
            if(brick == null){
                this.brick = BrickFactory.generateBrick(value,color).get();
                this.block.getItems().add(brick);
            }
            else {
                int index = 0;
                int toRemove = -1;
                for (Brick brick1 : this.block.getItems()){
                    if (brick1.getColor() == brick.getColor() && brick1.getValue() == brick.getValue()) {
                        toRemove = index;
                        break;
                    }
                    index ++;
                }
                if(toRemove != -1){
                    if(event.getSource() instanceof RainBowButton){
                        RainBowButton b = (RainBowButton) event.getSource();
                        b.block.getItems().remove(brick);
                        this.brick = null;
                    }
                }
            }
        });
    }
}
