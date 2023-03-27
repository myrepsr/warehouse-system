package system.backend.others;

import system.backend.WSystem;
import system.backend.dao.DAO;
import system.backend.dao.MainDAO;
import system.backend.profiles.Agent;
import system.backend.profiles.Owner;
import system.backend.services.ValidationService;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long ID;
    @Max(value = 1000000, message = "Size - too long")
    //@Pattern(regexp = "[0-9]", message = "Size - can contain only digits")
    private double size;
    @Max(value = 50, message = "Temperature - too long")
    //@Pattern(regexp = "[0-9]", message = "Temperature - can contain only digits")
    private double temperature;
    private String category;
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "Warehouse_StockType",
            joinColumns = {@JoinColumn(name = "warehouse_id")},
            inverseJoinColumns = {@JoinColumn(name = "stockType_id")})
    private Set<StockType> stockTypes;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    public Warehouse(){

    }

//    public static void getWarehouseData(Warehouse warehouse, Map<String, Map<String, String>> data){
//        data.put("data", new HashMap<>());
//        data.put("stocktypes", new HashMap<>());
//
//        data.get("data").put("size", String.valueOf(warehouse.getSize()));
//        data.get("data").put("category", warehouse.getCategory());
//        data.get("data").put("temperature", String.valueOf(warehouse.getTemperature()));
//
//        int i = 0;
//        for (StockType stockType : warehouse.getStockTypes()) {
//            data.get("stocktypes").put("stocktype " + i, stockType.getType());
//        }
//    }
//
//    public static void setWarehouseData(Warehouse warehouse, Map<String, Map<String, String>> data){
//        warehouse.setSize(Double.parseDouble(data.get("data").get("size")));
//        warehouse.setTemperature(Double.parseDouble(data.get("data").get("temperature")));
//        warehouse.setCategory(data.get("data").get("category"));
//
//        WSystem wSystem = WSystem.getInstance();
//        Set<Map.Entry<String, String>> stocks = data.get("stocktypes").entrySet();
//
//        Set<StockType> stockTypes = new HashSet<>();
//
//        for (Map.Entry<String, String> stock : stocks)
//            stockTypes.add(wSystem.findStockTypeBy1Value(stock.getValue()));
//
//        warehouse.setStockTypes(stockTypes);
//    }
//
//    public static Map<String, Set<String>> updateWarehouse(Warehouse warehouse, Map<String, Map<String, String>> data){
//
//        warehouse.setSize(Double.parseDouble(data.get("data").get("size")));
//        warehouse.setTemperature(Double.parseDouble(data.get("data").get("temperature")));
//        warehouse.setCategory(data.get("data").get("category"));
//
//        WSystem wSystem = WSystem.getInstance();
//        Set<Map.Entry<String, String>> stocks = data.get("stocktypes").entrySet();
//
//        Set<StockType> stockTypes = new HashSet<>();
//
//        for (Map.Entry<String, String> stock : stocks)
//            stockTypes.add(wSystem.findStockTypeBy1Value(stock.getValue()));
//
//        warehouse.setStockTypes(stockTypes);
//
//        ValidationService validationService = ValidationService.getInstance();
//
//        Map<String, Set<String>> constraints = validationService.validate(warehouse);
//
//        if(constraints.isEmpty()) {
//            DAO<Warehouse, String> warehouseDAO = new MainDAO<>();
//            warehouseDAO.update(warehouse);
//        }
//        return constraints;
//    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public Set<StockType> getStockTypes() {
        return stockTypes;
    }

    public void setStockTypes(Set<StockType> stockTypes) {
        this.stockTypes = stockTypes;
    }

    public Owner getOwner() {
        return owner;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
        owner.getWarehouses().add(this);
    }
}
