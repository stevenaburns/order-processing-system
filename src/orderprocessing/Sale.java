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
    private int userId;
    
    public Sale(int transactionId, int userId, int SKU, int quantity, int price, TransactionType transactionType) {
        super(transactionId,SKU, transactionType);
        this.quantity = quantity;
        this.price = price;
        this.userId = userId;
    }

    @Override
    int getQuantity() {
        return quantity;  
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    int getCost() {
        return 0;
    }

    @Override
    String getDescription() {
        return null;
    }

    @Override
    String getName() {
        return null;
    }

    @Override
    int getExchangeSKU() {
        return 0;
    }

    @Override
    int getExchangeAmount() {
        return 0;
    }
    
}
