package orderprocessing;

/**
 *
 * @author sab5964 and tjf5285
 */
public class Return extends Transaction
{

    public Return(int amount, int userId, int itemId) {
        super(userId, itemId);
    }
    
    int getQuantity(){return 0;};
    
}
