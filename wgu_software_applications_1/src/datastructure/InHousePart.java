package datastructure;

/**
 * Subclass of Part to hold an "In-House" part.
 * Has an extra field to hold the Machine ID.
 *
 * @author Joshua Sizer
 */
public class InHousePart extends Part {

    /**
     * The Machine ID for this part.
     */
    private int machineId;

    /**
     * Creates an In-House part.
     *
     * @param id The unique numerical ID for this part
     * @param name The name of this part
     * @param price The price of this part
     * @param stock The inventory level of this part
     * @param min The minimum allowable stock of this part
     * @param max The maximum allowable stock of this part
     * @param machineId The Machine ID of this part.
     */
    public InHousePart(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Set this part's Machine ID.
     *
     * @param machineId the new Machine ID.
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * Get this part's Machine ID
     *
     * @return
     */
    public int getMachineId() {
        return this.machineId;
    }
}
