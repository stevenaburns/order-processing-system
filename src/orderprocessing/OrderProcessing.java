package orderprocessing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
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

    public static void main(String[] args) throws InterruptedException, SQLException, IOException, SocketException {
        Connection con = null;
        try {
            con = getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(OrderProcessing.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Inventory inventory = getInventory(con, "inventory");
        AccountLedger ledger = new AccountLedger();
        ArrayList<Customer> customers = createCustomerList();
        
        TransactionController tc;
        ServerSocket serverSocket = new ServerSocket(9999);
        System.out.println("Socket open on port 9999...");

        //Listen for clients
        while (true) {
            //Accept any client requests and add them to the list
            Socket socket = serverSocket.accept();
            
            //Create new ServerListener instance to keep track of client
            ServerListener sl = new ServerListener(socket, inventory, ledger);
            sl.start();
            System.out.println("New client connected");
        }
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