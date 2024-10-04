/**
 * @author Michelle Martinez-Figueroa
 * January 7, 2021
 * Inventory - JavaFX Application
 */

package View_Controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddPart implements Initializable {
    public RadioButton inHouseRadio;
    public RadioButton outsourcedRadio;
    public Label changingLbl;
    public TextField changingTxt;
    public TextField idTxt;
    public TextField nameTxt;
    public TextField stockTxt;
    public TextField priceTxt;
    public TextField maxTxt;
    public TextField minTxt;

    /**
     * method to initialize add part screen
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        disableID();
        autoGenerateID();
    } // END  initialize method


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
     * method triggered when radio buttons are selected
     * @param actionEvent selecting either radio buttons
     */
    public void onToggle(javafx.event.ActionEvent actionEvent) {
        if (inHouseRadio.isSelected()) {
            onInHouse(actionEvent);
        } else {
            onOutsourced(actionEvent);
        } // END if - else
    } // END onToggle method


    /**
     * method to change label to machineId
     * @param actionEvent selecting in house button
     */
    public void onInHouse(javafx.event.ActionEvent actionEvent) {
        changingLbl.setText("Machine ID");
    } // END onInHouse method


    /**
     * method to change label to Company Name
     * @param actionEvent selecting outsourced button
     */
    public void onOutsourced(javafx.event.ActionEvent actionEvent) {
        changingLbl.setText("Company Name");
    } // END onOutsourced Method


    /**
     * method to save part
     * @param actionEvent clicking save button
     */
    public void onSave(javafx.event.ActionEvent actionEvent) throws IOException {
        //Create variables
        int i =0 , s = 0, mi = 0, ma = 0, machId = 0;
        String n = " ", cname = " ";
        double p = 0.00;
        boolean inHouseCheck = true;

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
            s = Integer.parseInt(stockTxt.getText());
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
            p = Double.parseDouble(priceTxt.getText());
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
        if (!(s <= ma && s >= mi)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid Entry");
            alert.setContentText("Stock must be between minimum and maximum.");
            alert.showAndWait();
            return;
        } // ENd if

        //more validation and check which box is selected
        if (!changingTxt.getText().isEmpty()) {

            // if in house part
            if (inHouseRadio.isSelected()) {

               if (isInteger(changingTxt.getText())
                        && Integer.parseInt(changingTxt.getText()) > 0) {

                        // if is double
                       if ( isDouble(changingTxt.getText()) ) {
                           Alert alert = new Alert(Alert.AlertType.ERROR);
                           alert.setHeaderText("Invalid Entry");
                           alert.setContentText("Machine ID must be a whole number.");
                           alert.showAndWait();
                           return;

                       // if has letters
                       } else if (hasChar(changingTxt.getText())) {
                           Alert alert = new Alert(Alert.AlertType.ERROR);
                           alert.setHeaderText("Invalid Entry");
                           alert.setContentText("Machine ID can only be numbers.");
                           alert.showAndWait();
                           return;
                       } // END changing txt validation for inHouse


                   machId = Integer.parseInt(minTxt.getText());
               } else {
                   // if machine ID empty
                   Alert alert = new Alert(Alert.AlertType.ERROR);
                   alert.setHeaderText("Invalid Entry");
                   alert.setContentText("Machine ID must be more than 0.");
                   alert.showAndWait();
                   return;
               } // End validation for inHouse

            } else {

                //If Outsourced Part
                inHouseCheck = false;

                //More Validation
                if (!changingTxt.getText().isEmpty()) {
                    cname = changingTxt.getText();
                } else {
                    // if company name empty
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Invalid Entry");
                    alert.setContentText("Please enter company name.");
                    alert.showAndWait();
                    return;
                } // END Outsourced Validation

            } // END if - else
        } else {
            if (inHouseRadio.isSelected()) {
                // if machine id is empty
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Invalid Entry");
                alert.setContentText("Please enter machine ID.");
                alert.showAndWait();
                return;
            } else {
                // if company name is empty
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Invalid Entry");
                alert.setContentText("Please enter company name.");
                alert.showAndWait();
                return;
            } // END if - else
        } // END if - else




        //Create part and add to inventory
        Part tempPart;
        if (inHouseCheck == true) {
            tempPart = new InHouse(i, n, p, s, mi, ma, machId);
            Inventory.addPart(tempPart);
        } else if ( !inHouseCheck ) {
            tempPart = new Outsourced(i, n, p, s, mi, ma, cname);
            Inventory.addPart(tempPart);
        }

        goToMain(actionEvent);
    } // END on save method

    /**
     * method to disable ID text field
     */
    public void disableID() {
        idTxt.setDisable(true);
    } // END disable ID method


    /**
     * method to autogenerate Ids
     */
    public void autoGenerateID() {
        int nextId = 100001;

        //
        for (Part p: Inventory.getAllParts()) {
            if (p.getId() == nextId) {
                nextId++;
            } //END if
        } // END for

        //increment 1 more time to get the next id
        String s = Integer.toString(nextId++);

        idTxt.setText(s);

    } // END auto generate is method

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
    } // END if


} // END Add Part Controller class
