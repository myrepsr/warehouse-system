package system.backend.services;

import system.backend.others.Indicator;
import system.backend.profiles.Agent;
import system.backend.profiles.MainProfile;
import system.backend.profiles.Owner;
import system.backend.profiles.SecondaryProfile;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.Validation;
import java.util.*;

//This class will be used where validation is needed
public class ValidationService {
    private static ValidationService validationService;
    private ValidatorFactory factory;
    private Validator validator;
    private Long ignoreThisID = null;
    private Indicator indicator;
    private Map<String, Set<String>> cons = new LinkedHashMap<>();

    public static ValidationService getInstance() {
        if (validationService == null)
            validationService = new ValidationService();
        return validationService;
    }

    public ValidationService() {
        createFactory();
        createValidator();
    }

    private void createFactory() {
        factory = Validation.buildDefaultValidatorFactory();
    }

    private void createValidator() {
        validator = factory.getValidator();
    }

    public Set<ConstraintViolation<Object>> oldValidate(Object object) {
        return validator.validate(object);
    }

    public Set<ConstraintViolation<Object>> validateProperty(Object object, String property) {
        return validator.validateProperty(object, property);
    }

    public Map<String, Set<String>> validate(Object object) {
        Set<ConstraintViolation<Object>> constraints = validator.validate(object);

        if (!constraints.isEmpty()) {
            cons.clear();
            for (ConstraintViolation<Object> con : constraints)
                cons.put(con.getPropertyPath().toString(), new LinkedHashSet<>());

            Set<Map.Entry<String, Set<String>>> entries = cons.entrySet();
//            for (ConstraintViolation<Object> con : constraints) {
//                for (Map.Entry<String, Set<String>> entry : entries)
//                    if (con.getPropertyPath().toString().equals(entry.getKey()))
//                        entry.getValue().add(con.getMessage());
//            }

            // ^^ Up is the old loop. Down is the new one.

            for (ConstraintViolation<Object> con : constraints) {
                for (Map.Entry<String, Set<String>> entry : entries)
                    if (con.getPropertyPath().toString().equals(entry.getKey()))
                        if(con.getMessage().equals(" is already taken")) {
                            if (con.getPropertyPath().toString().equals("emailAddress"))
                                entry.getValue().add("Email address" + con.getMessage());
                            else if (con.getPropertyPath().toString().equals("username"))
                                entry.getValue().add("Username" + con.getMessage());
                            else if (con.getPropertyPath().toString().equals("phoneNumber"))
                                entry.getValue().add("Phone number" + con.getMessage());
                        }
                        else entry.getValue().add(con.getMessage());
            }
        }
        return cons;
    }

    public Long getIgnoreThisID() {
        return ignoreThisID;
    }

    public void setIgnoreThisID(Long ignoreThisID) {
        this.ignoreThisID = ignoreThisID;
    }

    public boolean allDataFilled(Map<String, String> data) {

        if (data != null) {
            Set<Map.Entry<String, String>> set = data.entrySet();

            for (Map.Entry<String, String> entry : set) {
                if (entry.getValue().equals("")) return false;
            }
            return true;
        }
        return false;
    }


//    public boolean areStockTypesSelected(Map<String, String> data){
//        Set<Map.Entry<String, Map<String, String>>> firstEntries = data.entrySet();
//
//        for(Map.Entry<String, Map<String, String>> firstEntry : firstEntries){
//            Set<Map.Entry<String, String>> secondEntries = firstEntry.getValue().entrySet();
//            for(Map.Entry<String, String> entry : secondEntries){
//                if(entry.getValue().equals(""))
//                    return false;
//            }
//        }
//        return true;
//    }

//    public boolean allWarehouseDataFilled(Map<String, String> data){
//        for(Map.Entry<String, String> entry : data.entrySet()){
//            if(entry.getValue().equals(""))
//                return false;
//        }
//        return true;
//    }

    public boolean stockTypeEmpty(){

        return true;
    }

    public boolean passwordsMatch(String password, String confirmPassword){
        if (!password.equals(confirmPassword))
            return false;

        return true;
    }

    public Indicator getIndicator() {
        return indicator;
    }

    public void setIndicator(Indicator indicator) {
        this.indicator = indicator;
    }
}
