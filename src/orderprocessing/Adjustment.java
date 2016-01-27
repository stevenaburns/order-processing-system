package orderprocessing;

/**
 *
 * @author sab5964 and tjf5285
 */
public class Adjustment extends Transaction{
    private String name;
    private String description;
    private int cost;
    private int price;
    private int quantity;
    
    public Adjustment(int transactionId, int SKU, String name, String description, int cost, int price, int quantity, TransactionType transactionType) {
        super(transactionId, SKU, transactionType);
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    int getQuantity(){
        return quantity;
    };

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}