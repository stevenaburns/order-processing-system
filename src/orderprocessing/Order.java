package orderprocessing;

/**
 *
 * @author sab5964 and tjf5285
 */
public class Order extends Transaction
{
    private int quantity;
    
    public Order(int transactionId, int userId, int itemId, int quantity) {
        super(transactionId, userId, itemId);
        this.quantity = quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    int getQuantity() {
        return quantity;
    }
    
}
