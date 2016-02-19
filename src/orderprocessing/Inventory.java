package orderprocessing;

import java.util.ArrayList;

/**
 *
 * @author sab5964 and tjf5285
 */
public class Inventory 
{
    private ArrayList<InventoryItem> inventory = new ArrayList();

    public ArrayList<InventoryItem> getInventory() {
        return inventory;
    }
    
    public void AddInventoryItem(InventoryItem i){
        inventory.add(i);
    }

    public void setInventory(ArrayList inventory) {
        this.inventory = inventory;
    }
}
