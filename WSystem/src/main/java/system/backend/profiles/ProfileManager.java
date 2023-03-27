package system.backend.profiles;

import system.backend.WSystem;
import system.backend.dao.MainDAO;
import system.backend.dao.DAO;
import system.backend.others.Indicator;
import system.backend.others.StockType;
import system.backend.others.Warehouse;
import system.backend.services.ValidationService;

import javax.validation.ConstraintViolation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ProfileManager<T> {

    public ProfileManager(){

    }

    public <T extends MainProfile & SecondaryProfile> Map<String, Set<String>> createProfile(Class<T> c, Map<String, String> data) {

        try {
            T object = c.getDeclaredConstructor().newInstance();
            setProfileData(object, data);

            ValidationService validationService = ValidationService.getInstance();
            Indicator indicator = new Indicator();
            indicator.setValidationIndicator(c);
            validationService.setIndicator(indicator);
            Map<String, Set<String>> constraints = validationService.validate(object);

            if(constraints.isEmpty()) {
                if (object.getClass() == Owner.class) {
                    DAO<Owner, String> ownerDAO = new MainDAO<>();
                    ownerDAO.save((Owner) object);
                }
                else if (object.getClass() == Agent.class) {
                    DAO<Agent, String> agentDAO = new MainDAO<>();
                    agentDAO.save((Agent) object);
                }
            }
            return constraints;

        } catch (Exception e){
            System.out.println("Something happened during creating the profile.");
            e.printStackTrace();

            return null;
        }
    }

    public <T extends MainProfile & SecondaryProfile> void setProfileData(T object, Map<String, String> data){
        object.setFirstname(data.get("firstname"));
        object.setLastname(data.get("lastname"));
        object.setUsername(data.get("username"));
        object.setPassword(data.get("password"));
        object.setEmailAddress(data.get("email"));
        object.setPhoneNumber(data.get("phone"));
    }

    public <T extends MainProfile & SecondaryProfile> void getProfileData(T object, Map<String, String> data){
        data.clear();
        data.put("firstname", object.getFirstname());
        data.put("lastname", object.getLastname());
        data.put("username", object.getUsername());
        data.put("password", object.getPassword());
        data.put("email", object.getEmailAddress());
        data.put("phone", object.getPhoneNumber());
    }

    public <T extends MainProfile & SecondaryProfile>
    Map<String, Set<String>> updateProfile(T object, Class<T> c, Map<String, String> data){

        setProfileData(object, data);

        ValidationService validationService = ValidationService.getInstance();
        Indicator indicator = new Indicator();
        indicator.setValidationIndicator(c);
        validationService.setIndicator(indicator);
        validationService.setIgnoreThisID(object.getID());
        Map<String, Set<String>> constraints = validationService.validate(object);

        if(constraints.isEmpty()) {
            if (object.getClass() == Owner.class) {
                DAO<Owner, String> ownerDAO = new MainDAO<>();
                ownerDAO.update((Owner) object);
            }
            else if (object.getClass() == Agent.class) {
                DAO<Agent, String> agentDAO = new MainDAO<>();
                agentDAO.update((Agent) object);
            }
        }

        return constraints;
    }
    public <T extends MainProfile & SecondaryProfile>
    Map<String, Set<String>> updateProfileWithoutPass(T object, Class<T> c, Map<String, String> data){
        setProfileDataWithoutPass(object, data);

        ValidationService validationService = ValidationService.getInstance();
        Indicator indicator = new Indicator();
        indicator.setValidationIndicator(c);
        validationService.setIndicator(indicator);
        validationService.setIgnoreThisID(object.getID());
        Map<String, Set<String>> constraints = validationService.validate(object);

        if(constraints.isEmpty()) {
            if (object.getClass() == Owner.class) {
                DAO<Owner, String> ownerDAO = new MainDAO<>();
                ownerDAO.update((Owner) object);
            }
            else if (object.getClass() == Agent.class) {
                DAO<Agent, String> agentDAO = new MainDAO<>();
                agentDAO.update((Agent) object);
            }
        }

        return constraints;
    }
    public <T extends MainProfile & SecondaryProfile> void getProfileDataWithoutPass(T object, Map<String, String> data){
        data.clear();
        data.put("firstname", object.getFirstname());
        data.put("lastname", object.getLastname());
        data.put("username", object.getUsername());
        data.put("email", object.getEmailAddress());
        data.put("phone", object.getPhoneNumber());
    }
    public <T extends MainProfile & SecondaryProfile> void setProfileDataWithoutPass(T object, Map<String, String> data){
        object.setFirstname(data.get("firstname"));
        object.setLastname(data.get("lastname"));
        object.setUsername(data.get("username"));
        object.setEmailAddress(data.get("email"));
        object.setPhoneNumber(data.get("phone"));
    }

    public void getWarehouseData(Warehouse warehouse, Map<String, Map<String, String>> data){
        data.put("data", new HashMap<>());
        data.put("stocktypes", new HashMap<>());

        data.get("data").put("size", String.valueOf(warehouse.getSize()));
        data.get("data").put("category", warehouse.getCategory());
        data.get("data").put("temperature", String.valueOf(warehouse.getTemperature()));

        int i = 0;
        for (StockType stockType : warehouse.getStockTypes()) {
            data.get("stocktypes").put("stocktype " + i, stockType.getType());
        }
    }

    public void setWarehouseData(Warehouse warehouse, Map<String, Map<String, String>> data){
        warehouse.setSize(Double.parseDouble(data.get("data").get("size")));
        warehouse.setTemperature(Double.parseDouble(data.get("data").get("temperature")));
        warehouse.setCategory(data.get("data").get("category"));

        WSystem wSystem = WSystem.getInstance();
        Set<Map.Entry<String, String>> stocks = data.get("stocktypes").entrySet();

        Set<StockType> stockTypes = new HashSet<>();

        for (Map.Entry<String, String> stock : stocks)
            stockTypes.add(wSystem.findStockTypeBy1Value(stock.getValue()));

        warehouse.setStockTypes(stockTypes);
    }

    public Map<String, Set<String>> updateWarehouse(Warehouse warehouse, Map<String, Map<String, String>> data){

        warehouse.setSize(Double.parseDouble(data.get("data").get("size")));
        warehouse.setTemperature(Double.parseDouble(data.get("data").get("temperature")));
        warehouse.setCategory(data.get("data").get("category"));

        WSystem wSystem = WSystem.getInstance();
        Set<Map.Entry<String, String>> stocks = data.get("stocktypes").entrySet();

        Set<StockType> stockTypes = new HashSet<>();

        for (Map.Entry<String, String> stock : stocks)
            stockTypes.add(wSystem.findStockTypeBy1Value(stock.getValue()));

        warehouse.setStockTypes(stockTypes);

        ValidationService validationService = ValidationService.getInstance();

        Map<String, Set<String>> constraints = validationService.validate(warehouse);

        if(constraints.isEmpty()) {
            DAO<Warehouse, String> warehouseDAO = new MainDAO<>();
            warehouseDAO.update(warehouse);
        }
        return constraints;
    }
}
