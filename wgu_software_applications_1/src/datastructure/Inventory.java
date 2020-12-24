package datastructure;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static int nextPartID = 0;
    private static int nextProductID = 0;

    public static int getNextPartID() {
        return nextPartID;
    }

    public static int getNextProductID() {
        return nextProductID;
    }

    public static void addPart(Part newPart) {
        allParts.add(newPart);
        nextPartID++;
    }

    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
        nextProductID++;
    }

    public static Part lookupPart(int partId) {
        return null;
    }

    public static Product lookupProduct(int productId) {
        return null;
    }

    public static ObservableList<Part> lookupPart(String partName) {
        return null;
    }

    public static ObservableList<Product> lookupProduct(String productName) {
        return null;
    }

    public static void updatePart(int index, Part selectedPart) {

    }

    public static void updateProduct(int index, Product newProduct) {

    }

    public static boolean deletePart(Part selectedPart) {
        return false;
    }

    public static boolean deleteProduct(Product selectedProduct) {
        return false;
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
