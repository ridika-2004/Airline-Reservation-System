import java.util.List;

public abstract class DisplayManager {
    abstract void displayRegisteredUsersForAllFlights();
    abstract void displayRegisteredUsersForASpecificFlight(String flightNum);
    abstract void displayFlightsRegisteredByOneUser(String userID);
    
    void displayHeaderForUsers(Flight flight, List<Customer> customers) {
        System.out.printf("\nDisplaying Registered Customers for Flight No. \"%-6s\"\n", flight.getFlightNumber());
        System.out.println("+------------+------------+----------------------+---------+");
        System.out.println("| SerialNum  |   UserID   | Passenger Names      | Age     |");
        System.out.println("+------------+------------+----------------------+---------+");
        for (int i = 0; i < customers.size(); i++) {
            System.out.printf("| %-10d | %-10s | %-20s | %-7s |%n", i + 1, customers.get(i).getUserID(), customers.get(i).getName(), customers.get(i).getAge());
        }
        System.out.println("+------------+------------+----------------------+---------+");
    }
}