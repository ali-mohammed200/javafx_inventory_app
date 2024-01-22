package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Product {
    private final ObservableList<Part> associatedParts = FXCollections.observableList(new ArrayList<Part>());
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Constructor for Product
     *
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * set id
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * set name
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * set price
     *
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * set stock
     *
     * @param stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return min
     */
    public int getMin() {
        return min;
    }

    /**
     * set min
     *
     * @param min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return max
     */
    public int getMax() {
        return max;
    }

    /**
     * set max
     *
     * @param max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * set associated parts
     *
     * @param part
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * delete associated part
     *
     * @param selectedAssociatedPart
     * @return boolean if deleted?
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        return associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * get all associated parts
     *
     * @return associated parts list
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
