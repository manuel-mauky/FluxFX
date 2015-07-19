package counterapp;

import eu.lestard.fluxfx.react.ReactView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CounterView implements ReactView {
    @FXML
    public Label countLabel;

    private int countValue;


    @Override
    public void setInitialState() {
        countValue = 0;
    }


    @Override
    public void render() {
        countLabel.setText(Integer.toString(countValue));
    }


    public void count() {
        setState(() -> {
            countValue = countValue + 1;
        });
    }
}
