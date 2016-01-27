package orderprocessing;

/**
 *
 * @author sab5964 and tjf5285
 */
public class Return extends Transaction{
    private int quantity;
    private int userId;
    private int price;
    
    public Return(int transactionId, int quantity, int userId, int SKU, int price, TransactionType transactionType) {
        super(transactionId, SKU, transactionType);
        this.quantity = quantity;
        this.userId = userId;
        this.price = price;
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
}