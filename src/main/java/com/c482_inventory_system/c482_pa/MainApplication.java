package com.c482_inventory_system.c482_pa;

import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        loadDummyData();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/View/main-form.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 870, 450);
        stage.setTitle("Inventory Management - Main");
        stage.setScene(scene);
        stage.show();
    }

    public void loadDummyData(){
        Product pr = new Product(1, "Bike", 10, 2, 1, 5);
        Product pr2 = new Product(2, "Car", 100, 12, 1, 5);
        Part part = new Outsourced(1, "Outsourced Part Tire", 20, 2, 1, 5, "FOX");
        pr.addAssociatedPart(part);
        Part part2 = new InHouse(2, "InHouse Part Bell", 10, 2, 1, 5, 2);
        pr.addAssociatedPart(part2);
        Inventory.addPart(part);
        Inventory.addPart(part2);
        Inventory.addProduct(pr);
        Inventory.addProduct(pr2);
    }

    public static void main(String[] args) {
        launch();
    }
}