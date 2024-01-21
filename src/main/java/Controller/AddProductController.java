package Controller;

import Model.*;
import com.c482_inventory_system.c482_pa.MainApplication;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {
    private final ObservableList<Part> associatedParts = FXCollections.observableList(new ArrayList<Part>());
    @FXML
    private Stage stage;
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
    private TextField minInput;
    @FXML
    private TableView tablePart;
    @FXML
    private TableColumn cPartId;
    @FXML
    private TableColumn cPartName;
    @FXML
    private TableColumn cPartInvLvl;
    @FXML
    private TableColumn cPartPrice;
    @FXML
    private Button addPartButton;
    @FXML
    private TextField searchBarPart;
    @FXML
    private TableView tableAssocPart;
    @FXML
    private TableColumn aPartId;
    @FXML
    private TableColumn aPartName;
    @FXML
    private TableColumn aPartInvLvl;
    @FXML
    private TableColumn aPartPrice;
    @FXML
    private Button removeAssocPart;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label warningLabel;
    @FXML
    private Label partsWarning;
    @FXML
    private Label assocPartsWarning;

    @FXML
    protected void onSearchPart(KeyEvent event) {
        String searchTerm = searchBarPart.getText();
        boolean isNumeric = searchTerm.chars().allMatch(Character::isDigit);
        if (isNumeric && searchTerm != "") {
            Integer id = Integer.parseInt(searchTerm);
            Part part = Inventory.lookupPart(id);
            if (part != null) {
                searchTerm = part.getName();
            }
        }
        tablePart.setItems(Inventory.lookupPart(searchTerm));
    }

    @FXML
    protected void onSave(ActionEvent event) throws IOException {
        System.out.println("save");

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

        int id = new Random().nextInt(1000);

        Product product = null;
        product = new Product(id, name, price, inv, min, max);

        if (warning.length() > 0) {
            warningLabel.setText("Exception:\n" + warning);
        } else {
            for (Part part : associatedParts) {
                product.addAssociatedPart(part);
            }
            Inventory.addProduct(product);
            onCancel(event); //close add parts window
        }
    }

    @FXML
    protected void onAddAssociatedPart(ActionEvent event) {
        System.out.println("add");
        Part part = (Part) tablePart.getSelectionModel().getSelectedItem();
        if (part != null) {
            associatedParts.add(part);
        } else {
            setWarningLabel("No part selected", partsWarning);
        }
    }

    @FXML
    protected void onRemoveAssociatedPart(ActionEvent event) {
        System.out.println("remove");
        Part part = (Part) tableAssocPart.getSelectionModel().getSelectedItem();
        if (part != null) {
            associatedParts.remove(part);
        } else {
            setWarningLabel("No associated part selected", assocPartsWarning);
        }
    }

    @FXML
    protected void setWarningLabel(String warning, Label label) {
        label.setText(warning);
        Animation animation = new Transition() {
            {
                setCycleDuration(Duration.millis(2000));
            }

            protected void interpolate(double frac) {
                if (((int) ((float) frac * 100)) == 100) {
                    label.setText("");
                } else {
                    label.setText(warning);
                }
            }

        };
        animation.play();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tablePart.setPlaceholder(new Label("No parts to show"));
        tablePart.setItems(Inventory.getAllParts());
        tableAssocPart.setItems(associatedParts);

        cPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        cPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cPartInvLvl.setCellValueFactory(new PropertyValueFactory<>("stock"));
        cPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        aPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        aPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        aPartInvLvl.setCellValueFactory(new PropertyValueFactory<>("stock"));
        aPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}