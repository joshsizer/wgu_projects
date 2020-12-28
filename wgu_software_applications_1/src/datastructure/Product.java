package datastructure;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A product which can hold associated parts.
 *
 * @author Joshua Sizer
 */
public class Product {
    /**
     * A list of parts associated with this product.
     */
    private ObservableList<Part> associatedParts;

    /**
     * This product's ID
     */
    private int id;

    /**
     * This product's name
     */
    private String name;

    /**
     * This product's price
     */
    private double price;

    /**
     * This product's stock
     */
    private int stock;

    /**
     * This product's minimum allowable quantity.
     */
    private int min;

    /**
     * This product's maximum allowable quantity.
     */
    private int max;

    /**
     * Creates a product.
     *
     * @param id The unique numerical ID for this product
     * @param name The name of this product
     * @param price The price of this product
     * @param stock The inventory level of this product
     * @param min The minimum allowable stock of this product
     * @param max The maximum allowable stock of this product
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        associatedParts = FXCollections.observableArrayList();
    }

    /**
     *
     * @return This product's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Set this product's ID
     * @param id The ID to set for this product.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return This product's name
     */
    public String getName() {
        return name;
    }

    /**
     * Set this product's name.
     * @param name The name to set for this product.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return This product's price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Set this product's price.
     * @param price The price to set for this product.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     *
     * @return This product's inventory level.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Set this product's inventory level.
     * @param stock The stock to set for this product.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     *
     * @return This product's minimum allowable inventory level.
     */
    public int getMin() {
        return min;
    }

    /**
     * Set this product's minimum allowable inventory level.
     * @param min The minimum allowable inventory level for this product.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     *
     * @return This product's maximum allowable inventory level.
     */
    public int getMax() {
        return max;
    }

    /**
     * Set this product's maximum allowable inventory level.
     * @param max The maximum allowable inventory level for this product.
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Adds an associated part to this product.
     * @param part The part to add.
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Delete an associated part from this product.
     * @param selectedAssociatedPart The part to delete.
     * @return True if the part is deleted, false otherwise.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        return associatedParts.remove(selectedAssociatedPart);
    }

    /**
     *
     * @return the list of associated parts for this product.
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return this.associatedParts;
    }
}
