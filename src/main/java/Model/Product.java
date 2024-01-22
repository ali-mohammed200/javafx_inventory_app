package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Product Class
 */
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
     * Params for the constructor
     *
     * @param id id
     * @param name name
     * @param price price
     * @param stock stock
     * @param min min
     * @param max max
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
     * @param id id
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
     * @param name name
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
     * @param price price
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
     * @param stock stock
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
     * @param min min
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
     * @param max max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * set associated parts
     *
     * @param part part
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * delete associated part
     *
     * @param selectedAssociatedPart selectedAssociatedPart
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
