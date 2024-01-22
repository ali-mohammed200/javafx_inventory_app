package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Inventory Class
 */
public class Inventory {
    private static final ObservableList<Part> allParts = FXCollections.observableList(new ArrayList<Part>());

    private static final ObservableList<Product> allProducts = FXCollections.observableList(new ArrayList<Product>());

    /**
     * add a part to the inventory
     *
     * @param newPart newPart
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * add a product to the inventory
     *
     * @param newProduct newProduct
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * search for a part
     *
     * @param partId partId
     * @return part
     */
    public static Part lookupPart(int partId) {
        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /**
     * search for a part
     *
     * @param partName partName
     * @return list of parts
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> partsList = FXCollections.observableArrayList(new ArrayList<Part>());

        for (Part part : allParts) {
            if (part.getName().toLowerCase().contains(partName.toLowerCase())) {
                partsList.add(part);
            }
        }
        return partsList;
    }

    /**
     * search for a product
     *
     * @param productId productId
     * @return product
     */
    public static Product lookupProduct(int productId) {
        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**
     * search for a product
     *
     * @param productName productName
     * @return list of products
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> productsList = FXCollections.observableArrayList(new ArrayList<Product>());

        for (Product product : allProducts) {
            if (product.getName().toLowerCase().contains(productName.toLowerCase())) {
                productsList.add(product);
            }
        }
        return productsList;
    }

    /**
     * update a part
     *
     * @param index        index
     * @param selectedPart selectedPart
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * update a product
     *
     * @param index           index
     * @param selectedProduct selectedProduct
     */
    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    /**
     * delete desired part
     *
     * @param selectedPart selectedPart
     * @return boolean if part deleted
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * delete desired product
     *
     * @param selectedProduct selectedProduct
     * @return boolean if deleted
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /**
     * get all parts
     *
     * @return list of all parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * get all products
     *
     * @return list of all products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
