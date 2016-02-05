package orderprocessing;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sab5964 and tjf5285
 */
public class TransactionController implements Runnable{
    private ArrayList<InventoryItem> inventory;
    private Customer customer;
    private int totalSalesUnits;
    private int totalReturnUnits;
    private int totalReturns;
    private int totalSales;
    private Transaction t;
    
    public TransactionController(Inventory inventory, Transaction t){
        this.inventory = inventory.getInventory();
        this.t = t;
    }
    
    public void performTransaction(Transaction t){
       TransactionType type = t.getTransactionType();
       int SKU = t.getSKU();
       InventoryItem ii = inventory.get(SKU);
       
        if(checkAvailability(t)){
            switch(type){
                case ORDER:
                    performOrder(t, ii);
                    break;
                case RETURN:
                    performReturn(t, ii);
                    break;
                case ADJUSTMENT:
                    performAdjustment(t, ii);
                    break;
                case EXCHANGE:
                    performExchange(t, ii);
                    break;
            }
        }
        else{
            System.out.println("\nInsufficient inventory to complete " + t.getTransactionType());
            if(t.getTransactionType() == TransactionType.EXCHANGE){
                System.out.println("Exchange for " + t.getExchangeAmount() + " items of " + inventory.get(t.getExchangeSKU()).getName() + " failed, only " +  inventory.get(t.getExchangeSKU()).getQuantity() + " available");
            }
            else{
                System.out.println(t.getQuantity() + " units of " + ii.getName() + " requested, only " + ii.getQuantity() + " available");
            }
        }
    }
    
    private boolean checkAvailability(Transaction t){
        InventoryItem ii = inventory.get(t.getSKU());
        if(t.getTransactionType() == TransactionType.ADJUSTMENT || t.getTransactionType() == TransactionType.RETURN){
            return true;
        }
        
        if(t.getTransactionType() == TransactionType.EXCHANGE){
            InventoryItem exchangeItem = inventory.get(t.getExchangeSKU());
            if(exchangeItem.getQuantity() - t.getExchangeAmount() < 0){
                return false;
            }
            else{
                return true;
            }
        }
        return ii.getQuantity() - t.getQuantity() >= 0; //System.out.println("\nOnly " + ii.getQuantity() + " units of " + ii.getName() + " available, order requested " + getTransaction().getQuantity());
    }

    public void performOrder(Transaction t, InventoryItem ii){
        ii.setQuantity(ii.getQuantity() - t.getQuantity());
        double price = (double)t.getPrice()*t.getQuantity();
        totalSales+= price;
        totalSalesUnits += t.getQuantity();
        System.out.println("\n" + t.getQuantity() + " units of " + ii.getName() + " were sold to " + customer.getFirstName() + " for " + price/100);
    }
    
    public void performReturn(Transaction t, InventoryItem ii){
        ii.setQuantity(ii.getQuantity() + t.getQuantity());
        double price = (double)t.getPrice()*t.getQuantity();
        totalReturns += price;
        totalReturnUnits += t.getQuantity();
        System.out.println("\n" + t.getQuantity() + " units of " + ii.getName() + " were returned by " + customer.getFirstName() + " for " + price/100);
    }
    
    public void performExchange(Transaction t, InventoryItem ii){
        ii.setQuantity(ii.getQuantity() + t.getQuantity());
        InventoryItem exchangeItem = inventory.get(t.getExchangeSKU());
        exchangeItem.setQuantity(exchangeItem.getQuantity() - t.getExchangeAmount());
        System.out.println("\n" + t.getQuantity() + " units of " + ii.getName() + " were exchanged for " + t.getExchangeAmount() + " units of " + exchangeItem.getName() + " by " + customer.getFirstName());
    }
    
    public void performAdjustment(Transaction t, InventoryItem ii){
        ii.setCost(t.getCost());
        ii.setDescription(t.getDescription());
        ii.setName(t.getName());
        ii.setPrice(t.getPrice());
        ii.setQuantity(t.getQuantity());
        System.out.println("\nAn adjustment was made to " + ii.getName());
    }
    
    
    public void displayInventory(){
        System.out.println("\nItemID\tName\tPrice\tQuantity\tDescription");
        
        for(InventoryItem i: inventory){
            System.out.println(i.getSKU() + "\t" + i.getName() + "\t" + (double)i.getPrice()/100 + "\t" + i.getQuantity() + "\t\t" + i.getDescription());
        }
    }
    
    public void displayTotals(){
        System.out.println("\n-----------------------\n\tTOTALS\n-----------------------");
        System.out.println("\nSales\n----------------\nTotal Units Sold: \t" + totalSalesUnits);
        System.out.println("Total Sales: \t\t" + (double)totalSales/100);
        System.out.println("\nReturns\n----------------\nTotal Units Returned: \t" + totalReturnUnits);
        System.out.println("Total Returns: \t\t" + (double)totalReturns/100);
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

    @Override
    public void run() {
        performTransaction(t);
    }
}