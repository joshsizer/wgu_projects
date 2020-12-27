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
        Part selectedPart = null;
        for (Part part : allParts) {
            if (part.getId() == partId) selectedPart = part;
        }
        return selectedPart;
    }

    public static Product lookupProduct(int productId) {
        Product selectedProduct = null;
        for (Product product : allProducts) {
            if (product.getId() == productId) selectedProduct = product;
        }
        return selectedProduct;
    }

    public static ObservableList<Part> lookupPart(String partName) {
        return null;
    }

    public static ObservableList<Product> lookupProduct(String productName) {
        return null;
    }

    public static void updatePart(int id, Part selectedPart) {
        Part currentPart = lookupPart(id);
        if (currentPart.getClass().equals(selectedPart.getClass())) {
            currentPart.setName(selectedPart.getName());
            currentPart.setPrice(selectedPart.getPrice());
            currentPart.setStock(selectedPart.getStock());
            currentPart.setMin(selectedPart.getMin());
            currentPart.setMax(selectedPart.getMax());
            if (currentPart instanceof InHousePart) {
                ((InHousePart)currentPart).setMachineId(((InHousePart)selectedPart).getMachineId());
            } else {
                ((OutsourcedPart)currentPart).setCompanyName(((OutsourcedPart)selectedPart).getCompanyName());
            }
        } else {
            deletePart(currentPart);
            allParts.add(selectedPart);
        }
    }

    public static void updateProduct(int id, Product newProduct) {
        Product currentProduct = lookupProduct(id);
        currentProduct.setName(newProduct.getName());
        currentProduct.setStock(newProduct.getStock());
        currentProduct.setPrice(newProduct.getPrice());
        currentProduct.setMin(newProduct.getMin());
        currentProduct.setMax(newProduct.getMax());

        ObservableList<Part> currentProductPartsToRemove = FXCollections.observableArrayList();

        for (Part part : currentProduct.getAllAssociatedParts()) {
            currentProductPartsToRemove.add(part);
        }

        for (Part part : currentProductPartsToRemove) {
            currentProduct.deleteAssociatedPart(part);
        }

        for (Part part : newProduct.getAllAssociatedParts()) {
            currentProduct.addAssociatedPart(part);
        }
    }

    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
