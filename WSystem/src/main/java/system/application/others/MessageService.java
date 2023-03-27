package system.application.others;

import system.application.controllers.Controller;
import system.application.controllers.admin.EditUserInfoController;
import system.application.controllers.admin.RegisterAgentController;
import system.application.controllers.admin.RegisterOwnerController;
import system.application.controllers.owner.CreateWarehouseController;
import system.application.controllers.owner.ViewProfileController;

import java.util.*;

public class MessageService {
    private static MessageService messageService;

    public static MessageService getInstance() {
        if (messageService == null)
            messageService = new MessageService();
        return messageService;
    }

    // old fill data function
    public void fillDataMessage(Controller controller, Map<String, String> data) {
        String message = "Please fill all of the required data!";

        if(controller.getClass() == CreateWarehouseController.class)
            controller.fillConsBox1(message);
        else {
            controller.getSuccessLabel().setVisible(false);
            controller.getViolationsLabel().setVisible(true);

            if (data.get("firstname").equals("") || data.get("lastname").equals("") || data.get("username").equals(""))
                controller.fillConsBox1(message);
            if (data.get("password").equals("") || data.get("confirmPassword").equals("")
                    || data.get("phone").equals("") || data.get("email").equals(""))
                controller.fillConsBox2(message);
        }
    }

    public void noStockTypesMessage(Controller controller){
        String message = "Please select stock types!";
        controller.fillConsBox1(message);
    }

    public void passwordMatchMessage(Controller controller) {
        String message = "Passwords don't match!";
        System.out.println("Passwords don't match!");
        controller.fillConsBox2(message);
        controller.getSuccessLabel().setVisible(false);
        controller.getViolationsLabel().setVisible(true);
    }

    public void showMessages(Controller controller, Map<String, Set<String>> cons, String[] keys) {

        Set<Map.Entry<String, Set<String>>> entries = cons.entrySet();

        // ORDERED TEST
//        Map<String, Set<String>> ordered = new LinkedHashMap<>();
//
//        for (int j = 0; j < keys.length; j++) {
//            for (Map.Entry<String, Set<String>> entry : entries)
//                if (keys[j].equals(entry.getKey()))
//                    ordered.put(entry.getKey(), entry.getValue());
//        }

        if(controller.getClass() == RegisterAgentController.class
                || controller.getClass() == RegisterOwnerController.class
                || controller.getClass() == EditUserInfoController.class
                || controller.getClass() == ViewProfileController.class)
            addNewLines(entries, keys);

        for (int i = 0; i < keys.length; i++) {
            for (Map.Entry<String, Set<String>> entry : entries)
                if (i < 3 && keys[i].equals(entry.getKey())) {
                    for (String message : entry.getValue())
                        if (!message.isEmpty())
                            controller.fillConsBox1(message);
                } else if (i >= 3 && keys[i].equals(entry.getKey())) {
                    for (String message : entry.getValue())
                        if (!message.isEmpty())
                            controller.fillConsBox2(message);
                }
        }
    }
    private void addNewLines(Set<Map.Entry<String, Set<String>>> entries, String[] keys){
        Map<String, Boolean> isEmptyMap = new HashMap<>();

        for (int j = 0; j < keys.length; j++) {
            for (Map.Entry<String, Set<String>> entry : entries) {
                if (keys[j].equals(entry.getKey())) {
                    isEmptyMap.put(entry.getKey(), false);
                    break;
                } else isEmptyMap.put(keys[j], true);
            }
        }

        for (int i = 0; i < keys.length; i++) {
            for (Map.Entry<String, Set<String>> entry : entries)
                if (i == 0 || i == 3) {
                    if (!isEmptyMap.get(keys[i]) && (!isEmptyMap.get(keys[i + 1]) || !isEmptyMap.get(keys[i + 2])))
                        if (entry.getKey().equals(keys[i]))
                            entry.getValue().add("\n");
                } else if (i == 1 || i == 4) {
                    if (!isEmptyMap.get(keys[i]) && !isEmptyMap.get(keys[i + 1]))
                        if (entry.getKey().equals(keys[i]))
                            entry.getValue().add("\n");
                }
        }
    }
}

