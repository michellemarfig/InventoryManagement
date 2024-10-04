/**
 * @author Michelle Martinez-Figueroa
 * January 7, 2021
 * Inventory - JavaFX Application
 */
package model;


public class InHouse extends Part {
    private int machineId;

    /**
     * Constructor
     * @param id id to set
     * @param name name to set
     * @param price price to set
     * @param stock current stock
     * @param min minimum to keep in stock
     * @param max maximum to keep in stock
     * @param m machine id to set
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int m) {
        super(id, name, price, stock, min, max);
        setMachineId(m);

    } // END constructor


    /**
     * mutator to set machine id
     * @param m machine id to set
     */
    public void setMachineId(int m) {
        machineId = m;
    } // END setMachineId mutator


    /**
     * Accessor to get machine id
     * @return machine id
     */
    public int getMachineId(){
        return machineId;
    } // END getMachineId accessor
    
} // END InHouse Class
