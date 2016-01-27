package orderprocessing;

import java.util.ArrayList;

/**
 *
 * @author sab5964 and tjf5285
 */
public class TransactionController{
    private ArrayList<InventoryItem> inventory;
    private Customer customer;
    private Transaction transaction;
    private int totalSalesUnits;
    private int totalSales;
    
    public TransactionController(Inventory inventory){
        this.inventory = inventory.getInventory();
    }
    
    public void performTransaction(Transaction t){
       TransactionType type = t.getTransactionType();
       int SKU = t.getSKU();
       InventoryItem ii = inventory.get(SKU);
       
        if(checkAvailability(t)){
            switch(type){
                case ORDER:
                    ii.setQuantity(ii.getQuantity() - t.getQuantity());
                    System.out.println("\n" + t.getQuantity() + " units of " + ii.getName() + " were sold to " + customer.getFirstName() + " for " + (double)t.getPrice()*t.getQuantity()/100);
                    break;
                case RETURN:
                    ii.setQuantity(ii.getQuantity() + t.getQuantity());
                    System.out.println("\n" + t.getQuantity() + " units of " + ii.getName() + " were returned by " + customer.getFirstName() + " for " + (double)t.getPrice()*t.getQuantity()/100);
                    break;
                case ADJUSTMENT:
                    ii.setCost(t.getCost());
                    ii.setDescription(t.getDescription());
                    ii.setName(t.getName());
                    ii.setPrice(t.getPrice());
                    ii.setQuantity(t.getQuantity());
                    System.out.println("\nAn adjustment was made to " + ii.getName());
                    break;
            }
        }
    }
    
    private boolean checkAvailability(Transaction t){
        InventoryItem ii = inventory.get(t.getSKU());
        if(t.getTransactionType() == TransactionType.ADJUSTMENT || t.getTransactionType() == TransactionType.RETURN){
            return true;
        }
        return ii.getQuantity() - t.getQuantity() >= 0; //System.out.println("\nOnly " + ii.getQuantity() + " units of " + ii.getName() + " available, order requested " + getTransaction().getQuantity());
    }

    public void displayInventory(){
        System.out.println("\nItemID\tName\tPrice\tQuantity\tDescription");
        
        for(InventoryItem i: inventory){
            System.out.println(i.getSKU() + "\t" + i.getName() + "\t" + (double)i.getPrice()/100 + "\t" + i.getQuantity() + "\t\t" + i.getDescription());
        }
    }
    
    public void displayTotals(){
        System.out.println("\nTotal Units Sold: \t" + totalSalesUnits);
        System.out.println("Total Sales: \t\t" + (double)totalSales/100);
    }
    
    public void setCustomer(Customer customer){
        this.customer = customer;
    }

    public int getTotalSales(){
        return totalSales;
    }
    
    public ArrayList<InventoryItem> getInventory(){
        return inventory;
    }

    public int getTotalSalesUnits(){
        return totalSalesUnits;
    }
}