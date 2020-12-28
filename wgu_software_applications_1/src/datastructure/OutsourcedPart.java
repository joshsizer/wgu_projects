package datastructure;

/**
 * Subclass of Part to hold an "Outsourced" part.
 *
 * @author Joshua Sizer
 */
public class OutsourcedPart extends Part {
    /**
     * The company name for this part.
     */
    private String companyName;

    /**
     * Creates an Outsourced part.
     *
     * @param id The unique numerical ID for this part
     * @param name The name of this part
     * @param price The price of this part
     * @param stock The inventory level of this part
     * @param min The minimum allowable stock of this part
     * @param max The maximum allowable stock of this part
     * @param companyName The company name of this part.
     */
    public OutsourcedPart(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Sets this part's company name.
     *
     * @param companyName The updated company name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     *
     * @return this part's company name
     */
    public String getCompanyName() {
        return this.companyName;
    }
}
