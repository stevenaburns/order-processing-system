package orderprocessing;

/**
 *
 * @author sab5964 and tjf5285
 */
public class Exchange extends Transaction
{

    public Exchange(int transactionId, int amount, int userId, int itemId) {
        super(transactionId, userId, itemId);
    }
    
    int getQuantity(){return 0;};
    
}
