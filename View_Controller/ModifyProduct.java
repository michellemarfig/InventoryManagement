/**
 * @author Michelle Martinez-Figueroa
 * January 7, 2021
 * Inventory - JavaFX Application
 */

package View_Controller;

import javafx.collections.FXCollections;
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

public class ModifyProduct implements Initializable {
    public TableView<Part> allPartsTable;
    public TableView<Part> associatedTable;
    public TextField idText;
    public TextField stockTxt;
    public TextField maxTxt;
    public TextField nameTxt;
    public TextField minTxt;
    public TextField priceTxt;
    public TextField searchTxt;
    private Product pSelected;
    private ObservableList<Part> tempList = FXCollections.observableArrayList();

    /**
     * method to initialize modify product screen
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setSelection();
        setPartsTable();
        setAssociatedParts();
        disableID();
        populate();
    } // END initialize

    /**
     * method to setup part table
     */
    public void setPartsTable() {
        allPartsTable.setItems(Inventory.getAllParts());
        allPartsTable.refresh();
    } // END setTable method

    /**
     * method to setup associated parts table
     */
    public void setAssociatedParts () {
        tempList.addAll(pSelected.getAllAssociatedParts());

            associatedTable.setItems(pSelected.getAllAssociatedParts());
            associatedTable.refresh();
    } // END setAssociatedParts

    /**
     * method to set the selected product
     */
    public void setSelection() {
        pSelected = Main.productSelection;
    } // END setSelection method


    /**
     * method to populate text fields
     */
    public void populate() {
        System.out.println("Max is " + pSelected.getMax() + "in modify product");
        idText.setText(String.valueOf(pSelected.getId()));
        nameTxt.setText(String.valueOf(pSelected.getName()));
        priceTxt.setText(Double.toString(pSelected.getPrice()));
        stockTxt.setText(Integer.toString(pSelected.getStock()));
        maxTxt.setText(Integer.toString(pSelected.getMax()));
        minTxt.setText(Integer.toString(pSelected.getMin()));
    } // END populate method


    /**
     * method to add associated part
     * @param actionEvent clicking add associated part buttons
     */
    public void addAssociated(ActionEvent actionEvent) {
        if (allPartsTable.selectionModelProperty().get().getSelectedItem() == null) {
            // if no part is selected
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Select a part from upper table first.");
            alert.showAndWait();
            return;
        } // END if nothing is selected

        Part p = allPartsTable.selectionModelProperty().getValue().getSelectedItem();
        pSelected.addAssociatedPart(p);
        associatedTable.setItems(pSelected.getAllAssociatedParts());
        associatedTable.refresh();
    } //END add associated method


    /**
     * method to switch to main screen
     * @param actionEvent clicking cancel button
     * @throws IOException for IO errors if it happens
     */
    public void goToMain(javafx.event.ActionEvent actionEvent) throws IOException {
        //Confirmation
        Alert conf = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to cancel?",
                ButtonType.OK, ButtonType.CANCEL);
        conf.setHeaderText("Confirmation");
        Optional<ButtonType> result = conf.showAndWait();
        if (result.get() == ButtonType.CANCEL) {
            return;
        } // END if

        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/MainController.fxml"));
        Stage stage= (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    } // END go to add part method


    /**
     * method to remove associated part
     * @param actionEvent clicking remove associated parts
     */
    public void removeAssociated(ActionEvent actionEvent) {
        if (associatedTable.selectionModelProperty().get().getSelectedItem() == null) {
            // if no associatedPart is selected
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Select a part from lower table first.");
            alert.showAndWait();
            return;
        } // END if nothing is selected

        Part p = associatedTable.selectionModelProperty().getValue().getSelectedItem();

        //Confirmation
        Alert conf = new Alert(Alert.AlertType.CONFIRMATION,
                            "Are you sure you want to remove this associated part?",
                                ButtonType.OK, ButtonType.CANCEL);
        conf.setHeaderText("Confirmation");
        Optional<ButtonType> result = conf.showAndWait();

        if (result.get() == ButtonType.CANCEL) {
            return;
        } // END if

        pSelected.deleteAssociatedPart(p);
        associatedTable.setItems(pSelected.getAllAssociatedParts());
        associatedTable.refresh();
    } // END removeAssociatedPart method

    /**
     * method to save
     * @param actionEvent clicking save button
     * @throws IOException for IO errors if it happens
     */
    public void onSave(ActionEvent actionEvent) throws IOException {
        String n = nameTxt.getText();
        int stock = Integer.parseInt(stockTxt.getText());
        int ma = Integer.parseInt(maxTxt.getText());
        int mi = Integer.parseInt(minTxt.getText());
        double price = Double.parseDouble(priceTxt.getText());
        int index = findIndex();

        // Validation
        // if name is empty, show dialog box
        if (!nameTxt.getText().isEmpty()) {
            n = nameTxt.getText();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid Entry");
            alert.setContentText("Name field cannot be empty.");
            alert.showAndWait();
            return;
        } // END name validation

        // if stock if empty, show dialog box
        if (stockTxt.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid Entry");
            alert.setContentText("Stock cannot be empty.");
            alert.showAndWait();
            return;

            // if stock is negative, show dialog box
        } else if (!isInteger(stockTxt.getText())
                || hasChar(stockTxt.getText())
                || Integer.parseInt(stockTxt.getText()) < 0 ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid Entry");
            alert.setContentText("Stock must be at least 0.");
            alert.showAndWait();
            return;

            // if stock is a double, show dialog box
        } else if (isDouble(stockTxt.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid Entry");
            alert.setContentText("Stock must be a whole number.");
            alert.showAndWait();
            return;

            // if stock has letters, show dialog box
        } else if (hasChar(stockTxt.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid Entry");
            alert.setContentText("Stock can only be numbers.");
            alert.showAndWait();
            return;
        } else {
            stock = Integer.parseInt(stockTxt.getText());
        } // END stock validation



        // if price is empty, show dialog box
        if (priceTxt.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid Entry");
            alert.setContentText("Price cannot be empty.");
            alert.showAndWait();
            return;

            // if price has letters
        } else if (hasChar(priceTxt.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid Entry");
            alert.setContentText("Price must be at least $0.01.");
            alert.showAndWait();
            return;

            // if not an number
        } else if (!isInteger(priceTxt.getText()) || !isDouble(priceTxt.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid Entry");
            alert.setContentText("Price must be at least $0.01.");
            alert.showAndWait();
            return;

            // if price is less than 0
        } else if (Double.parseDouble(priceTxt.getText()) < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid Entry");
            alert.setContentText("Price must be at least $0.01.");
            alert.showAndWait();
        } else {
            price = Double.parseDouble(priceTxt.getText());
        } // END price validation



        // if min is empty
        if (minTxt.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid Entry");
            alert.setContentText("Minimum cannot be empty.");
            alert.showAndWait();
            return;

            // if not a number
        } else if(!isInteger(minTxt.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid Entry");
            alert.setContentText("Minimum must be at least 0.");
            alert.showAndWait();
            return;

            // if a double
        } else if (isDouble(minTxt.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid Entry");
            alert.setContentText("Minimum must be a whole number.");
            alert.showAndWait();
            return;

            // if negative
        } else if (Integer.parseInt(minTxt.getText()) <0 ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid Entry");
            alert.setContentText("Minimum must be at least 0.");
            alert.showAndWait();
            return;

            // if has letters
        } else if (hasChar(minTxt.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid Entry");
            alert.setContentText("Minimum can only be numbers.");
            alert.showAndWait();
            return;
        } else {
            mi = Integer.parseInt(minTxt.getText());
        } // END min validation



        // if max is empty
        if (maxTxt.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid Entry");
            alert.setContentText("Maximum cannot be empty.");
            alert.showAndWait();
            return;

            // if not a number
        }  else if (!isInteger(maxTxt.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid Entry");
            alert.setContentText("Maximum must be at least 0");
            alert.showAndWait();
            return;

            // if a double
        } else if (isDouble(maxTxt.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid Entry");
            alert.setContentText("Maximum must be a whole number.");
            alert.showAndWait();
            return;

            // if has letters
        } else if (hasChar(maxTxt.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid Entry");
            alert.setContentText("Maximum can only be numbers.");
            alert.showAndWait();
            return;
        } else {
            ma = Integer.parseInt(maxTxt.getText());
        } // END max validation



        // if min is greater than max
        if (mi > ma) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid Entry");
            alert.setContentText("Maximum must be more than minimum.");
            alert.showAndWait();
            return;
        } // END if

        // if stock is not between min and max
        if (!(stock <= ma && stock >= mi)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid Entry");
            alert.setContentText("Stock must be between minimum and maximum.");
            alert.showAndWait();
            return;
        } // ENd if


        pSelected.setName(n);
        pSelected.setStock(stock);
        pSelected.setMax(ma);
        pSelected.setMin(mi);
        pSelected.setPrice(price);

        goToMain(actionEvent);

    } //END onSave method


    /**
     * method to find index of selected item
     * @return index
     */
    private int findIndex() {
        int id = pSelected.getId();
        int index =0;
        int i = 0;

        for (Part p: Inventory.getAllParts()) {
            if (p.getId() == id) {
                index = i;
            } else {
                i++;
            } // ENd if - else
        } // END for
        return index;
    } // NED find index method


    /**
     * method to disable ID text field
     */
    public void disableID() {
        idText.setDisable(true);
    } // END disable ID method


    /**
     * method to complete search for parts
     * @param actionEvent when part search button is pressed
     */
    public void partSearch(javafx.event.ActionEvent actionEvent) {
        String s = searchTxt.getText().toLowerCase().trim();
        ObservableList<Part> results = Inventory.lookupPart(s);

        // if it didn't catch nothing then search by id
        if (results.size() == 0 ) {
            int id = Integer.parseInt(s);
            Part p = Inventory.lookupPart(id);
            if (p != null) {
                results.add(p);
            } // END check if p is null
        } // END if

        // if still empty, show dialog box
        if (results.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("No parts found.");
            alert.setContentText("Try another search.");
            alert.showAndWait();
            searchTxt.clear();
        } else {
            allPartsTable.setItems(results);
        } // END if - else
    } // END getSearchResults method


    /**
     * method to determine is a String can be converted into int
     * @param s string to check
     * @return true if it is an integer
     */
    public boolean isInteger(String s) {
        if (s.contains("0") ||
                s.contains(("1")) ||
                s.contains("2") ||
                s.contains("3") ||
                s.contains("4") ||
                s.contains("5") ||
                s.contains("6") ||
                s.contains("7") ||
                s.contains("8") ||
                s.contains("9") ) {
            return true;
        } // END if
        return false;
    } // ENd isInteger method


    /**
     * method to determine if a string can be converted toa double
     * @param s string to check
     * @return true if it is a double.
     */
    public boolean isDouble(String s) {
        // END if
        return isInteger(s) && s.contains(".");
    } // END isDouble method



    /**
     * method to check whether a string has any characters
     * @param s string to check
     * @return true if it contains a char
     */
    public boolean hasChar (String s) {
        char c = 'a';
        for ( int i = 0; i < 26; i++) {
            if (s.contains(String.valueOf(c))) {
                return true;
            } // END if
            c++;
        } // END for
        return false;
    } // END hasChar method

} // END ModifyProduct class
