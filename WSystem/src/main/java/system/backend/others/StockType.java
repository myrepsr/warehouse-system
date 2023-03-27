package system.backend.others;

import javax.persistence.*;
import java.util.List;

@Entity
public class StockType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long ID;
    private String type;
    @ManyToMany(mappedBy = "stockTypes")
    private List<Warehouse> warehouses;

    public StockType(){

    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(List<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }
}
