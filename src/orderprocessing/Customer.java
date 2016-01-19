package orderprocessing;

/**
 *
 * @author sab5964 and tjf5285
 */
public class Customer 
{
    private String firstName;
    private String lastName;
    private String address;
    private String ZIP;
    private String state;
    private String city;
    private int customerId;

    /**
     * Constructor
     */
    public Customer(int customerId, String firstName, String lastName, String address, String ZIP, String state, String city)
    {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.ZIP = ZIP;
        this.state = state;
        this.city = city;
    }
    
    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the ZIP
     */
    public String getZIP() {
        return ZIP;
    }

    /**
     * @param ZIP the ZIP to set
     */
    public void setZIP(String ZIP) {
        this.ZIP = ZIP;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the customerId
     */
    public int getCustomerId() {
        return customerId;
    }
}
