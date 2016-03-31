/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orderprocessing;

import java.awt.BorderLayout;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 *
 * @author Timothy Flynn
 */
public class Client extends JFrame {

    public Client() {

        setLayout(new BorderLayout());
        JTextField test = new JTextField();
        add(test);

    }

    public static void main(String[] args) throws Exception {

        Client c = new Client();
        listenForOrders();

    }

    public static void listenForOrders() throws IOException {
        //Connect to server
        Socket socket = new Socket("localhost", 9999);

        //Initialize PrintStream object to communicate with server
        PrintStream printStream = new PrintStream(socket.getOutputStream());

        //Listen for keyboard inputs from user
        while (true) {
            //Prompt user and listen for input 
            System.out.println("i, customer, item, quantity, price, TransactionType.ORDER");
            Scanner scan = new Scanner(System.in);
            String message = scan.nextLine();

            //Send user message to server
            printStream.println(message);
            printStream.flush();
        }
    }
}
