package model.UI;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import model.Brick;

public class BlockRemoveButton extends Button {
    private final boolean top;
    private final ListView<Brick> block;

    public BlockRemoveButton(ListView<Brick> block, boolean top) {
        this.top = top;
        this.block = block;
        this.setOnAction(event -> {
            if (top) {
                int value = block.getItems().size() - 1;
                if (value >= 0) {
                    block.getItems().remove(value);
                }
            } else {
                if (block.getItems().size() > 0) {
                    block.getItems().remove(0);
                }
            }


        });
    }
}
