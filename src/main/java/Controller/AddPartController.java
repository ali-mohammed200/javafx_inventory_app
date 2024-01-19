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
import java.util.Random;

public class AddPartController implements Initializable {
    @FXML
    private Stage stage;

    @FXML
    private RadioButton ihButton;
    @FXML
    private RadioButton osButton;
    @FXML
    private TextField idInput;
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
    private ToggleGroup group = new ToggleGroup();

    @FXML
    protected void onSave(ActionEvent event) {
        String name = nameInput.getText();
        int inv = Integer.parseInt(invInput.getText());
        double price = Double.parseDouble(priceInput.getText());
        int max = Integer.parseInt(maxInput.getText());
        int min = Integer.parseInt(minInput.getText());
        String custom = customInput.getText();
        int id = new Random().nextInt(1000);
        Part part = null;
        if (ihButton.isSelected()) {
            int machineId = Integer.parseInt(custom);
            part = new InHouse(id, name, price, inv, max, min, machineId);
        } else if (osButton.isSelected()) {
            part = new Outsourced(id, name, price, inv, max, min, custom);
        }
        Inventory.addPart(part);
//        Part part = new Outsourced(1, "Outsourced Part Tire", 20.00, 20, 1, 5, "FOX");
//        pr.addAssociatedPart(part);
//        Part part2 = new InHouse(2, "InHouse Part Bell", 10.00, 27, 1, 5, 2);


        System.out.print(ihButton.isSelected());
    }

    @FXML
    protected void onCancel(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/View/main-form.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Inventory Management - Main");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ihButton.setToggleGroup(group);
        osButton.setToggleGroup(group);
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