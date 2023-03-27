package system.application.controllers;

import javafx.scene.control.*;

public class AbstractController implements Controller {
    @Override
    public Label getViolationsLabel() {
        System.out.println("ABSTRACT FUNCTION CALLED");
        return null;
    }

    @Override
    public void fillConsBox2(String message) {
        System.out.println("ABSTRACT FUNCTION CALLED");
    }

    @Override
    public void fillConsBox1(String message) {
        System.out.println("ABSTRACT FUNCTION CALLED");
    }

    @Override
    public TextField getUsernameField() {
        System.out.println("ABSTRACT FUNCTION CALLED");
        return null;
    }

    @Override
    public TextField getFirstNameField() {
        System.out.println("ABSTRACT FUNCTION CALLED");
        return null;
    }

    @Override
    public TextField getLastNameField() {
        System.out.println("ABSTRACT FUNCTION CALLED");
        return null;
    }

    @Override
    public PasswordField getPasswordField() {
        System.out.println("ABSTRACT FUNCTION CALLED");
        return null;
    }

    @Override
    public TextField getEmailField() {
        System.out.println("ABSTRACT FUNCTION CALLED");
        return null;
    }

    @Override
    public TextField getPhoneNumberField() {
        System.out.println("ABSTRACT FUNCTION CALLED");
        return null;
    }

    @Override
    public Label getSuccessLabel() {
        System.out.println("ABSTRACT FUNCTION CALLED");
        return null;
    }

    @Override
    public PasswordField getConfirmField() {
        System.out.println("ABSTRACT FUNCTION CALLED");
        return null;
    }

    @Override
    public ComboBox<String> getTypeBox() {
        System.out.println("ABSTRACT FUNCTION CALLED");
        return null;
    }

    @Override
    public ListView<String> getStockTypeView() {
        System.out.println("ABSTRACT FUNCTION CALLED");
        return null;
    }

    @Override
    public TextField getSizeField() {
        System.out.println("ABSTRACT FUNCTION CALLED");
        return null;
    }

    @Override
    public TextField getTemperatureField() {
        System.out.println("ABSTRACT FUNCTION CALLED");
        return null;
    }
}
