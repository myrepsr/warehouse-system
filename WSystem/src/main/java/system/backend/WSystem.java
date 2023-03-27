package system.backend;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import system.backend.dao.*;
import system.backend.others.Indicator;
import system.backend.others.StockType;
import system.backend.others.Warehouse;
import system.backend.profiles.*;
import system.backend.services.*;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WSystem {
    private Logger LOGGER;
    private static WSystem wsystem;
    //Actors
    private Admin admin;
    private List<Agent> agents;
    private List<Owner> owners;
    
    WSystem(){
        // Creating the system
        setLOGGER(LogManager.getLogger());

        agents = new ArrayList<>();
        owners = new ArrayList<>();
    }

    public static WSystem getInstance(){
        if(wsystem == null)
            wsystem = new WSystem();
        return wsystem;
    }

    public void createAdminTable(String firstName, String lastName,
                            String username, String pass) {
//        CryptoService cryptoService = CryptoService.getInstance();
//        pass = cryptoService.encrypt(pass, cryptoService.getKey(), cryptoService.getCipher());

        DAO<Admin, String> adminDAO = new MainDAO<>();
        Admin admin = adminDAO.findByID(Admin.class, 1L);

        if (admin == null) {
            admin = new Admin(firstName, lastName, username, pass);

            ValidationService validationService = ValidationService.getInstance();
            Indicator indicator = new Indicator();
            indicator.setValidationIndicator(Admin.class);
            validationService.setIndicator(indicator);
            Map<String, Set<String>> constraints = validationService.validate(admin);

            if (constraints.isEmpty()) {
                this.admin = admin;
                adminDAO.save(admin);
            } else {
                LOGGER.error("Admin couldn't be validated");
                Set<Map.Entry<String, Set<String>>> entries = constraints.entrySet();
                for (Map.Entry<String, Set<String>> entry : entries) {
                    LOGGER.error("The " + entry.getKey() + " is not valid!");
                }
            }
        }

    }

    public void createStockTypeTable(){
        DAO<StockType, String> stockTypeDAO = new MainDAO<>();
        List<StockType> stockTypeList = stockTypeDAO.selectAll(StockType.class);

        if(stockTypeList.isEmpty()) {
            String[] stockTypes = {"Vegetables", "Fruits", "Seeds",
                    "Computers", "Smartphones", "Chargers",
                    "Explosives", "Guns", "Military Equipment"};
            StockType[] stockTypes1 = new StockType[9];
            for (int i = 0; i < stockTypes.length; i++) {
                stockTypes1[i] = new StockType();
                stockTypes1[i].setType(stockTypes[i]);
                stockTypeDAO.save(stockTypes1[i]);
            }
        }
    }

    public boolean hasProfiles(){
        if(hasAgentProfiles() || hasOwnerProfiles())
            return true;

        return false;
    }

    public boolean hasAgentProfiles(){
        DAO<Agent, String> agentDAO = new MainDAO<>();
        agents = agentDAO.selectAll(Agent.class);

        if(!agents.isEmpty())
            return true;

        return false;
    }

    public boolean hasOwnerProfiles(){
        DAO<Owner, String> ownerDAO = new MainDAO<>();
        owners = ownerDAO.selectAll(Owner.class);

        if(!owners.isEmpty())
            return true;

        return false;
    }

    public Owner findOwnerBy2Values(String value1, String value2){
        DAO<Owner, String> ownerDAO = new MainDAO<>();
        Owner owner = ownerDAO.findBy2Values(Owner.class, "username", "password", value1, value2);
        return owner;
    }

    public Owner findOwnerBy1Value(String value){
        DAO<Owner, String> ownerDAO = new MainDAO<>();
        Owner owner = ownerDAO.findBy1Value(Owner.class, "password", value);
        return owner;
    }

    public StockType findStockTypeBy1Value(String value){
        DAO<StockType, String> stockTypeDAO = new MainDAO<>();
        StockType stockType = stockTypeDAO.findBy1Value(StockType.class, "type", value);
        return stockType;
    }

    public void initializeDB(){
        createAdminTable("Admin", "Adminov", "admin", "Admin_123");
        createStockTypeTable();
    }

    public void addWarehouseToDatabase(Warehouse warehouse){
        DAO<Warehouse, String> warehouseDAO = new MainDAO<>();
        warehouseDAO.save(warehouse);
    }

    public MainProfile getAdmin() {
        return admin;
    }

    public List<Agent> getAgents() {
        return agents;
    }

    public List<Owner> getOwners() {
        return owners;
    }

    public void setLOGGER(Logger LOGGER) {
        this.LOGGER = LOGGER;
    }

    public void setAdmin(MainProfile admin) {
        this.admin = (Admin)admin;
    }

}
