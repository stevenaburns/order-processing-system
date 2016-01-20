package orderprocessing;

/**
 *
 * @author sab5964 and tjf5285
 */
public abstract class Transaction 
{
    private int userId;
    private int itemId;

    public Transaction(int userId, int itemId)
    {
        this.userId = userId;
        this.itemId = itemId;
    }
    
    abstract int getQuantity();
    
    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the itemId
     */
    public int getItemId() {
        return itemId;
    }

    /**
     * @param itemId the itemId to set
     */
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}
