package model;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import sample.BrickFactory;

public class BlockAddButton extends Button {
    private final boolean top;
    private ListView<Brick> block;

    public BlockAddButton(ListView<Brick> block,boolean top) {
        this.top = top;
        this.block = block;
        this.setOnAction(event -> {
            if(top){
                int value = block.getItems().get(block.getItems().size()-1).getValue() + 1;
                if(value > 13){
                    value = 1;
                }
                block.getItems().add(BrickFactory.generateBrick(value+"",block.getItems().get(block.getItems().size() - 1).getColor()).get());
            }else{
                int value = block.getItems().get(0).getValue() - 1;
                if(value < 1){
                    value = 13;
                }
                block.getItems().add(0,BrickFactory.generateBrick(value+"",block.getItems().get(0).getColor()).get());
            }


        });
    }
}
