package datastructure;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Locale;

/**
 * A static class for keeping track of parts
 * and products.
 *
 * updateProduct contains a runtime error that was corrected.
 *
 * @author Joshua Sizer
 */
public class Inventory {

    /**
     * The global list of parts
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * The global list of products
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * The next available, unused part ID
     */
    private static int nextPartID = 0;

    /**
     * The next available, unused product ID
     */
    private static int nextProductID = 0;

    /**
     * Get the next available, unused part ID
     * @return the next available, unused part ID
     */
    public static int getNextPartID() {
        return nextPartID;
    }

    /**
     * Get the next available, unused product ID
     * @return the next available, unused product ID
     */
    public static int getNextProductID() {
        return nextProductID;
    }

    /**
     * Add a part to the inventory.
     *
     * @param newPart The part to add.
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
        nextPartID++;
    }

    /**
     * Add a product to the inventory.
     *
     * @param newProduct The product to add.
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
        nextProductID++;
    }

    /**
     * Lookup a part using its ID
     *
     * @param partId The part's ID
     * @return The part with the corresponding partID, or null if no parts are found
     */
    public static Part lookupPart(int partId) {
        Part selectedPart = null;
        for (Part part : allParts) {
            if (part.getId() == partId) selectedPart = part;
        }
        return selectedPart;
    }

    /**
     * Lookup a product using its ID
     *
     * @param productId The product's ID
     * @return The product with the corresponding productID, or null if no parts are found
     */
    public static Product lookupProduct(int productId) {
        Product selectedProduct = null;
        for (Product product : allProducts) {
            if (product.getId() == productId) selectedProduct = product;
        }
        return selectedProduct;
    }

    /**
     * Lookup a part (or parts) using a partial string
     *
     * @param partName The partial string to search on. Can be an ID as well.
     * @return The part (or parts) matching the given partName string.
     */
    public static ObservableList<Part> lookupPart(String partName) {
        partName = partName.toLowerCase();
        ObservableList<Part> toRet = FXCollections.observableArrayList();
        try {
            int id = Integer.parseInt(partName);
            Part lookedUp = lookupPart(id);
            if (lookedUp != null) {
                toRet.add(lookedUp);
            }
        } catch (NumberFormatException e) {

        }

        for (Part part : allParts) {
            String name = part.getName();
            if (name.toLowerCase().contains(partName)) {
                toRet.add(part);
            }
        }
        return toRet;
    }

    /**
     * Lookup a product (or products) using a partial string
     *
     * @param productName The partial string to search on. Can be an ID as well.
     * @return The product (or products) matching the given productName string.
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        productName = productName.toLowerCase();
        ObservableList<Product> toRet = FXCollections.observableArrayList();
        try {
            int id = Integer.parseInt(productName);
            Product lookedUp = lookupProduct(id);
            if (lookedUp != null) {
                toRet.add(lookedUp);
            }
        } catch (NumberFormatException e) {

        }

        for (Product product : allProducts) {
            String name = product.getName();
            if (name.toLowerCase().contains(productName)) {
                toRet.add(product);
            }
        }
        return toRet;
    }

    /**
     * Update a part.
     *
     * @param id The id of the part to update
     * @param selectedPart The part used to update the part found from the ID
     */
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

    /**
     * Update a product.
     *
     * @param id The id of the product to update
     * @param newProduct The product used to update the product found from the ID
     */
    public static void updateProduct(int id, Product newProduct) {
        Product currentProduct = lookupProduct(id);
        currentProduct.setName(newProduct.getName());
        currentProduct.setStock(newProduct.getStock());
        currentProduct.setPrice(newProduct.getPrice());
        currentProduct.setMin(newProduct.getMin());
        currentProduct.setMax(newProduct.getMax());

        ObservableList<Part> currentProductPartsToRemove = FXCollections.observableArrayList();

        /*
         * Originally had the code:
         * for (Part part : currentProduct.getAllAssociatedParts()) {
         *      currentProduct.deleteAssociatedPart(part);
         * }
         *
         * but this led to ConcurrentModificationException.
         * This is because you cannot modify a collection while
         * it is being iterated over since this leads to inconsistencies.
         *
         * To fix this, I created a duplicate list to gather up all parts,
         * and then remove the parts from the currentProduct using
         * the duplicate list.
         */
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

    /**
     * Deletes a part from the inventory.
     *
     * @param selectedPart The part to delete
     * @return True if the part was deleted, false otherwise
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * Deletes a product from the inventory
     *
     * @param selectedProduct The product to delete
     * @return True if the product was deleted, false otherwise
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /**
     *
     * @return A list of all parts in the inventory.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     *
     * @return A list of all products in the inventory.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
