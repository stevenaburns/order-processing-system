package orderprocessing;

/**
 *
 * @author sab5964 and tjf5285
 */
public class Return extends Transaction
{
    private int quantity;
    
    public Return(int quantity, int userId, int itemId) {
        super(userId, itemId);
        this.quantity = quantity;
    }

    @Override
    int getQuantity() {
        return quantity;
    }
    

    
}
