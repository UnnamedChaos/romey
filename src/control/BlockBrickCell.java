package control;

import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Brick;
import model.UI.BlockSplitButton;

class BlockBrickCell extends ListCell<Brick> {

    private final Controller controller;

    public BlockBrickCell(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void updateItem(Brick brick, boolean empty) {
        super.updateItem(brick, empty);
        if (brick != null) {

            if (brick != null) {
                Rectangle rect = new Rectangle(40, 40);
                setMaxWidth(40);
                BlockSplitButton b = new BlockSplitButton(getListView(), brick, controller);
                if (brick.isJoker()) {
                    b.setText(brick.getValue() + "J");
                } else {
                    b.setText(brick.getValue() + "");
                }
                setPadding(new Insets(0));
                rect.setFill(Color.web(brick.getColor().toString()));
                StackPane stack = new StackPane();
                stack.getChildren().addAll(rect, b);
                setGraphic(stack);
            }
        } else {
            this.setGraphic(null);
            this.setTextFill(Color.WHITE);
            this.setText("");
        }

    }
}
