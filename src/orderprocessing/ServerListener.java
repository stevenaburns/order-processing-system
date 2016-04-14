/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orderprocessing;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;

/**
 *
 * @author Timothy Flynn
 */
public class ServerListener extends Thread {

    //Create variables for input/output variables related to socket
    private final Socket socket;
    private BufferedReader input;
    private PrintStream output;
    private String clientInput;
    private TransactionController tc;
    private String[] orderData;
    private Inventory inventory;
    private AccountLedger ledger;
    private int num;
    private Customer c = new Customer(0, "Tim", "Flynn", "101 Easy St.", "16802", "PA", "University Park");

    public ServerListener(Socket socket, Inventory inventory, AccountLedger ledger) {
        this.socket = socket;
        this.inventory = inventory;
        this.ledger = ledger;
    }

    @Override
    public void run() {
        try {
            while (true) {
                //Read input from the client
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                clientInput = input.readLine();
                System.out.println(clientInput);
                orderData = clientInput.split(",");
                
                int transactionTypeNum = Integer.parseInt(orderData[orderData.length-1].trim());
                TransactionType t = null;
                
                System.out.println("TRANSACTION #" + transactionTypeNum);
                System.out.println(transactionTypeNum + " :TTN");
                switch (transactionTypeNum) {
                    case 1:
                        t = TransactionType.ORDER;
                        tc = new TransactionController(inventory, ledger, new Order(Integer.parseInt(orderData[0].trim()), Integer.parseInt(orderData[1].trim()), Integer.parseInt(orderData[2].trim()), Integer.parseInt(orderData[3].trim()), Integer.parseInt(orderData[4].trim()), t));
                        break;
                    case 2:
                        t = TransactionType.RETURN;
                        //Order(int transactionId, int userId, int SKU, int quantity, int price, TransactionType transactionType)
                        //Return(int transactionId, int quantity, int userId, int SKU, int price, TransactionType transactionType) {
                        tc = new TransactionController(inventory, ledger, new Return(Integer.parseInt(orderData[0].trim()), Integer.parseInt(orderData[1].trim()), Integer.parseInt(orderData[2].trim()), Integer.parseInt(orderData[3].trim()), Integer.parseInt(orderData[4].trim()), t));
                        break;
                    case 3:
                        t = TransactionType.EXCHANGE;
                        //public Exchange(int transactionId, int quantity, int exchangeQuantity, int exchangeSKU, int SKU, int price, int exchangePrice, int userId, TransactionType transactionType)
                        tc = new TransactionController(inventory, ledger, new Exchange(Integer.parseInt(orderData[0].trim()), Integer.parseInt(orderData[1].trim()), Integer.parseInt(orderData[2].trim()), Integer.parseInt(orderData[3].trim()), Integer.parseInt(orderData[4].trim()), Integer.parseInt(orderData[5].trim()), Integer.parseInt(orderData[6].trim()), Integer.parseInt(orderData[7].trim()), t));
                        break;
                    case 4:
                        System.out.println("Here we are");
                        t = TransactionType.ADJUSTMENT;
                        tc = new TransactionController(inventory, ledger, new Adjustment(Integer.parseInt(orderData[0].trim()), 
                                Integer.parseInt(orderData[1].trim()), orderData[2].trim(), 
                                orderData[3].trim(), Integer.parseInt(orderData[4].trim()),
                                Integer.parseInt(orderData[5].trim()),Integer.parseInt(orderData[6].trim()), t));
                        System.out.println(clientInput);
                        break;
                }
               
                tc.setCustomer(c);
                Thread transactionThread = new Thread(tc);
                transactionThread.start();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ServerListener.class.getName()).log(Level.SEVERE, null, ex);
                }
                PrintStream toClient = new PrintStream(socket.getOutputStream(), true);
                toClient.println(tc.getReceiptInfo());
                tc.displayInventory();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
