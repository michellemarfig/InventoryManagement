/**
 * @author Michelle Martinez-Figueroa
 * January 7, 2021
 * Inventory - JavaFX Application
 */

package model;
import java.lang.String;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    /** Method to add part
     * @param newPart new part to add
     */
    public  static void addPart (Part newPart) {
        allParts.add(newPart);
    } // ENd addPart method

    /**
     * Method to add new Product
     * @param newProduct new product to add
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    } // END add product method

    /**
     * method to lookup part
     * @param partId part id to lookup
     * @return part matching partId
     */
    public static Part lookupPart(int partId) {
        ObservableList<Part> list = getAllParts();
        for (Part p: list) {
            if( p.getId() == partId) {
                return p;
            } // END if
        } // END for
        return  null;

    } // END lookupPart by id method


    /**
     * method to lookup product
     * @param productId product id to lookup
     * @return product matching product id
     */
    public static Product lookupProduct(int productId) {
        ObservableList<Product> list = Inventory.getAllProducts();
        for (Product p: list) {
            if( p.getId() == productId) {
                return p;
            } // END if
        } // END for
        return  null;
    } // END lookupProduct by id class


    /**
     * method to lookup part by name
     * @param s name to lookup
     * @return list of all matching results
     */
    public static ObservableList<Part> lookupPart(String s) {
        ObservableList<Part> results = FXCollections.observableArrayList(); // new arrayList to hold results

        for (Part p: getAllParts()) {
            if ( p.getName().toLowerCase().contains(s)) {
                results.add(p);
                System.out.println("Added: " + p.getName());
            } // END if
        } // END for
        //System.out.println(results.get(0).getName());
        return results;
    } //END lookupPart by name method


    /**
     * method to lookup product by name
     * @param s string to lookup
     * @return list of all matching results
     */
    public static ObservableList<Product> lookupProduct(String s) {
        ObservableList<Product> results = FXCollections.observableArrayList(); // new arrayList to hold results
        for (Product p: getAllProducts()) {
            if ( p.getName().toLowerCase().contains(s)) {
                results.add(p);
            } // END if
        } // END for
        return results;
    } // END lookup part by name method


    /**
     * method to modify part by getting a new part and replacing it at the specified index
     * @param index index of part to modify
     * @param sPart new part to insert at index
     */
    public static void updatePart(int index, Part sPart) {
        //get data from sPart
        int i = sPart.getId();
        String n = sPart.getName();
        double  price = sPart.getPrice();
        int stock = sPart.getStock();
        int ma = sPart.getMax();
        int mi = sPart.getMin();
        int m;
        String cname;


        if (sPart instanceof InHouse) {
            m = ((InHouse) sPart).getMachineId();
            InHouse p = (InHouse) allParts.get(index);
            p.setMachineId(m);
        } else {
            cname = ((Outsourced) sPart).getCompanyName();
            Outsourced p = (Outsourced) allParts.get(index);
            p.setCompanyName(cname);
        } // END if-else

        //set data
        allParts.get(index).setId(i);
        allParts.get(index).setName(n);
        allParts.get(index).setStock(stock);
        allParts.get(index).setPrice(price);
        allParts.get(index).setMin(mi);
        allParts.get(index).setMax(ma);

    } // ENd selectedPart method


    /**
     * method to modify product by getting a new product and replacing it at the specified index
     * @param index index of product to modify
     * @param sProduct product to be inserted at index
     */
    public static void updateProduct(int index, Product sProduct) {
        allProducts.get(index).setId(sProduct.getId());
        allProducts.get(index).setName(sProduct.getName());
        allProducts.get(index).setStock(sProduct.getStock());
        allProducts.get(index).setPrice(sProduct.getPrice());
        allProducts.get(index).setMin(sProduct.getMin());
        allProducts.get(index).setMax(sProduct.getMax());
    } // ENd selectedProduct method

    /**
     * method to delete part
     * @param selectedPart part to delete
     * @return true if part was deleted
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    } // ENd deletePart method


    /**
     * method to delete product
     * @param selectedProduct product to delete
     * @return true if product was deleted
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    } // ENd deleteProduct method


    /**
     * method to get all parts
     * @return list of all parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    } // EMD getAllParts method


    /**
     * method to return all products
     * @return list of all products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    } // END getAllProducts method


} // END class
