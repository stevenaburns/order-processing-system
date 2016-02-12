package orderprocessing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sab5964 and tjf5285
 */
public class TransactionController implements Runnable{
    private final ArrayList<InventoryItem> inventory;
    private AccountLedger ledger;
    private Customer customer;
    private int totalSalesUnits;
    private int totalReturnUnits;
    private int totalReturns;
    private int totalSales;
    private final Transaction t;
    
    public TransactionController(Inventory inventory, AccountLedger ledger, Transaction t){
        this.inventory = inventory.getInventory();
        this.ledger = ledger;
        this.t = t;
    }
    
    public void performTransaction(Transaction t) throws InterruptedException {
       TransactionType type = t.getTransactionType();
       int SKU = t.getSKU();
       InventoryItem ii = inventory.get(SKU);
       
       synchronized(inventory){
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
        return ii.getQuantity() - t.getQuantity() >= 0; 
    }

    public void performOrder(Transaction t, InventoryItem ii){
        synchronized(ii){
            ii.setQuantity(ii.getQuantity() - t.getQuantity());
            double price = (double)t.getPrice()*t.getQuantity();
            ledger.setTotalSalesPrice(ledger.getTotalSalesPrice()+ t.getPrice()*t.getQuantity());
            ledger.setTotalSalesUnits(ledger.getTotalSalesUnits()+ t.getQuantity());
            //totalSalesUnits += t.getQuantity();
            System.out.println("\n" + t.getQuantity() + " units of " + ii.getName() + " were sold to " + customer.getFirstName() + " for " + price/100);
        }
    }
    
    public void performReturn(Transaction t, InventoryItem ii){
        synchronized(ii){
            ii.setQuantity(ii.getQuantity() + t.getQuantity());
            double price = (double)t.getPrice()*t.getQuantity();
            ledger.setTotalReturnPrice(ledger.getTotalReturnPrice()+ t.getPrice()*t.getQuantity());
            ledger.setTotalReturnUnits(ledger.getTotalReturnUnits()+ t.getQuantity());
            System.out.println("\n" + t.getQuantity() + " units of " + ii.getName() + " were returned by " + customer.getFirstName() + " for " + price/100);
        }
    }
    
    public void performExchange(Transaction t, InventoryItem ii) throws InterruptedException{
        
        InventoryItem exchangeItem = inventory.get(t.getExchangeSKU());
        ArrayList<InventoryItem> sortedItems = new ArrayList();
        sortedItems.add(ii);
        sortedItems.add(exchangeItem);
        
        Collections.sort(sortedItems, (InventoryItem item1, InventoryItem item2) -> {
            if (item1.getSKU()> item2.getSKU())
                return 1;
            if (item1.getSKU() < item2.getSKU())
                return -1;
            return 0;
        });
        
        //synchronized(ii){
        synchronized(sortedItems.get(0)){
            ii.setQuantity(ii.getQuantity() + t.getQuantity());

            //synchronized(inventory.get(t.getExchangeSKU())){
            synchronized(sortedItems.get(1)){
                exchangeItem.setQuantity(exchangeItem.getQuantity() - t.getExchangeAmount());
                System.out.println("\n" + t.getQuantity() + " units of " + ii.getName() + " were exchanged for " + t.getExchangeAmount() + " units of " + exchangeItem.getName() + " by " + customer.getFirstName());
            }
        }
    }
    
    public void performAdjustment(Transaction t, InventoryItem ii){
        synchronized(ii){
            ii.setCost(t.getCost());
            ii.setDescription(t.getDescription());
            ii.setName(t.getName());
            ii.setPrice(t.getPrice());
            ii.setQuantity(t.getQuantity());
            System.out.println("\nAn adjustment was made to " + ii.getName());
        }
    }
    
    
    public void displayInventory(){
        System.out.println("\nItemID\tName\tPrice\tQuantity\tDescription");
        
        for(InventoryItem i: inventory){
            System.out.println(i.getSKU() + "\t" + i.getName() + "\t" + (double)i.getPrice()/100 + "\t" + i.getQuantity() + "\t\t" + i.getDescription());
        }
    }
    
    public void displayTotals(){
        System.out.println("\n-----------------------\n\tTOTALS\n-----------------------");
        System.out.println("\nSales\n----------------\nTotal Units Sold: \t\t" + ledger.getTotalSalesUnits());
        System.out.println("Total Sales($): \t\t" + (double)ledger.getTotalSalesPrice()/100);
        
        System.out.println("\nReturns\n----------------\nTotal Units Returned: \t\t" + ledger.getTotalReturnUnits());
        System.out.println("Total Returns($): \t\t" + (double)ledger.getTotalReturnPrice()/100);

    }
    
    public synchronized void setCustomer(Customer customer){
        this.customer = customer;
    }

    public int getTotalSales(){
        return totalSales;
    }
    
    public synchronized ArrayList<InventoryItem> getInventory(){
        return inventory;
    }

    public int getTotalSalesUnits(){
        return totalSalesUnits;
    }

    @Override
    public void run() {
        try {
            performTransaction(t);
        } catch (InterruptedException ex) {
            Logger.getLogger(TransactionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}