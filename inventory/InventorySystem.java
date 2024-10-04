/**
 * @author Michelle Martinez-Figueroa
 * January 7, 2021
 * Inventory - JavaFX Application
 */

package inventory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;
public class InventorySystem extends Application {


    /**
     * main method to launch application
     */
    public static void main(String[] args) {
        launch(args);
    } // END main method


    /**
     * method that sets up test data
     * @param primaryStage to show
     * @throws Exception if it happens
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
                setTestData();


        //Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/MainController.fxml"));

        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/MainController.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    } // END start method



    /**
     * method to set test Data for tableViews
     */
    public void setTestData() {
        Part t1 = new InHouse(100001, "Shirt", 3.50, 7, 5, 20, 80000458);
        Inventory.addPart(t1);
        Part t2 = new InHouse(100002, "Pants", 7.99, 4, 3, 10, 80000612);
        Inventory.addPart(t2);
        Part t3 = new InHouse(100003, "Socks", 2.99, 5, 3, 10, 80000615);
        Inventory.addPart(t3);
        Part t4 = new Outsourced(100004, " HT Paper", 0.99, 25, 20, 250, "Neenah");
        Inventory.addPart(t4);
        Part t5 = new Outsourced(100005, "Vinyl", 2.33, 10, 5, 50, "Siser");
        Inventory.addPart(t5);
        Part t6 = new Outsourced(100006, "Sticker Paper", 0.10, 15, 7, 50, "Online Labels");
        Inventory.addPart(t6);

        Product t7 = new Product(900001, "Custom T-Shirt", 15.00, 25, 10, 30);
        t7.addAssociatedPart(t1);
        t7.addAssociatedPart(t5);
        Inventory.addProduct(t7);
        Product t8 = new Product(900002, "Custom Socks", 12.00, 5, 2, 20);
        t8.addAssociatedPart(t3);
        t8.addAssociatedPart(t4);
        Inventory.addProduct(t8);
        Product t9 = new Product(900003, "Custom Stickers", 4.50, 5, 2, 15);
        t9.addAssociatedPart(t6);
        Inventory.addProduct(t9);


    } // END setTestData method



} // ENd InventorySystem class
