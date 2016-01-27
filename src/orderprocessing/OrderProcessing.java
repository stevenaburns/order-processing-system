package orderprocessing;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author sab5964 and tjf5285
 */
public class OrderProcessing {

    public static void main(String[] args){
        Inventory inventory = createInventory();
        ArrayList<Customer> customers = createCustomerList();
        TransactionController tc = new TransactionController(inventory);
        
        int iterations = 15;
        
        for(int i = 0; i < iterations; i++){
            Random rand = new Random();
            Random rand2 = new Random();
            Random rand3 = new Random();
            Random rand4 = new Random();
            
            int customer = rand.nextInt(customers.size());
            int item = rand2.nextInt(inventory.getInventory().size());
            int quantity = rand3.nextInt(5) + 1;
            int price = tc.getInventory().get(item).getPrice();
            int k = rand4.nextInt(10);
            
            tc.setCustomer(customers.get(customer));
            System.out.println("--------------------------------------------------------------");
            
            if(k <= 5){   
                tc.displayInventory();
                tc.performTransaction(new Order(i, customer, item, quantity, price, TransactionType.ORDER));
            }
            else{
                tc.displayInventory();
                tc.performTransaction(new Return(i, quantity, customer, item, price, TransactionType.RETURN));
            }
            
            if(i == iterations-1){
                tc.displayInventory();
                System.out.println("--------------------------------------------------------------");
                tc.displayInventory();
                tc.performTransaction(new Adjustment(i, item, "New", "New description goes here", 2222, 2222, 100, TransactionType.ADJUSTMENT));
            }
            tc.displayInventory();
        }
    }
    
    public static Inventory createInventory(){
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
    
    public static ArrayList<Customer> createCustomerList(){
        ArrayList<Customer> customerList = new ArrayList();
        //Create customer info      |ID     |First      |Last       |Address        |ZIP    |State |City
        Customer c =    new Customer(0,     "Tim",      "Flynn",    "101 Easy St.", "16802", "PA", "University Park");
        Customer c1 =   new Customer(1,     "Jerry",    "Williams", "102 Easy St.", "16802", "PA", "University Park");
        Customer c2 =   new Customer(2,     "John",     "Smith",    "103 Easy St.", "16802", "PA", "University Park");
        Customer c3 =   new Customer(3,     "Adam",     "Jones",    "104 Easy St.", "16802", "PA", "University Park");
        //Add customers to list
        customerList.add(c);
        customerList.add(c1);
        customerList.add(c2);
        customerList.add(c3);
        //Return the customer list
        return customerList;
    }
}