package orderprocessing;

/**
 *
 * @author sab5964 and tjf5285
 */
public class Customer {
    private String firstName;
    private String lastName;
    private String address;
    private String ZIP;
    private String state;
    private String city;
    private int customerId;

    public Customer(int customerId, String firstName, String lastName, String address, String ZIP, String state, String city){
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.ZIP = ZIP;
        this.state = state;
        this.city = city;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZIP() {
        return ZIP;
    }

    public void setZIP(String ZIP) {
        this.ZIP = ZIP;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCustomerId() {
        return customerId;
    }
}