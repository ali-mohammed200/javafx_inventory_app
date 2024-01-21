package Controller;

import Model.*;
import com.c482_inventory_system.c482_pa.MainApplication;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainFormController implements Initializable {
    @FXML
    private Stage stage;
    @FXML
    private AnchorPane mainPane;
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
    private TableView tableProduct;
    @FXML
    private TableColumn cProdId;
    @FXML
    private TableColumn cProdName;
    @FXML
    private TableColumn cProdInvLvl;
    @FXML
    private TableColumn cProdPrice;
    @FXML
    private TextField searchBarProduct;
    @FXML
    private Label productsWarning;
    @FXML
    private Label partsWarning;


    @FXML
    protected void onAddPart(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/View/add-part.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onAddProduct(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/View/add-product.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Add Product");
        stage.setX(0);
        stage.setY(0);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onModifyPart(ActionEvent event) throws IOException {
        Part part = (Part) tablePart.getSelectionModel().getSelectedItem();
        Integer selectedIndex = tablePart.getSelectionModel().getSelectedIndex();
        if (part != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/View/modify-part.fxml"));
            ModifyPartController.setSelectedPart(part);
            ModifyPartController.setPartIndex(selectedIndex);

            Scene scene = new Scene(fxmlLoader.load());
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Modify Part");
//            stage.setX(0);
            stage.setY(0);
            stage.setScene(scene);
            stage.show();
        } else {
            setWarningLabel("No part selected to modify. ", partsWarning);
        }
    }

    @FXML
    protected void onModifyProduct(ActionEvent event) throws IOException {
        Product selectedProduct = (Product) tableProduct.getSelectionModel().getSelectedItem();
        Integer selectedIndex = tableProduct.getSelectionModel().getSelectedIndex();
        if (selectedProduct != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/View/modify-product.fxml"));
            ModifyProductController.setSelectedProduct(selectedProduct);
            ModifyProductController.setProductIndex(selectedIndex);

            Scene scene = new Scene(fxmlLoader.load());
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Modify Product");
            stage.setY(0);
            stage.setScene(scene);
            stage.show();

        } else {
            setWarningLabel("No product selected to modify. ", productsWarning);
        }
    }

    //    Show alert that asks yes or cancel
    @FXML
    protected void onDeletePart(ActionEvent event) {
        Part part = (Part) tablePart.getSelectionModel().getSelectedItem();
        if (part != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Part");
            alert.setHeaderText("Confirm deletion");
            alert.setContentText("Are you sure you want to delete " + part.getName() + "?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                Inventory.deletePart(part);
                tablePart.setItems(Inventory.getAllParts());
            } else {
                setWarningLabel("Not Deleted", partsWarning);
            }
        } else {
            setWarningLabel("No part selected", partsWarning);
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
    protected void onDeleteProduct(ActionEvent event) {
        Product product = (Product) tableProduct.getSelectionModel().getSelectedItem();
        if (product != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Product");
            alert.setHeaderText("Confirm deletion");
            alert.setContentText("Are you sure you want to delete " + product.getName() + "?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                if (product.getAllAssociatedParts().size() == 0) {
                    Inventory.deleteProduct(product);
                    tableProduct.setItems(Inventory.getAllProducts());
                } else {
                    setWarningLabel("Product has associated parts", productsWarning);
                }
            } else {
                setWarningLabel("Not Deleted", productsWarning);
            }
        } else {
            setWarningLabel("No product selected", productsWarning);
        }
    }

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
    protected void onSearchProduct(KeyEvent event) {
        String searchTerm = searchBarProduct.getText();
        boolean isNumeric = searchTerm.chars().allMatch(Character::isDigit);
        if (isNumeric && searchTerm != "") {
            Integer id = Integer.parseInt(searchTerm);
            Product product = Inventory.lookupProduct(id);
            if (product != null) {
                searchTerm = product.getName();
            }
        }
        tableProduct.setItems(Inventory.lookupProduct(searchTerm));
    }


    @FXML
    protected void onExitApplication(ActionEvent event) {
        stage = (Stage) mainPane.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tablePart.setPlaceholder(new Label("No parts to show"));
        tableProduct.setPlaceholder(new Label("No products to show"));

        tablePart.setItems(Inventory.getAllParts());
        tableProduct.setItems(Inventory.getAllProducts());

        cPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        cPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cPartInvLvl.setCellValueFactory(new PropertyValueFactory<>("stock"));
        cPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        cProdId.setCellValueFactory(new PropertyValueFactory<>("id"));
        cProdName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cProdInvLvl.setCellValueFactory(new PropertyValueFactory<>("stock"));
        cProdPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}