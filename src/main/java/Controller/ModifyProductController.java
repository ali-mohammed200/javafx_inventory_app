package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
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
import java.util.ResourceBundle;

public class ModifyProductController implements Initializable {
    private static Product selectedProduct;
    private static Integer productIndex = 0;
    private final ObservableList<Part> tempAssociatedParts = FXCollections.observableList(new ArrayList<Part>(selectedProduct.getAllAssociatedParts()));
    @FXML
    private Stage stage;
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
    private Label warningLabel;
    @FXML
    private Label partsWarning;
    @FXML
    private Label assocPartsWarning;

    /**
     * set selected product
     *
     * @param selectedProduct
     */
    public static void setSelectedProduct(Product selectedProduct) {
        ModifyProductController.selectedProduct = selectedProduct;
    }

    /**
     * set product index
     *
     * @param productIndex
     */
    public static void setProductIndex(Integer productIndex) {
        ModifyProductController.productIndex = productIndex;
    }

    /**
     * search for a part in the inventory
     *
     * @param event
     */
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

    /**
     * Event handler for saving a modified product
     *
     * @param event
     * @throws IOException
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

        int id = selectedProduct.getId();

        Product product = null;
//        Creating a new product every time which I should not do. The loop will need to be modified as well
        product = new Product(id, name, price, inv, min, max);

        if (warning.length() > 0) {
            warningLabel.setText("Exception:\n" + warning);
        } else {
            for (Part part : tempAssociatedParts) {
                product.addAssociatedPart(part);
            }
            Inventory.updateProduct(productIndex, product);
            onCancel(event); //close add products window
        }
    }

    /**
     * Event Handler for adding an associated part
     *
     * @param event
     */
    @FXML
    protected void onAddAssociatedPart(ActionEvent event) {
        Part part = (Part) tablePart.getSelectionModel().getSelectedItem();
        if (part != null) {
            tempAssociatedParts.add(part);
        } else {
            setWarningLabel("No part selected", partsWarning);
        }
    }

    /**
     * Event Handler for removing an associated part
     *
     * @param event
     */
    @FXML
    protected void onRemoveAssociatedPart(ActionEvent event) {
        Part part = (Part) tableAssocPart.getSelectionModel().getSelectedItem();
        if (part != null) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remove Associated Part");
            alert.setHeaderText("Confirm deletion");
            alert.setContentText("Are you sure you want to delete " + part.getName() + "?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                tempAssociatedParts.remove(part);
            } else {
                setWarningLabel("Not Deleted", assocPartsWarning);
            }
        } else {
            setWarningLabel("No associated part selected", assocPartsWarning);
        }
    }

    /**
     * sets a warning animation on a label
     * fades after 2 seconds
     *
     * @param warning
     * @param label
     */
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

    /**
     * Event Handler for canceling product modification
     *
     * @param event
     * @throws IOException
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
     * sets data for the tables and inputs on the screen
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameInput.setText(selectedProduct.getName());
        invInput.setText(Integer.toString(selectedProduct.getStock()));
        priceInput.setText(Double.toString(selectedProduct.getPrice()));
        maxInput.setText(Integer.toString(selectedProduct.getMax()));
        minInput.setText(Integer.toString(selectedProduct.getMin()));

        tablePart.setPlaceholder(new Label("No parts to show"));
        tableAssocPart.setPlaceholder(new Label("No associated parts to show"));

        tablePart.setItems(Inventory.getAllParts());
        tableAssocPart.setItems(tempAssociatedParts);

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