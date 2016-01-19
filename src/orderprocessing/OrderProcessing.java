package orderprocessing;

import orderprocessing.InventoryItem;

/**
 *
 * @author sab5964 and tjf5285
 */
public class OrderProcessing {

    public static void main(String[] args) 
    {
        Inventory inventory = createInventory();
        TransactionController tc = new TransactionController(inventory);
        tc.displayInventory();
    }
    
    public static Inventory createInventory()
    {
        InventoryItem socks = new InventoryItem(1, "Socks", "Red Harry Potter Griffindor socks", 899, 3);
        InventoryItem shoes = new InventoryItem(2, "Shoes", "Black Jordan sneakers", 7499, 2);
        InventoryItem jeans = new InventoryItem(3, "Jeans", "Blue Levi's jeans size 32", 2499, 5);
        InventoryItem hat = new InventoryItem(4, "Hat", "Black and grey wool winter hat", 1599, 6);
        
        Inventory storeInventory = new Inventory();
        
        storeInventory.AddInventoryItem(socks);
        storeInventory.AddInventoryItem(shoes);
        storeInventory.AddInventoryItem(jeans);
        storeInventory.AddInventoryItem(hat);
        
        return storeInventory;
    }
    
}
