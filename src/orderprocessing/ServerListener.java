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
                orderData = clientInput.split(",");
                int transactionTypeNum = Integer.parseInt(orderData[5].trim());
                TransactionType t = null;
                
                System.out.println("TRANSACTION #" + transactionTypeNum);
                switch (transactionTypeNum) {
                    case 1:
                        t = TransactionType.ORDER;
                        break;
                    case 2:
                        t = TransactionType.RETURN;
                        break;
                    case 3:
                        t = TransactionType.EXCHANGE;
                        break;
                    case 4:
                        t = TransactionType.ADJUSTMENT;
                        break;
                }

                
                tc = new TransactionController(inventory, ledger, new Order(Integer.parseInt(orderData[0].trim()), Integer.parseInt(orderData[1].trim()), Integer.parseInt(orderData[2].trim()), Integer.parseInt(orderData[3].trim()), Integer.parseInt(orderData[4].trim()), t));
                tc.setCustomer(c);
                Thread transactionThread = new Thread(tc);
                transactionThread.start();
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ServerListener.class.getName()).log(Level.SEVERE, null, ex);
                }
                //System.out.println("HERE WE GO: " + tc.getReceiptInfo());
                PrintStream toClient = new PrintStream(socket.getOutputStream(), true);
                toClient.println(tc.getReceiptInfo());
                tc.displayInventory();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
