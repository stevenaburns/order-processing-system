package orderprocessing;

import java.util.ArrayList;
import java.util.Random;
import orderprocessing.InventoryItem;

/**
 *
 * @author sab5964 and tjf5285
 */
public class OrderProcessing {

    public static void main(String[] args) 
    {
        Inventory inventory = createInventory();
        ArrayList<Customer> customers = createCustomerList();
        TransactionController tc = new TransactionController(inventory);
        
        int iterations = 50;
        
        for(int i = 0; i < iterations; i++)
        {
            Random rand = new Random();
            Random rand2 = new Random();
            Random rand3 = new Random();
            
            int customer = rand.nextInt(customers.size());
            int item = rand2.nextInt(inventory.getInventory().size());
            int quantity = rand3.nextInt(5);
            tc.setCustomer(customers.get(customer));
            
            tc.setOrder(new Order(customer, item, quantity));
            performTransaction(tc);
        }
        
        tc.displayTotals();
       
    }
    
    public static void performTransaction(TransactionController tc)
    {
        System.out.println("Inventory Before Sale:");
        tc.displayInventory();
        if(tc.checkAvailibility() == true)
        {
           tc.performTransaction();
           tc.displayTransactionDetails();
           System.out.println("\nInventory After Sale:");
           tc.displayInventory();
        }
        else
        {
            System.out.println("\nRequested units unavailable, order cancelled");
        } 
        
        System.out.println("---------------------------------------------------------------\n");
    }
    
    public static Inventory createInventory()
    {
        //Sample inventory                           |ID    |Name       |Description                            |Price  |Units
        InventoryItem socks =       new InventoryItem(0,    "Socks",    "Red Harry Potter Griffindor socks",    899,    29);
        InventoryItem shoes =       new InventoryItem(1,    "Shoes",    "Black Jordan sneakers",                7499,   20);
        InventoryItem jeans =       new InventoryItem(2,    "Jeans",    "Blue Levi's jeans size 32",            2499,   15);
        InventoryItem hat =         new InventoryItem(3,    "Hat",      "Black and grey wool winter hat",       1599,   16);
        InventoryItem gloves =      new InventoryItem(4,    "Gloves",   "Blue wool winter gloves",              1299,   26);
        InventoryItem sweatshirt =  new InventoryItem(5,    "Shirt",    "Purple cotton t-shirt",                3999,   18);
        
        //Create an inventory object to store all this information
        Inventory storeInventory = new Inventory();
        
        //Add the items to the inventory
        storeInventory.AddInventoryItem(socks);
        storeInventory.AddInventoryItem(shoes);
        storeInventory.AddInventoryItem(jeans);
        storeInventory.AddInventoryItem(hat);
        storeInventory.AddInventoryItem(gloves);
        storeInventory.AddInventoryItem(sweatshirt);
        
        //Return the inventory
        return storeInventory;
    }
    
    public static ArrayList<Customer> createCustomerList()
    {
        ArrayList<Customer> customerList = new ArrayList();
        
        //Create customer info      |ID     |First      |Last       |Address        |ZIP    |State |City
        Customer c =    new Customer(0,     "Tim",      "Flynn",    "101 Easy St.", "16802", "PA", "University Park");
        Customer c1 =   new Customer(1,     "Jerry",    "Williams", "102 Easy St.", "16802", "PA", "University Park");
        Customer c2 =   new Customer(2,     "John",     "Smith",    "103 Easy St.", "16802", "PA", "University Park");
        Customer c3 =   new Customer(2,     "Adam",     "Jones",    "104 Easy St.", "16802", "PA", "University Park");
        
        customerList.add(c);
        customerList.add(c1);
        customerList.add(c2);
        customerList.add(c3);
        return customerList;
    }
}
