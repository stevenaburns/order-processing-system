package orderprocessing;

/**
 *
 * @author sab5964 and tjf5285
 */
public class Adjustment extends Transaction
{

    public Adjustment(int transactionId, int amount, int userId, int itemId) {
        super(transactionId, userId, itemId);
    }

    @Override
    int getQuantity(){return 0;};
    
}
