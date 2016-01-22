/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orderprocessing;

/**
 *
 * @author Tim
 */
public class Sale extends Transaction{

    private int quantity;
    private int price;
    
    public Sale(int transactionId, int userId, int SKU, int quantity, int price) {
        super(transactionId, userId, SKU);
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    int getQuantity() {
        return quantity;  
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the price
     */
    public int getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(int price) {
        this.price = price;
    }
    
}
