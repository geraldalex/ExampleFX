package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.ArrayList;


public class Controller {

    @FXML
    TextField fieldID;
    @FXML
    TextField fieldName;
    @FXML
    TextField fieldSurname;
    @FXML
    TextField fieldPayment;
    @FXML
    TextField fieldSuccess;

    private ArrayList<Worker> workers = new ArrayList<>();
    public Controller(){
    }

    public void onAdd(){
        if (!fieldID.getText().isEmpty() && !fieldName.getText().isEmpty() && !fieldSurname.getText().isEmpty() && !fieldPayment.getText().isEmpty()){
            workers.add(new Worker(Integer.parseInt(fieldID.getText()), fieldName.getText(), fieldSurname.getText(), Double.parseDouble(fieldPayment.getText())));
            fieldSuccess.setText("Succesful");
        } else fieldSuccess.setText("Failure");
    }




}
