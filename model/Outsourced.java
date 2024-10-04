/**
 * @author Michelle Martinez-Figueroa
 * January 7, 2021
 * Inventory - JavaFX Application
 */
package model;
public class Outsourced extends Part{
    private String companyName;


    /**
     * constructor
     * @param id id to set
     * @param name name to set
     * @param price price to set
     * @param stock current stock
     * @param min minimum to keep in stock
     * @param max maximum to keep in stock
     * @param c company name to set
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String c) {
        super(id, name, price,stock, min, max); //Calls superclass constructor
        setCompanyName(c);
    } // END Constructor


    /**
     * mutator to set company name
     * @param c company name to set
     */
    public void setCompanyName(String c) {
        companyName = c;
    } // END setCompanyName mutator


    /**
     * Accessor to get company name
     * @return company name
     */
    public String getCompanyName() {
        return companyName;
    } // END getCompanyName accessor
    
    
} // END Outsourced class
