package orderprocessing;

/**
 *
 * @author sab5964 and tjf5285
 */
public class Exchange extends Transaction
{
    
    public Exchange(int transactionId, int amount, int userId, int SKU, TransactionType transactionType) {
        super(transactionId, SKU, transactionType);
    }

    @Override
    int getQuantity() {
        return 0;
    }  
    
    @Override
    int getPrice() {
        return 0;
    }

    @Override
    int getCost() {
        return 0;
    }

    @Override
    String getDescription() {
        return null;
    }

    @Override
    String getName() {
        return null;
    }
}
