package orderprocessing;

import java.util.ArrayList;

/**
 *
 * @author sab5964 and tjf5285
 */
public class TransactionController 
{
    ArrayList<InventoryItem> inventory;
    
    public TransactionController(Inventory i)
    {
        this.inventory = i.getInventory();
    }
    
    public void displayInventory()
    {
        System.out.println("ItemID\tName\tPrice\tQuantity\tDescription");
        
        for(InventoryItem i: inventory)
        {
            System.out.println(i.getItemID() + "\t" + i.getName() + "\t" + (double)i.getPrice()/100 + "\t" + i.getQuantity() + "\t\t" + i.getDescription());
        }
        
    }
}
