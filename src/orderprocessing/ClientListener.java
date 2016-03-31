/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orderprocessing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Timothy Flynn
 */
public class ClientListener extends Thread {

    private final Socket socket;

    ClientListener(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStreamReader inputStream = null;
        
        try {
            //Initialize inputStream from socket and created BufferedReader
            inputStream = new InputStreamReader(socket.getInputStream());
            BufferedReader buffReader = new BufferedReader(inputStream);
            
            //Listen for messages coming from the server
            while (true) {
                String s = buffReader.readLine();
                //Formatting to make chat appear nicely
                System.out.println("Order Processing System: " + s);
            }
        } 
        
        catch (IOException ex) {
            Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        finally {
            try {
                inputStream.close();
            } 
            
            catch (IOException ex) {
                Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}