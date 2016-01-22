package orderprocessing;

import java.util.ArrayList;
import java.util.Random;

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
            Random rand4 = new Random();
            
            int customer = rand.nextInt(customers.size());
            int item = rand2.nextInt(inventory.getInventory().size());
            int quantity = rand3.nextInt(5)+1;
            
            tc.setCustomer(customers.get(customer));
            tc.setTransaction(new Order(i, customer, item, quantity));
            
            //System.out.println("User ID: " + tc.getTransaction().getUserId());
            //System.out.println("User ID: " + tc.getTransaction().getItemId());
            //System.out.println("User ID: " + tc.getTransaction().getQuantity());
            //System.out.println(tc.getTransaction().getTransactionId());
            
            if(rand4.nextInt(8) == 3)
            {
                performReturn(tc);
            }
            else
            {
                performOrder(tc);
            }
        }
        
        tc.displayTotals();
    }
    
    public static void performOrder(TransactionController tc)
    {
        System.out.println("Inventory Before Sale:");
        tc.displayInventory();
        tc.performOrder();
        
        tc.displaySaleDetails();
        System.out.println("\nInventory After Sale:");
        tc.displayInventory();
        
        System.out.println("---------------------------------------------------------------\n");
    }
    
     public static void performReturn(TransactionController tc)
    {
        System.out.println("Inventory Before Return:");
        tc.displayInventory();
        tc.performReturn();
        tc.displayReturnDetails();
        System.out.println("\nInventory After Return:");
        tc.displayInventory();
       
        System.out.println("---------------------------------------------------------------\n");
    }
    
    public static Inventory createInventory()
    {
        //Sample inventory                           |ID    |Name       |Description                            |Cost   |Price  |Units
        InventoryItem socks =       new InventoryItem(0,    "Socks",    "Red Harry Potter Griffindor socks",    599,    899,    29);
        InventoryItem shoes =       new InventoryItem(1,    "Shoes",    "Black Jordan sneakers",                4999,   499,   20);
        InventoryItem jeans =       new InventoryItem(2,    "Jeans",    "Blue Levi's jeans size 32",            1399,   2499,   15);
        InventoryItem hat =         new InventoryItem(3,    "Hat",      "Black and grey wool winter hat",       799,    1599,   16);
        InventoryItem gloves =      new InventoryItem(4,    "Gloves",   "Blue wool winter gloves",              499,    1299,   26);
        InventoryItem sweatshirt =  new InventoryItem(5,    "Shirt",    "Purple cotton t-shirt",                1999,   3999,   18);
        
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
        Customer c3 =   new Customer(3,     "Adam",     "Jones",    "104 Easy St.", "16802", "PA", "University Park");
        
        customerList.add(c);
        customerList.add(c1);
        customerList.add(c2);
        customerList.add(c3);
        return customerList;
    }
}
