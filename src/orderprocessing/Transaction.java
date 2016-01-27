package orderprocessing;

/**
 *
 * @author sab5964 and tjf5285
 */
public abstract class Transaction{
    private int SKU;
    private int transactionId;
    private TransactionType transactionType;

    public Transaction(int transactionId, int SKU, TransactionType transactionType)
    {
        this.transactionId = transactionId;
        this.SKU = SKU;
        this.transactionType = transactionType;
    }
    
    abstract int getQuantity();
    abstract int getCost();
    abstract int getPrice();
    abstract String getDescription();
    abstract String getName();
    
    public int getSKU(){
        return SKU;
    }

    public int getTransactionId(){
        return transactionId;
    }

    public void setTransactionId(int transactionId){
        this.transactionId = transactionId;
    }

    public TransactionType getTransactionType(){
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType){
        this.transactionType = transactionType;
    }
}