package orderprocessing;

/**
 *
 * @author sab5964 and tjf5285
 */
public class Return extends Transaction
{
    private int quantity;
    
    public Return(int transactionId, int quantity, int userId, int itemId) {
        super(transactionId, userId, itemId);
        this.quantity = quantity;
    }

    @Override
    int getQuantity() {
        return quantity;
    }
    

    
}
