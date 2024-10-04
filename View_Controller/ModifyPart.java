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

public class ModifyPart implements Initializable {
    private static Part selected;
    public RadioButton inHouseRadio;
    public ToggleGroup modifyPartToggle;
    public RadioButton outsourcedRadio;
    public TextField idTxt;
    public TextField nameTxt;
    public TextField stockTxt;
    public TextField priceTxt;
    public TextField maxTxt;
    public TextField minTxt;
    public TextField changingTxt;
    public Label changingLbl;

    /**
     * method to initialize modify part screen
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        disableId();
        setSelected();
        populate();
    } // END Initialise method

    /**
     * method to add data to text fields
     */
    public void populate() {
        idTxt.setText(String.valueOf(selected.getId()));
        nameTxt.setText(String.valueOf(selected.getName()));
        priceTxt.setText(Double.toString(selected.getPrice()));
        stockTxt.setText(Integer.toString(selected.getStock()));
        maxTxt.setText(Integer.toString((selected.getMax())));
        minTxt.setText(Integer.toString(selected.getMin()));

        if (selected instanceof InHouse) {
            changingTxt.setText(Integer.toString(((InHouse) selected).getMachineId()));
            inHouseRadio.setSelected(true);
            changingLbl.setText("Machine ID");
        } else {
            changingTxt.setText(((Outsourced) selected).getCompanyName());
            outsourcedRadio.setSelected(true);
            changingLbl.setText("Company Name");
        }// END if -else
    } // END populate method


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
     * method to pass the selected part
     */
    public static void setSelected () {
        selected = Main.partSelection;
    } // END

    /**
     * method to change label to machineId
     * @param actionEvent selecting in house radio button
     */
    public void onInHouse(javafx.event.ActionEvent actionEvent) {
        changingLbl.setText("Machine ID");
        changingTxt.clear();
    } // END onInHouse method


    /**
     * method to change label to Company Name
     * @param actionEvent selecting outsourced radio button
     */
    public void onOutsourced(javafx.event.ActionEvent actionEvent) {
        changingLbl.setText("Company Name");
        changingTxt.clear();
    } // END onOutsourced Method

    public void onSave(javafx.event.ActionEvent actionEvent) throws IOException {
        //Create variables for text entered
        int i = Integer.parseInt(idTxt.getText());
        String n;
        int stock;
        int ma;
        int mi;
        double price = 0.00;
        int machId = 0;
        String cname = " ";
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

        if (!changingTxt.getText().isEmpty()) {
            if (inHouseRadio.isSelected()) {
                // if empty field
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Invalid Entry");
                alert.setContentText("Please enter machine ID.");
                alert.showAndWait();
                return;
            } else {
                // if empty field
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Invalid Entry");
                alert.setContentText("Please enter company name.");
                alert.showAndWait();
                return;
            } // END if - else
        } // END if is empty


        if (inHouseRadio.isSelected() && changingTxt.getText().isEmpty()) {
            // if empty machine ID
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid Entry");
            alert.setContentText("Please enter machine ID.");
            alert.showAndWait();
            return;
        } else if (inHouseRadio.isSelected() && isDouble(changingTxt.getText()) ) {
            // if a double
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid Entry");
            alert.setContentText("Machine ID must be a whole number.");
            alert.showAndWait();
            return;
        } else if (inHouseRadio.isSelected() && hasChar(changingTxt.getText())) {
            // if has letters
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid Entry");
            alert.setContentText("Machine ID can only be numbers.");
            alert.showAndWait();
            return;
        } // END changing txt validation for inHouse


        if (outsourcedRadio.isSelected() && changingTxt.getText().isEmpty()) {
            // if company name is empty
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid Entry");
            alert.setContentText("Please enter company name.");
            alert.showAndWait();
            return;
        }



        // if already inHouse
        if (selected instanceof InHouse && inHouseRadio.isSelected()) {
            machId = Integer.parseInt(changingTxt.getText());
            InHouse p = new InHouse(i, n, price, stock, mi, ma, machId);
            Inventory.updatePart(index, p);
        }

        //if need to convert to inHouse
        if (selected instanceof Outsourced && inHouseRadio.isSelected()) {
            machId = Integer.parseInt(changingTxt.getText());
            Inventory.deletePart(selected);
            InHouse p = new InHouse(i, n, price, stock, mi, ma, machId);
            Inventory.addPart(p);
        }

        // if need to convert to outsourced
        if (selected instanceof InHouse && outsourcedRadio.isSelected()) {
            cname = changingTxt.getText();
            Inventory.deletePart(selected);
            Outsourced p = new Outsourced(i, n, price, stock, mi, ma, cname);
            Inventory.addPart(p);
        }

        // if already outsourced
        if (selected instanceof Outsourced && outsourcedRadio.isSelected()) {
            cname = changingTxt.getText();
            Outsourced p = new Outsourced(i, n, price, stock, mi,ma, cname);
            Inventory.updatePart(index, p);
        } //END if else

        goToMain(actionEvent);

    } // END onSave method

    /**
     * method to find index of current part
     * @return index
     */
    private int findIndex() {
        int id = selected.getId();
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
     * method to disable id text field
     */
    public void disableId() {
        idTxt.setDisable(true);
    } // END disable Id method



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



} // END modifyPart Controller
