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
import java.util.ResourceBundle;

/**
 * ModifyPartController Class
 */
public class ModifyPartController implements Initializable {
    private static Part selectedPart;
    private static Integer partIndex = 0;
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
     * set selected part
     *
     * @param sPart sPart
     */
    public static void setSelectedPart(Part sPart) {
        selectedPart = sPart;
    }

    /**
     * set part index
     *
     * @param pIndex pIndex
     */
    public static void setPartIndex(Integer pIndex) {
        partIndex = pIndex;
    }


    /**
     * Event handler for saving a modified part
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
        int id = selectedPart.getId();
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
            Inventory.updatePart(partIndex, part);
            onCancel(event); //close add parts window
        }
    }

    /**
     * Event Handler for canceling part modification
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
     * sets data for the inputs on the screen
     *
     * @param url            url
     * @param resourceBundle resourceBundle
     */
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ihButton.setToggleGroup(group);
        osButton.setToggleGroup(group);

        nameInput.setText(selectedPart.getName());
        invInput.setText(Integer.toString(selectedPart.getStock()));
        priceInput.setText(Double.toString(selectedPart.getPrice()));
        maxInput.setText(Integer.toString(selectedPart.getMax()));
        minInput.setText(Integer.toString(selectedPart.getMin()));

        if (selectedPart instanceof Outsourced) {
            osButton.setSelected(true);
            customInput.setText(((Outsourced) selectedPart).getCompanyName());
            customLabel.setText("Company Name");
        } else if (selectedPart instanceof InHouse) {
            ihButton.setSelected(true);
            customInput.setText(Integer.toString(((InHouse) selectedPart).getMachineId()));
            customLabel.setText("Machine ID");
        }

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