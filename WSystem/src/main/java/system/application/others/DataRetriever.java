package system.application.others;

import javafx.collections.ObservableList;
import system.application.controllers.*;
import system.backend.profiles.MainProfile;
import system.backend.profiles.SecondaryProfile;

import java.util.*;

public class DataRetriever {
    private static DataRetriever dataRetriever;

    public static DataRetriever getInstance() {
        if (dataRetriever == null)
            dataRetriever = new DataRetriever();
        return dataRetriever;
    }

    public void getRegisterData(Controller controller, Map<String, String> data) {
        data.clear();
        data.put("firstname", controller.getFirstNameField().getText().trim());
        data.put("lastname", controller.getLastNameField().getText().trim());
        data.put("username", controller.getUsernameField().getText().trim());
        data.put("password", controller.getPasswordField().getText());
        data.put("confirmPassword", controller.getConfirmField().getText());
        data.put("email", controller.getEmailField().getText());
        data.put("phone", controller.getPhoneNumberField().getText());
    }

    public void getEditData(Controller controller, Map<String, String> data) {
        data.clear();
        data.put("firstname", controller.getFirstNameField().getText().trim());
        data.put("lastname", controller.getLastNameField().getText().trim());
        data.put("username", controller.getUsernameField().getText().trim());
        data.put("password", controller.getPasswordField().getText());
        data.put("email", controller.getEmailField().getText());
        data.put("phone", controller.getPhoneNumberField().getText());
    }

    public void getEditDataWithoutPass(Controller controller, Map<String, String> data) {
        data.clear();
        data.put("firstname", controller.getFirstNameField().getText().trim());
        data.put("lastname", controller.getLastNameField().getText().trim());
        data.put("username", controller.getUsernameField().getText().trim());
        data.put("email", controller.getEmailField().getText());
        data.put("phone", controller.getPhoneNumberField().getText());
    }

    public void getWarehouseDataFromController(Controller controller, Map<String, Map<String, String>> data) {
        data.clear();
        data.put("data", new HashMap<>());

//        System.out.println(controller.getTypeBox().getSelectionModel().getSelectedItem());
//
//        System.out.println("Category: " + controller.getTypeBox().getSelectionModel().getSelectedItem());
//        System.out.println("Size: " + controller.getSizeField().getText());
//        System.out.println("Temperature: " + controller.getTemperatureField().getText());

//        Scanner scanner = new Scanner(System.in);
//        scanner.next();
//
        if(controller.getTypeBox().getSelectionModel().getSelectedItem() == null)
            data.get("data").put("category", "");
        else
            data.get("data").put("category", controller.getTypeBox().getSelectionModel().getSelectedItem());
        data.get("data").put("size", controller.getSizeField().getText());
        data.get("data").put("temperature", controller.getTemperatureField().getText());

        ObservableList<String> stockType = controller.getStockTypeView().getSelectionModel().getSelectedItems();

        if(!stockType.isEmpty()) {
            data.put("stocktypes", new HashMap<>());
            Map<String, String> stockTypes = data.get("stocktypes");

            int i = 1;
            for (String stock : stockType) {
                stockTypes.put("stocktype " + i, stock);
                i++;
            }
        }

//        System.out.println("Sled vkarvane: " + data.get("stocktypes").get("stocktype 1"));
//        Scanner scanner = new Scanner(System.in);
//        scanner.next();
    }
}
//        for (Map.Entry<String, Map<String, String>> entry : set) {
//            Set<Map.Entry<String, String>> entrySet = entry.getValue().entrySet();
//            for(Map.Entry<String, String> newEntry : entrySet){
//                newEntry.
//            }
//        }
//
//        stockType = controller.getStockTypeView().getSelectionModel().getSelectedItems();  // Selected items of ListView
//        for (String o : stockType) {
//            System.out.println("o = " + o);
//        }
//    public void getWarehouseData(CreateWarehouseController controller, Map<String, String> data){
//        data.clear();
//        data.put("category", controller.getTypeBox().getSelectionModel().getSelectedItem());
//        data.put("size", controller.getSizeField().getText());
//        data.put("temperature", controller.getTemperatureField().getText());
//
//        ObservableList<String> stockType = controller.getStockTypeView().getSelectionModel().getSelectedItems();
//        if(!stockType.isEmpty()) {
//            int i = 1;
//            for (String stock : stockType) {
//                data.put("stocktype " + i, stock);
//                i++;
//            }
//        }
//    }