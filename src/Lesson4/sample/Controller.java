package Lesson4.sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class Controller {


    @FXML
    VBox vBox;

    @FXML
    TextField textField;

    @FXML
    HBox controlPane;

    @FXML
    Button btn1;

    int k = 0;


    public void sendMsg(ActionEvent actionEvent) {

        sendMessage();


    }

    public void sendMessage() {

        if(textField.getText().isEmpty()){


        }else {

            Double s = vBox.getWidth() * 0.75;
            if (k == 0) {


                HBox box = new HBox();


                vBox.setMargin(box, new Insets(10, 10, 10, 0));

                box.setAlignment(Pos.TOP_LEFT);

                Label message = new Label(textField.getText());
                message.setMaxWidth(s);
                message.setWrapText(true);

                box.getChildren().add(message);
                vBox.getChildren().add(box);


                k = 1;
            } else if (k == 1) {

                HBox box = new HBox();
                box.setMargin(box, new Insets(10, 0, 10, 10));


                box.setAlignment(Pos.TOP_RIGHT);
                Label message = new Label(textField.getText());
                message.setMaxWidth(s);
                message.setWrapText(true);

                box.getChildren().add(message);
                vBox.getChildren().add(box);

                k = 0;
            }


            textField.clear();
            textField.requestFocus();


        }
    }
}
