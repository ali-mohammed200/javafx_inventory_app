package Model;

/**
 * Outsourced Part Class
 */
public class Outsourced extends Part {
    private String companyName;

    /**
     * Constructor of Outsourced Part
     * params for constructor
     *
     * @param id          id param
     * @param name        name param
     * @param price       price param
     * @param stock       stock param
     * @param min         min param
     * @param max         max param
     * @param companyName companyName param
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
     * @param companyName param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
