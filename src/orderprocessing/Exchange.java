package orderprocessing;

/**
 *
 * @author sab5964 and tjf5285
 */
public class Exchange extends Transaction
{
    private int exchangeSKU;
    private int exchangeQuantity;
    private int exchangePrice;
    private int quantity;
    private int price;
    private TransactionType transType;
    
    public Exchange(int transactionId, int quantity, int exchangeQuantity, int exchangeSKU, int SKU, int price, int exchangePrice, int userId, TransactionType transactionType) {
        super(transactionId, SKU, transactionType);
        this.transType = transactionType;
        this.quantity = quantity;
        this.price = price;
        this.exchangePrice = exchangePrice;
        this.exchangeSKU = exchangeSKU;
        this.exchangeQuantity = exchangeQuantity;
    }

    @Override
    int getQuantity() {
        return quantity;
    }  
    
    @Override
    int getPrice() {
        return price;
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

    public int getExchangeSKU() {
        return exchangeSKU;
    }

    public int getExchangePrice() {
        return exchangePrice;
    }

    public TransactionType getTransType() {
        return transType;
    }

    @Override
    int getExchangeAmount() {
        return exchangeQuantity;
    }
}