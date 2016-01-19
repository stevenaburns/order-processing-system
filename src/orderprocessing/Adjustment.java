package orderprocessing;

/**
 *
 * @author sab5964 and tjf5285
 */
public class Adjustment extends Transaction
{

    public Adjustment(int amount, int userId, int itemId) {
        super(userId, itemId);
    }
    
}
