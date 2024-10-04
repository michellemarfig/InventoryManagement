/**
 * @author Michelle Martinez-Figueroa
 * January 7, 2021
 * Inventory - JavaFX Application
 */

package model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product{
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;


    /** Constructor
     * @param i id
     * @param n name
     * @param p price
     * @param s stock
     * @param mi minimum
     * @param ma maximum
     */
    public Product(int i, String n, double p, int s, int mi, int ma) {
        setId(i);
        setName(n);
        setPrice(p);
        setStock(s);
        setMin(mi);
        setMax(ma);
    } // END Product Constructor


    
    /** Mutator to set id
    * @param i id to set
    */
    public void setId(int i){
        id = i;
    } // END setId Mutator
    
    
    
    /** Mutator to set product name
    * @param n name to set
     */
    public void setName(String n){
        name = n;
    } // END setName Mutator
    
    
    
    /** Mutator to set product price
    * @param p price to set
    */
    public void setPrice(double p){
        price = p;
    } // END setPrice Mutator
    
    
    /** Mutator to set stock quantity
    * @param s stock to set
    */
    public void setStock(int s){
        stock = s;
    } // END setStock Mutator
        
    
    /** Mutator to set minimum quantity
    * @param mi minimum to set
     */
    public void setMin(int mi){
        min = mi;
    } // END setMin Mutator
    
    
    /** Mutator to set maximum quantity
    * @param ma maximum to set
     */
    public void setMax(int ma){
        max = ma;
    } // END setMin Mutator

    
    
    
    
    
    
    /**Accessor to get product id
    * @return id
     */
    public int getId(){
        return id;
    } // END getId Accessor
    
    
    
    /** Accessor to get product name
    * @return name
    */
    public String getName(){
        return name;
    } // END getName Accessor
    
    
    
    /** Accessor to get product price
    * @return price
     */
    public double getPrice(){
        return price;
    } // END getPrice Accessor
    
    
    /** Accessor to get product stock
    * @return stock
     */
    public int getStock(){
        return stock;
    } // END getStock Accessor
        
    
    /** Accessor to get minimum
    * @return minimum
    */
    public int getMin(){
        return min;
    } // END getMin Accessor
    
    
    /** Accessor to get maximum
    * @return maximum
     */
    public int getMax(){
        return max;
    } // END getId Accessor
    
    
    
    /** method to add part to associatedParts
    * @param part part to add
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    } // END addAssociatedPart method
    
    
    
    /** method to delete a part in associatedParts
    * @param selectedAssociatedPart part to delete
    * @return boolean true if Part object was deleted
    */
    public boolean deleteAssociatedPart (Part selectedAssociatedPart) {
        return associatedParts.remove(selectedAssociatedPart);
    } // END deleteAssociatedPart method
    
    
    
    
    /** method to return all associatedParts
    * @return ObservableList - containing all Parts
    */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    } // END getAllAssociatedParts method



} // END class




