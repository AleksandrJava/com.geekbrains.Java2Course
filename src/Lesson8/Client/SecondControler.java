package Lesson8.Client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


import java.io.DataOutputStream;
import java.io.IOException;


public class SecondControler {
    @FXML
    Label label;

    @FXML
    TextArea textArea;

    @FXML
    Button btn;

    @FXML
    TextField textField;


    public void sendMsg(){
        if(!((MiniStage)btn.getScene().getWindow()).parentList.contains(textArea)) {
            ((MiniStage)btn.getScene().getWindow()).parentList.add(textArea);
            System.out.println("1");
        }
        DataOutputStream out = ((MiniStage)btn.getScene().getWindow()).out;
        String nickTo = ((MiniStage)btn.getScene().getWindow()).nickTo;
        try {
            out.writeUTF("/w " + nickTo + " " + textField.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

