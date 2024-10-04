/**
 * @author Michelle Martinez-Figueroa
 * January 7, 2021
 * Inventory - JavaFX Application
 */

package View_Controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.lang.String;


public class Main implements Initializable {
    public TableView<Part> partTable;
    public TableView<Product> productTable;
    public TextField mainPartSearchTxt;
    public TextField mainProductSearchTxt;
    public static Part partSelection;
    public static Product productSelection;


    /**
     * method to initialize the application
     * @param url url
     * @param resourceBundle resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updatePartTable(Inventory.getAllParts());
        updateProductTable(Inventory.getAllProducts());
    } // END initialize method


    /**
     * method to update Part Table
     */
    public void updatePartTable(ObservableList<Part> list) {
        partTable.setItems(list);
        partTable.refresh();
    } // END updatePartTable method


    /**
     * method to update product Table
     */
    public void updateProductTable(ObservableList<Product> list) {
        productTable.setItems(list);
        productTable.refresh();
    } // END updatePartTable method


    /**
     * method to switch to Add Part screen
     * @param actionEvent clicking Add Part button
     * @throws IOException for IO errors if it happens
     */
    public void goToAddPart(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/AddPartController.fxml"));
        Stage stage= (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    } // END go to add part method


    /**
     * method to switch to modify screen
     * @param actionEvent button click
     * @throws IOException for IO errors if it happens
     */
    public void goToModifyPart (javafx.event.ActionEvent actionEvent) throws IOException {
        //if part is selected go to modify part screen
        if (partTable.getSelectionModel().getSelectedItem() != null) {

            partSelection = partTable.getSelectionModel().getSelectedItem();

            //change screen
            Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/ModifyPartController.fxml"));
            Stage stage= (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();


        } else {
            //else show alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No part selected");
            alert.setContentText("Please select a part first.");
            alert.showAndWait();
        }// END if - else
    } // END go to modify part method
//

    /**
     * method to switch to add product screen
     * @param actionEvent clicking product add button
     * @throws IOException for IO errors
     */
    public void goToAddProduct (javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/AddProductController.fxml"));
        Stage stage= (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    } // END go to modify part method


    /**
     * method to switch to modify product screen
     * @param actionEvent clicking product modify button
     * @throws IOException for IO errors
     */
    public void goToModifyProduct (javafx.event.ActionEvent actionEvent) throws IOException {
        //if part is selected
        if (productTable.getSelectionModel().getSelectedItem() != null) {
            productSelection = productTable.getSelectionModel().getSelectedItem();
            System.out.println("Max is " + productSelection.getMax() + "in main");

            Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/ModifyProductController.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Modify Product");
            stage.setScene(scene);
            stage.show();
        } else {
            //else show alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No product selected");
            alert.setContentText("Please select a product.");
            alert.showAndWait();
        }
    } // END go to modify part method


    /**
     * method to complete search for parts
     * @param actionEvent when part search button is pressed
     */
    public void partSearch(javafx.event.ActionEvent actionEvent) {
        String s = mainPartSearchTxt.getText().toLowerCase().trim();
        ObservableList<Part> results = Inventory.lookupPart(s);

        // if it didn't catch nothing then search by id
        if (results.size() == 0) {
            int id = Integer.parseInt(s);
            Part p = Inventory.lookupPart(id);
            if (p != null) {
                results.add(p);
            } // END check if p is null
        } // END if empty results

        // if still empty, show dialog box
        if (results.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("No parts found.");
            alert.setContentText("Try another search.");
            alert.showAndWait();
            mainPartSearchTxt.clear();
        } else {
            partTable.setItems(results);
        } // END if - else
    } // END getSearchResults method




    /**
     * method to complete search for products
     * @param actionEvent when product search button is clicked
     */
    public void productSearch(javafx.event.ActionEvent actionEvent) {
        String s = mainProductSearchTxt.getText().toLowerCase().trim();
        ObservableList<Product> results = Inventory.lookupProduct(s);

        // if it didn't catch nothing then search by id
        if (results.size() == 0) {
            int id = Integer.parseInt(s);
            Product p = Inventory.lookupProduct(id);
            if (p != null) {
                results.add(p);
            } // END check if p is null
        } // END if empty results

        // if still empty, show dialog box
        if (results.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("No products found.");
            alert.setContentText("Try another search.");
            alert.showAndWait();
            mainProductSearchTxt.clear();
        } else {
            productTable.setItems(results);
        } // END if - else
    } // END productSearch




    /**
     * method to exit program
     * @param actionEvent clicking exit button
     */
    public void exit(javafx.event.ActionEvent actionEvent) {
        //Confirmation
        Alert conf = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to exit?",
                ButtonType.OK, ButtonType.CANCEL);
        conf.setHeaderText("Confirmation");
        Optional<ButtonType> result = conf.showAndWait();
        if (result.get() == ButtonType.CANCEL) {
            return;
        } // END if

        Platform.exit();
    } // END exit method


    /**
     * method to delete a part
     * @param actionEvent clicking the delete button in parts pane
     */
    public void partDelete(ActionEvent actionEvent) {
        // if not part is selected
        if (partTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error.");
            alert.setContentText("Please select a part first.");
            alert.showAndWait();
            return;
        } // END if

        Part selectedItem = partTable.getSelectionModel().getSelectedItem();

        //Confirmation
        Alert conf = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to delete this part?",
                        ButtonType.OK, ButtonType.CANCEL);
        conf.setHeaderText("Confirmation");
        Optional<ButtonType> result = conf.showAndWait();
        if (result.get() == ButtonType.CANCEL) {
            return;
        } // END if

        boolean b = Inventory.deletePart(selectedItem);

        //if not deleted
        if (!b) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error.");
            alert.setContentText("Part was not deleted. Please try again.");
            alert.showAndWait();
            return;
        } // END if dialog box
        partTable.setItems(Inventory.getAllParts());
    } // END partDelete method


    /**
     * method to delete product
     * @param actionEvent clicking the delete button in products pane
     */
    public void productDelete(ActionEvent actionEvent) {
        // nothing is selected
        if (productTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error.");
            alert.setContentText("Please select a product first.");
            alert.showAndWait();
            return;
        } // END if

        Product selectedItem = productTable.getSelectionModel().getSelectedItem();

        //Confirmation
        Alert conf = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to delete this product?",
                        ButtonType.OK, ButtonType.CANCEL);
        conf.setHeaderText("Confirmation");
        Optional<ButtonType> result = conf.showAndWait();
        if (result.get() == ButtonType.CANCEL) {
            return;
        } // END if

        boolean b;


        //if product doesn't contain associated parts, then delete
        if (selectedItem.getAllAssociatedParts().size() == 0) {
            b = Inventory.deleteProduct(selectedItem);
            productTable.setItems(Inventory.getAllProducts());

        } else {    // if it does, show dialog box
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error.");
            alert.setContentText("This product has parts associated to it. Please remove them and try again.");
            alert.showAndWait();
        } // END if dialog box
    } // END productDelete method

} //END MainController class

