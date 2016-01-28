package orderprocessing;

/**
 *
 * @author sab5964 and tjf5285
 */
public class Order extends Transaction{
    private int quantity;
    private int userId;
    private int price;
    
    public Order(int transactionId, int userId, int SKU, int quantity, int price, TransactionType transactionType){
        super(transactionId, SKU, transactionType);
        this.quantity = quantity;
        this.userId = userId;
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    int getQuantity() {
        return quantity;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    int getPrice() {
       return price;
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
