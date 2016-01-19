package orderprocessing;

/**
 *
 * @author sab5964 and tjf5285
 */
public class Order extends Transaction
{
    private int quantity;
    
    public Order(int userId, int itemId, int quantity) {
        super(userId, itemId);
        this.quantity = quantity;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
}
