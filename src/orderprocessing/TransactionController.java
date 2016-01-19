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
    private Order order;
    private int totalSalesUnits;
    private int totalSales;
    
    public TransactionController(Inventory inventory)
    {
        this.inventory = inventory.getInventory();
    }
    
    public boolean checkAvailibility()
    {
        InventoryItem ii = getInventory().get(getOrder().getItemId());

        if(ii.getQuantity() - getOrder().getQuantity() < 0)
        {
            System.out.println("\nOnly " + ii.getQuantity() + " units of " + ii.getName() + " available, order requested " + getOrder().getQuantity());
            return false;
        }
        else
        {
            return true;
        }
    }
    
    public void performTransaction()
    {
        InventoryItem ii = getInventory().get(getOrder().getItemId());
        ii.setQuantity((ii.getQuantity()-getOrder().getQuantity()));
        int quantity = getOrder().getQuantity();
        int price = ii.getPrice()*quantity;
        totalSales += price;
        totalSalesUnits += quantity;
    }
    
    public void displayTransactionDetails()
    {
        InventoryItem ii = getInventory().get(getOrder().getItemId());
        System.out.println("\n" + getOrder().getQuantity() + " unit(s) of " + ii.getName() + " was sold to " + getCustomer().getFirstName() + " " + getCustomer().getLastName() + " for " + (double)ii.getPrice()/100*getOrder().getQuantity());
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
    public Order getOrder() {
        return order;
    }

    /**
     * @param order the order to set
     */
    public void setOrder(Order order) {
        this.order = order;
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
}
