package system.backend.dataholders;

import system.backend.profiles.Owner;

public class OwnerDataHolder {
    private static OwnerDataHolder ownerDataHolder;
    private Owner owner;

    public static OwnerDataHolder getInstance(){
        if(ownerDataHolder == null)
            ownerDataHolder = new OwnerDataHolder();
        return ownerDataHolder;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
