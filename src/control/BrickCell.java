package control;

import javafx.css.PseudoClass;
import javafx.scene.control.ListCell;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.Brick;
import model.BrickColor;

class BrickCell extends ListCell<Brick> {
    private PseudoClass YELLOW = PseudoClass.getPseudoClass("yellow");

    @Override
    public void updateItem(Brick brick, boolean empty) {
        super.updateItem(brick, empty);
        if (brick != null) {

            if (brick != null) {
                Rectangle rect = new Rectangle(30, 30);
                setMaxWidth(30);
                rect.setFill(Color.web(brick.getColor().toString()));
                Text text = new Text(brick.getValue() + "");
                if (brick.getColor() != BrickColor.YELLOW) {
                    text.setFill(Color.WHITE);
                }
                StackPane stack = new StackPane();
                stack.getChildren().addAll(rect, text);
                setGraphic(stack);
            }
        } else {
            this.setGraphic(null);
            this.setTextFill(Color.WHITE);
            this.setText("");
        }

    }
}
