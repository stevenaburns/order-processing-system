package orderprocessing;

import java.util.ArrayList;

/**
 *
 * @author sab5964 and tjf5285
 */
public class TransactionController 
{
    private ArrayList<InventoryItem> inventory;
    private Customer customer;
    private Transaction transaction;
    private int totalSalesUnits;
    private int totalSales;
    
    public TransactionController(Inventory inventory)
    {
        this.inventory = inventory.getInventory();
    }
    
    public boolean checkAvailibility()
    {
        InventoryItem ii = getInventory().get(getTransaction().getItemId());

        if(ii.getQuantity() - getTransaction().getQuantity() < 0)
        {
            System.out.println("\nOnly " + ii.getQuantity() + " units of " + ii.getName() + " available, order requested " + getTransaction().getQuantity());
            return false;
        }
        else
        {
            return true;
        }
    }
    
    public void performOrder()
    {
        InventoryItem ii = getInventory().get(getTransaction().getItemId());
        ii.setQuantity((ii.getQuantity()-getTransaction().getQuantity()));
        int quantity = getTransaction().getQuantity();
        int price = ii.getPrice()*quantity;
        totalSales += price;
        totalSalesUnits += quantity;
    }
    
    public void displayOrderDetails()
    {
        InventoryItem ii = getInventory().get(getTransaction().getItemId());
        System.out.println("\n" + getTransaction().getQuantity() + " unit(s) of " + ii.getName() + " was sold to " + getCustomer().getFirstName() + " " + getCustomer().getLastName() + " for " + (double)ii.getPrice()/100*getTransaction().getQuantity());
    }
    
    public void displayReturnDetails()
    {
        InventoryItem ii = getInventory().get(getTransaction().getItemId());
        System.out.println("\n" + getTransaction().getQuantity() + " unit(s) of " + ii.getName() + " was returned by " + getCustomer().getFirstName() + " " + getCustomer().getLastName() + " for " + (double)ii.getPrice()/100*getTransaction().getQuantity());
    }
    
    public void displayInventory()
    {
        System.out.println("\nItemID\tName\tPrice\tQuantity\tDescription");
        
        for(InventoryItem i: getInventory())
        {
            System.out.println(i.getItemID() + "\t" + i.getName() + "\t" + (double)i.getPrice()/100 + "\t" + i.getQuantity() + "\t\t" + i.getDescription());
        }
        
    }
    
    public void displayTotals()
    {
      System.out.println("\nTotal Units Sold: \t" + totalSalesUnits);
      System.out.println("Total Sales: \t\t" + (double)totalSales/100);
    }
    
    /**
     * @return the inventory
     */
    public ArrayList<InventoryItem> getInventory() {
        return inventory;
    }

    /**
     * @param inventory the inventory to set
     */
    public void setInventory(ArrayList<InventoryItem> inventory) {
        this.inventory = inventory;
    }

    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * @return the order
     */
    public Transaction getTransaction() {
        return transaction;
    }

    /**
     * @param order the order to set
     */
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    /**
     * @return the totalSales
     */
    public int getTotalSales() {
        return totalSales;
    }

    /**
     * @param totalSales the totalSales to set
     */
    public void setTotalSales(int totalSales) {
        this.totalSales = totalSales;
    }

    /**
     * @return the totalSalesUnits
     */
    public int getTotalSalesUnits() {
        return totalSalesUnits;
    }

    /**
     * @param totalSalesUnits the totalSalesUnits to set
     */
    public void setTotalSalesUnits(int totalSalesUnits) {
        this.totalSalesUnits = totalSalesUnits;
    }

    void performReturn() {
        InventoryItem ii = getInventory().get(getTransaction().getItemId());
        ii.setQuantity((ii.getQuantity()+getTransaction().getQuantity()));
        int quantity = getTransaction().getQuantity();
        int price = ii.getPrice()*quantity;
        totalSales -= price;
        totalSalesUnits -= quantity;   
    }
}
