package orderprocessing;

/**
 *
 * @author sab5964 and tjf5285
 */
public abstract class Transaction 
{
    private int userId;
    private int itemId;
    private int transactionId;

    public Transaction(int transactionId, int userId, int itemId)
    {
        this.transactionId = transactionId;
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

    /**
     * @return the transactionId
     */
    public int getTransactionId() {
        return transactionId;
    }

    /**
     * @param transactionId the transactionId to set
     */
    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }
}
