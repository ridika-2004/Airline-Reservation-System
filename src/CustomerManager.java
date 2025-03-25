import java.util.List;

class CustomerManager {
    Customer findCustomerByID(String userID, List<Customer> customers) {
        return customers.stream()
                .filter(c -> userID.equals(c.getUserID()))
                .findFirst()
                .orElse(null);
    }
}