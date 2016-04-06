package orderprocessing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sab5964 and tjf5285
 */
public class TransactionController implements Runnable {

    private final ArrayList<InventoryItem> inventory;
    private final AccountLedger ledger;
    private Customer customer;
    private int totalSalesUnits;
    private int totalReturnUnits;
    private int totalReturns;
    private int totalSales;
    private final Transaction t;
    private String receipt;
    int threadNum;

    public TransactionController(Inventory inventory, AccountLedger ledger, Transaction t) {
        this.inventory = inventory.getInventory();
        this.ledger = ledger;
        this.t = t;
        //this.c = c;
    }

    public void performTransaction(Transaction t) throws InterruptedException, SQLException {
        TransactionType type = t.getTransactionType();
        int SKU = t.getSKU();
        InventoryItem ii = inventory.get(SKU);

        synchronized (inventory) {
            if (checkAvailability(t)) {
                switch (type) {
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
            } else {
                //c.close();
                //setReceiptInfo("\nInsufficient inventory to complete " + t.getTransactionType());
                System.out.println("\nInsufficient inventory to complete " + t.getTransactionType());
                //System.out.println("Connection closed");
                if (t.getTransactionType() == TransactionType.EXCHANGE) {
                    System.out.println("Exchange for " + t.getExchangeAmount() + " items of " + inventory.get(t.getExchangeSKU()).getName() + " failed, only " + inventory.get(t.getExchangeSKU()).getQuantity() + " available");
                } else {
                    setReceiptInfo("Transaction failed: " + t.getQuantity() + " units of " + ii.getName() + " requested, only " + ii.getQuantity() + " available");
                    System.out.println(t.getQuantity() + " units of " + ii.getName() + " requested, only " + ii.getQuantity() + " available");
                }
            }
        }
    }

    private boolean checkAvailability(Transaction t) {
        InventoryItem ii = inventory.get(t.getSKU());

        if (t.getTransactionType() == TransactionType.ADJUSTMENT || t.getTransactionType() == TransactionType.RETURN) {
            return true;
        }

        if (t.getTransactionType() == TransactionType.EXCHANGE) {
            InventoryItem exchangeItem = inventory.get(t.getExchangeSKU());
            if (exchangeItem.getQuantity() - t.getExchangeAmount() < 0) {
                return false;
            } else {
                return true;
            }
        }
        return ii.getQuantity() - t.getQuantity() >= 0;
    }

    public void performOrder(Transaction t, InventoryItem ii) throws SQLException {
        synchronized (ii) {
            ii.setQuantity(ii.getQuantity() - t.getQuantity());
            int quan = ii.getQuantity();

            try (Connection c = getConnection()) {
                Statement stmt;
                String query = "UPDATE inventory SET units = " + quan + " WHERE NAME ='" + ii.getName() + "';";
                System.out.println(query);
                stmt = c.createStatement();
                stmt.executeUpdate(query);
                c.close();
            }
            System.out.println("Connection Closed");
            double price = (double) t.getPrice() * t.getQuantity();
            setReceiptInfo(t.getQuantity() + " units of " + ii.getName() + " were sold to " + customer.getFirstName() + " for " + price / 100 + "\n");
            System.out.println(t.getQuantity() + " units of " + ii.getName() + " were sold to " + customer.getFirstName() + " for " + price / 100 + "\n");
        }

        synchronized (ledger) {
            ledger.setTotalSalesPrice(ledger.getTotalSalesPrice() + t.getPrice() * t.getQuantity());
            ledger.setTotalSalesUnits(ledger.getTotalSalesUnits() + t.getQuantity());
        }
    }

    public void performReturn(Transaction t, InventoryItem ii) throws SQLException {
        synchronized (ii) {
            ii.setQuantity(ii.getQuantity() + t.getQuantity());
            int quan = ii.getQuantity();
            double price = (double) t.getPrice() * t.getQuantity();

            try (Connection c = getConnection()) {
                Statement stmt;
                String query = "UPDATE inventory SET units = " + quan + " WHERE NAME ='" + ii.getName() + "';";
                System.out.println(query);
                stmt = c.createStatement();
                stmt.executeUpdate(query);
                c.close();
            }
            System.out.println("Connection closed");
            setReceiptInfo(t.getQuantity() + " units of " + ii.getName() + " were returned by " + customer.getFirstName() + " for " + price / 100 + "\n");
            System.out.println(t.getQuantity() + " units of " + ii.getName() + " were returned by " + customer.getFirstName() + " for " + price / 100 + "\n");
        }

        synchronized (ledger) {
            ledger.setTotalReturnPrice(ledger.getTotalReturnPrice() + t.getPrice() * t.getQuantity());
            ledger.setTotalReturnUnits(ledger.getTotalReturnUnits() + t.getQuantity());
        }
    }

    public void performExchange(Transaction t, InventoryItem ii) throws InterruptedException, SQLException {

        InventoryItem exchangeItem = inventory.get(t.getExchangeSKU());
        ArrayList<InventoryItem> sortedItems = new ArrayList();
        sortedItems.add(ii);
        sortedItems.add(exchangeItem);

        Collections.sort(sortedItems, (InventoryItem item1, InventoryItem item2) -> {
            if (item1.getSKU() > item2.getSKU()) {
                return 1;
            }
            if (item1.getSKU() < item2.getSKU()) {
                return -1;
            }
            return 0;
        });

        //synchronized(ii){
        synchronized (sortedItems.get(0)) {
            ii.setQuantity(ii.getQuantity() + t.getQuantity());
            int quan = ii.getQuantity();
            try (Connection c = getConnection()) {
                Statement stmt;
                String query = "UPDATE inventory SET units = " + quan + " WHERE NAME ='" + ii.getName() + "';";
                System.out.println(query);
                stmt = c.createStatement();
                stmt.executeUpdate(query);

                //synchronized(inventory.get(t.getExchangeSKU())){
                synchronized (sortedItems.get(1)) {
                    exchangeItem.setQuantity(exchangeItem.getQuantity() - t.getExchangeAmount());
                    quan = exchangeItem.getQuantity();
                    query = "UPDATE inventory SET units = " + quan + " WHERE NAME ='" + exchangeItem.getName() + "';";
                    System.out.println(query);
                    stmt = c.createStatement();
                    stmt.executeUpdate(query);
                    System.out.println(t.getQuantity() + " units of " + ii.getName() + " were exchanged for " + t.getExchangeAmount() + " units of " + exchangeItem.getName() + " by " + customer.getFirstName());
                    setReceiptInfo(t.getQuantity() + " units of " + ii.getName() + " were exchanged for " + t.getExchangeAmount() + " units of " + exchangeItem.getName() + " by " + customer.getFirstName());
                    c.close();
                }
            }
            
            System.out.println("Connection closed\n");
        }
    }

    public void performAdjustment(Transaction t, InventoryItem ii) throws SQLException {
        synchronized (ii) {
            
            try (Connection c = getConnection()) {
                ii.setCost(t.getCost());
                ii.setDescription(t.getDescription());
                ii.setName(t.getName());
                ii.setPrice(t.getPrice());
                ii.setQuantity(t.getQuantity());
                Statement stmt;
                String query = "UPDATE inventory SET units = " + ii.getQuantity() + ", cost = " + ii.getCost() + ", price = " + ii.getPrice() + ", name = '" + ii.getName() + "', description = '" + ii.getDescription() + "'WHERE NAME ='" + ii.getName() + "';";
                System.out.println(query);
                stmt = c.createStatement();
                stmt.executeUpdate(query);
                System.out.println("\nAn adjustment was made to " + ii.getName());
                c.close();
                System.out.println("Connection closed\n");
            }
        }
    }

    public void displayInventory() {
        System.out.println("\nItemID\tName\tPrice\tQuantity\tDescription");

        for (InventoryItem i : inventory) {
            System.out.println(i.getSKU() + "\t" + i.getName() + "\t" + (double) i.getPrice() / 100 + "\t" + i.getQuantity() + "\t\t" + i.getDescription());
        }
    }
    
    public String getReceiptInfo()
    {
        return receipt;
    }
    
    public void setReceiptInfo(String receipt){
        System.out.println("INSIDE SETRECEIPTINFO");
        this.receipt = receipt;
    }

    public void displayTotals() {
        System.out.println("\n-----------------------\n\tTOTALS\n-----------------------");
        System.out.println("\nSales\n----------------\nTotal Units Sold: \t\t" + ledger.getTotalSalesUnits());
        System.out.println("Total Sales($): \t\t" + (double) ledger.getTotalSalesPrice() / 100);

        System.out.println("\nReturns\n----------------\nTotal Units Returned: \t\t" + ledger.getTotalReturnUnits());
        System.out.println("Total Returns($): \t\t" + (double) ledger.getTotalReturnPrice() / 100);

    }

    public synchronized void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getTotalSales() {
        return totalSales;
    }

    public synchronized ArrayList<InventoryItem> getInventory() {
        return inventory;
    }

    public int getTotalSalesUnits() {
        return totalSalesUnits;
    }

    public static Connection getConnection() throws SQLException {
        Connection conn = null;

        Properties connectionProps = new Properties();
        connectionProps.put("user", "root");
        connectionProps.put("password", "");

        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/order_processing", connectionProps);

        System.out.println("Connected to database");
        return conn;
    }

    @Override
    public void run() {
        try {
            //Thread.sleep(10);
            performTransaction(t);
        } catch (InterruptedException ex) {
            Logger.getLogger(TransactionController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TransactionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}