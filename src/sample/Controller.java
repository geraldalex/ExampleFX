package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;


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
    @FXML
    ListView<String> listView = new ListView<>();

    private Database database = new Database();
    private ObservableList<String> items = FXCollections.observableArrayList();
    private int curArraySize = database.getAll().size();
    private ArrayList<Integer> IDs = new ArrayList<>();

    public Controller() {
    }

    public void onAdd() throws NumberFormatException {
        try {
            if (!fieldID.getText().isEmpty() && !fieldName.getText().isEmpty() && !fieldSurname.getText().isEmpty() && !fieldPayment.getText().isEmpty()) {
                int ID = Integer.parseInt(fieldID.getText());
                if (ID <= 0) throw new ArrayIndexOutOfBoundsException("ID is forbidden");
                if (!database.isFreeID(ID))
                    throw new IllegalArgumentException();  // checking if ID is unique
                IDs.add(ID);
                String name = fieldName.getText();
                String surname = fieldSurname.getText();
                Exception wrongPaymentException = new Exception();
                double payment = Double.parseDouble(fieldPayment.getText());
                if (payment <= 0) throw wrongPaymentException;
                database.add(new Worker(ID, name, surname, payment));  // adding new item
                fieldSuccess.setText("Successful");
                fieldID.clear();  // clear fields after adding item
                fieldName.clear(); //
                fieldSurname.clear(); //
                fieldPayment.clear();  //
            } else fieldSuccess.setText("Some field might be empty");
        } catch (ArrayIndexOutOfBoundsException ex) {
            fieldSuccess.setText("ID out of bonds");
        } catch (NumberFormatException ex) {
            fieldSuccess.setText("Illegal input format!!!");
        } catch (IllegalArgumentException ex) {
            fieldSuccess.setText("ID is not free");
        } catch (Exception e) {
            fieldSuccess.setText("Wrong payment");
        }
        displayAll();
    }

    public void displayAll() {
        listView.getItems().clear();
        for (Worker worker : database.getAll()) {
            items.add(worker.getID() + "  " + worker.getName() + "  " + worker.getSurname() + "  " + worker.getPayment() + '\n');
        }
        listView.setItems(items);
    }

    public void pushToFile() {
        Path file = Paths.get("./newFile.txt");
        byte[] str;
        try {
            OutputStream out = new BufferedOutputStream(Files.newOutputStream(file, CREATE, APPEND));

            for (Worker worker : database.getAll()) {
                str = (worker.getID() + "  " + worker.getName() + "  " + worker.getSurname() + "  " + worker.getPayment() + "\n").getBytes();
                out.write(str, 0, str.length);
            }
            out.close();
        } catch (IOException ex) {
            System.err.println(ex);
        }
        database.removeAll();
        displayAll();
    }


    public void removeAll() {
        database.removeAll();
        displayAll();
    }

}
