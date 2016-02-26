package orderprocessing;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sab5964 and tjf5285
 */
public class OrderProcessing {

    public static void main(String[] args) throws InterruptedException, SQLException {
        Connection con = null;
        try {
            con = getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(OrderProcessing.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Inventory inventory = getInventory(con, "inventory");
        AccountLedger ledger = new AccountLedger();
        ArrayList<Customer> customers = createCustomerList();

        TransactionController tc, tc2, tc3, tc4;

        int iterations = 10000;

        for (int i = 0; i < iterations; i++) {
            Random rand = new Random();
            Random rand2 = new Random();
            Random rand3 = new Random();
            Random rand4 = new Random();
            Random time = new Random();
            Random tranType = new Random();

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            String timeStamp = dateFormat.format(cal.getTime());

            int customer = rand.nextInt(customers.size());
            int item = rand2.nextInt(inventory.getInventory().size());
            int item2 = rand4.nextInt(inventory.getInventory().size());
            int quantity = rand3.nextInt(5) + 1;
            int price = inventory.getInventory().get(item).getPrice();

            tc = new TransactionController(inventory, ledger, new Order(i, customer, item, quantity, price, TransactionType.ORDER));
            tc2 = new TransactionController(inventory, ledger, new Exchange(i, quantity, quantity, item, item2, price, price * 2, customer, TransactionType.EXCHANGE));
            tc3 = new TransactionController(inventory, ledger, new Return(i, quantity, customer, item, price, TransactionType.RETURN));
            tc4 = new TransactionController(inventory, ledger, new Adjustment(i, 2, "Jeans", timeStamp, 2599, 3999, 1000, TransactionType.ADJUSTMENT));

            tc.setCustomer(customers.get(customer));
            tc2.setCustomer(customers.get(customer));
            tc3.setCustomer(customers.get(customer));
            tc4.setCustomer(customers.get(customer));
            //tc.threadNum;

            Thread orderThread = new Thread(tc);
            Thread exchangeThread = new Thread(tc2);
            Thread returnThread = new Thread(tc3);
            Thread adjustmentThread = new Thread(tc4);

            Thread.sleep(time.nextInt(25));
            
           int tran = tranType.nextInt(10);
            if(tran <= 6){
                orderThread.start();
            }
            if(tran == 7){
                returnThread.start();
            }
            if(tran == 8){
                exchangeThread.start();
            }
            if(tran == 9){
                adjustmentThread.start();
            }
        }
        //tc.displayInventory();
        //tc.displayTotals();
    }
    
    public static Inventory createInventory(){
        //Sample inventory                           |ID    |Name       |Description                            |Cost   |Price  |Units
        InventoryItem socks =       new InventoryItem(0,    "Socks",    "Red Harry Potter Griffindor socks",    599,    899,    200);
        InventoryItem shoes =       new InventoryItem(1,    "Shoes",    "Black Jordan sneakers",                4999,   499,    200);
        InventoryItem jeans =       new InventoryItem(2,    "Jeans",    "Blue Levi's jeans size 32",            1399,   2499,   200);
        InventoryItem hat =         new InventoryItem(3,    "Hat",      "Black and grey wool winter hat",       799,    1599,   200);
        InventoryItem gloves =      new InventoryItem(4,    "Gloves",   "Blue wool winter gloves",              499,    1299,   200);
        InventoryItem sweatshirt =  new InventoryItem(5,    "Shirt",    "Purple cotton t-shirt",                1999,   3999,   200);
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
    
    public static Connection getConnection() throws SQLException {
        Connection conn = null;

        Properties connectionProps = new Properties();
        connectionProps.put("user", "root");
        connectionProps.put("password", "");

        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/order_processing",connectionProps);

        System.out.println("Connected to database");
        return conn;
    }
    
    public static Inventory getInventory(Connection con, String dbName) throws SQLException {

    Inventory storeInventory = new Inventory();
    Statement stmt = null;
    String query = "select INVENTORYID, NAME, DESCRIPTION, PRICE, COST, UNITS from " + dbName;
    try {
        stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        System.out.println("\nID\tName\tUnits\tCost\tPrice\tDescription");
        while (rs.next()) {
            String name = rs.getString("NAME");
            String description = rs.getString("DESCRIPTION");
            int price = rs.getInt("PRICE");
            int cost = rs.getInt("COST");
            int units = rs.getInt("UNITS");
            int inventoryId = rs.getInt("INVENTORYID");

            System.out.println(inventoryId + "\t" + name + "\t" + units + "\t" + cost + "\t" + price + "\t" + description);
            //Add the items to the inventory
            InventoryItem dbItem = new InventoryItem(inventoryId, name, description, cost, price, units);
            storeInventory.AddInventoryItem(dbItem);
        }
    } catch (SQLException e ) {
        System.out.println(e);
        //JDBCTutorialUtilities.printSQLException(e);
    } finally {
        if (stmt != null) { stmt.close(); }
    }
    //con.close();
    System.out.println("\n");
    return storeInventory;
}
}