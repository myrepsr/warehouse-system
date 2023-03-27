package system.application.controllers;

import javafx.scene.control.*;

public interface Controller {
    Label getViolationsLabel();

    void fillConsBox2(String message);
    void fillConsBox1(String message);

    TextField getUsernameField();
    TextField getFirstNameField();
    TextField getLastNameField();
    PasswordField getPasswordField();
    TextField getEmailField();
    TextField getPhoneNumberField();

    Label getSuccessLabel();
    PasswordField getConfirmField();

    ComboBox<String> getTypeBox();
    ListView<String> getStockTypeView();
    TextField getSizeField();
    TextField getTemperatureField();
}