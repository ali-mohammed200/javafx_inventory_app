package Model;

public class Outsourced extends Part {
    private String companyName;

    /**
     * Constructor of Outsourced Part
     *
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * @return company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * set company name
     *
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
