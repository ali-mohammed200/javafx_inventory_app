package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import com.c482_inventory_system.c482_pa.MainApplication;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * RUNTIME ERROR:
 * - NumberFormatException
 * - - Occurred in the AddPartController onSave Function
 * - - Reason:
 * - - - String inputs were not convertable to numeric formats
 * - - - and the exception was thrown
 * - - Solution:
 * - - - Encompass the logic within Try Catch and inform the user
 * - - - of the incorrect data type
 */
public class AddPartController implements Initializable {
    private final ToggleGroup group = new ToggleGroup();
    @FXML
    private Stage stage;
    @FXML
    private RadioButton ihButton;
    @FXML
    private RadioButton osButton;
    @FXML
    private TextField nameInput;
    @FXML
    private TextField invInput;
    @FXML
    private TextField priceInput;
    @FXML
    private TextField maxInput;
    @FXML
    private TextField customInput;
    @FXML
    private Label customLabel;
    @FXML
    private TextField minInput;
    @FXML
    private Label warningLabel;

    /**
     * Event handler for saving a new part
     * RUNTIME ERROR:
     * * - NumberFormatException
     * * - Details are listed above
     *
     * @param event event
     * @throws IOException IOException
     */
    @FXML
    protected void onSave(ActionEvent event) throws IOException {
        String warning = "";
        String name = nameInput.getText();
        if (name == "") {
            warning += "Name is empty. ";
        }
        int inv = 0;
        try {
            inv = Integer.parseInt(invInput.getText());
        } catch (NumberFormatException e) {
            warning += "Inv is not a integer. ";
        }

        double price = 0;
        try {
            price = Double.parseDouble(priceInput.getText());
        } catch (NumberFormatException e) {
            warning += "Price is not a double. ";
        }

        int max = 0;
        try {
            max = Integer.parseInt(maxInput.getText());
        } catch (NumberFormatException e) {
            warning += "Max is not an integer. ";
        }

        int min = 0;
        try {
            min = Integer.parseInt(minInput.getText());
        } catch (NumberFormatException e) {
            warning += "Min is not an integer. ";
        }

        if (min > max) {
            warning += "Min must be less than max. ";
        }
        if (inv > max || inv < min) {
            warning += "Inv must be between min and max. ";
        }


        String custom = customInput.getText();
        int id = new Random().nextInt(1000);
        Part part = null;
        if (ihButton.isSelected()) {
            int machineId = 0;
            try {
                machineId = Integer.parseInt(custom);
                part = new InHouse(id, name, price, inv, min, max, machineId);
            } catch (NumberFormatException e) {
                warning += "Machine ID is not an integer. ";
            }
        } else if (osButton.isSelected()) {
            part = new Outsourced(id, name, price, inv, min, max, custom);
        }

        if (warning.length() > 0) {
            warningLabel.setText("Exception:\n" + warning);
        } else {
            Inventory.addPart(part);
            onCancel(event); //close add parts window
        }
    }

    /**
     * Event Handler for canceling part creation
     *
     * @param event event
     * @throws IOException IOException
     */
    @FXML
    protected void onCancel(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/View/main-form.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Inventory Management - Main");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Overrides the initialize method of the Initializable interface
     * sets groups the toggles and set data on the screen
     *
     * @param url            url
     * @param resourceBundle resourceBundle
     */
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ihButton.setToggleGroup(group);
        osButton.setToggleGroup(group);

        ihButton.setSelected(true);
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (ihButton.isSelected()) {
                    customLabel.setText("Machine ID");
                } else if (osButton.isSelected()) {
                    customLabel.setText("Company Name");
                }
            }
        });
    }
}