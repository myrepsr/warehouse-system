package testing;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Test;
import system.backend.Configuration;
import system.backend.dao.DAO;
import system.backend.dao.MainDAO;
import system.backend.others.Warehouse;
import system.backend.profiles.Owner;
import system.backend.profiles.ProfileManager;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class AppTest 
{
    @Test
    @Ignore
    public void registerUserTest(){
        Configuration configuration = Configuration.getInstance();
        configuration.configure();

        Map<String, String> data = new HashMap<>();

        data.put("firstname", "Sosi");
        data.put("lastname", "Tomov");
        data.put("username", "sosi");
        data.put("password", "Asd123_123");
        data.put("email", "sosi@abv.bg");
        data.put("phone", "44444444444");

        ProfileManager<Owner> profileManager = new ProfileManager<>();
        Map<String, Set<String>> cons = new LinkedHashMap<>();

        cons = profileManager.createProfile(Owner.class, data);

        if(!cons.isEmpty()){
            Set<Map.Entry<String, Set<String>>> entries = cons.entrySet();
            for(Map.Entry<String, Set<String>> entry : entries){
                System.out.println("\n" + entry.getKey() + ": ");
                for(String message : entry.getValue())
                    System.out.println(message);
            }
            fail();
        }
    }
    @Test
    @Ignore
    public void createWarehouseTest(){
        Configuration configuration = Configuration.getInstance();
        configuration.configure();

        Map<String, Map<String, String>> data = new HashMap<>();
        data.put("data", new HashMap<>());
        data.get("data").put("size", "21");
        data.get("data").put("temperature", "33");
        data.get("data").put("category", "Food Industry");

        data.put("stocktypes", new HashMap<>());
        data.get("stocktypes").put("stocktype 1", "Vegetables");
        data.get("stocktypes").put("stocktype 2", "Fruits");
        //data.get("stocktypes").put("stocktype 1", "Seeds");

        DAO<Owner, String> dao = new MainDAO<>();
        Owner owner = dao.findByID(Owner.class, 3L);

        Map<String, Set<String>> cons = new LinkedHashMap<>();
        cons = owner.createWarehouse(data);

        if(!cons.isEmpty()){
            Set<Map.Entry<String, Set<String>>> entries = cons.entrySet();
            for(Map.Entry<String, Set<String>> entry : entries){
                System.out.println("\n" + entry.getKey() + ": ");
                for(String message : entry.getValue())
                    System.out.println(message);
            }
            fail();
        }
    }
    @Test
    @Ignore
    public void adminUpdateProfilesTest(){
        Configuration configuration = Configuration.getInstance();
        configuration.configure();

        DAO<Owner, String> dao = new MainDAO<>();
        Owner owner = dao.findByID(Owner.class, 1L);

        Map<String, String> newData = new HashMap<>();
        newData.put("firstname", "!#@AA33");
        newData.put("lastname", "Stefanov");
        newData.put("username", "__--");
        newData.put("password", "asa");
        newData.put("email", "kiro@abv.bg");
        newData.put("phone", "222222222222");

        ProfileManager<Owner> profileManager = new ProfileManager<>();
        Map<String, Set<String>> cons = new LinkedHashMap<>();
        cons = profileManager.updateProfile(owner, Owner.class, newData);

        if(!cons.isEmpty()){
            Set<Map.Entry<String, Set<String>>> entries = cons.entrySet();
            for(Map.Entry<String, Set<String>> entry : entries){
                System.out.println("\n" + entry.getKey() + ": ");
                for(String message : entry.getValue())
                    System.out.println(message);
            }
            fail();
        }
    }
    @Test
    @Ignore
    public void updateWarehouseTest(){
        Configuration configuration = Configuration.getInstance();
        configuration.configure();

        DAO<Owner, String> ownerDAO = new MainDAO<>();
        Owner owner = ownerDAO.findByID(Owner.class, 1L);

        Warehouse warehouse = owner.getWarehouses().get(0);
//        DAO<Warehouse, String> warehousedao = new MainDAO<>();
//        Warehouse warehouse = warehousedao.findByID(Warehouse.class, 1L);

        Map<String, Map<String, String>> newData = new HashMap<>();
        newData.put("data", new HashMap<>());
        newData.get("data").put("size", "23432532453245234");
        newData.get("data").put("temperature", "324523452345234");
        newData.get("data").put("category", "Tech Industry");

        newData.put("stocktypes", new HashMap<>());
        newData.get("stocktypes").put("stocktype 1", "Computers");
        newData.get("stocktypes").put("stocktype 2", "Smartphones");

        Map<String, Set<String>> cons = new LinkedHashMap<>();
        cons = owner.updateWarehouse(warehouse, newData);

        if(!cons.isEmpty()){
            Set<Map.Entry<String, Set<String>>> entries = cons.entrySet();
            for(Map.Entry<String, Set<String>> entry : entries){
                System.out.println("\n" + entry.getKey() + ": ");
                for(String message : entry.getValue())
                    System.out.println(message);
            }
            fail();
        }
    }
    @Test
    @Ignore
    public void deleteUser(){
        Configuration configuration = Configuration.getInstance();
        configuration.configure();

        DAO<Owner, String> dao = new MainDAO<>();
        dao.deleteByID(Owner.class, 1L);
    }
    @Test
    @Ignore
    public void deleteWarehouse(){
        Configuration configuration = Configuration.getInstance();
        configuration.configure();

        DAO<Owner, String> dao = new MainDAO<>();
        Owner owner = dao.findByID(Owner.class, 3L);

        Warehouse warehouse = owner.getWarehouses().get(0);
        owner.getWarehouses().remove(0);

        DAO<Warehouse, String> warehouseDAO = new MainDAO<>();
        warehouseDAO.deleteByID(Warehouse.class, warehouse.getID());
    }
}
