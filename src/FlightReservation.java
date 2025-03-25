import java.util.List;
import java.util.Scanner;
import java.util.Iterator;

public class FlightReservation extends DisplayManager {

    @Override
    public void displayRegisteredUsersForAllFlights() {
        displayRegisteredUsersForAllFlight();
    }
    private final FlightManager flightManager = new FlightManager();
    private final CustomerManager customerManager = new CustomerManager();
    private final Scanner scanner = new Scanner(System.in);
    
    void bookFlight(String flightNo, int numOfTickets, String userID, List<Flight> flights, List<Customer> customers) {
        Flight flight = flightManager.findFlightByNumber(flightNo, flights);
        Customer customer = customerManager.findCustomerByID(userID, customers);
    
        if (flight == null || customer == null) {
            System.out.println("Invalid Flight/User ID!");
            return;
        }
    
        flight.setNoOfSeatsInTheFlight(flight.getNoOfSeats() - numOfTickets);
        if (!flight.getListOfRegisteredCustomersInAFlight().contains(customer)) {
            flight.addNewCustomerToFlight(customer);
        }
        customer.addNewFlightToCustomerList(flight);
        customer.numOfTicketsBookedByUser.add(numOfTickets);
    
        System.out.printf("You've booked %d tickets for Flight \"%s\"...\n", numOfTickets, flightNo.toUpperCase());
    }
    
    void cancelFlight(String userID, List<Customer> customers) {
        Customer customer = customerManager.findCustomerByID(userID, customers);
        if (customer == null || customer.getFlightsRegisteredByUser().isEmpty()) {
            System.out.println("No flights have been registered by you.");
            return;
        }
    
        displayFlightsRegisteredByOneUser(userID);
    
        System.out.print("Enter Flight Number to cancel: ");
        String flightNum = scanner.nextLine();
    
        System.out.print("Enter number of tickets to cancel: ");
        int numOfTickets = scanner.nextInt();
    
        Iterator<Flight> iterator = customer.getFlightsRegisteredByUser().iterator();
        while (iterator.hasNext()) {
            Flight flight = iterator.next();
            if (flightNum.equalsIgnoreCase(flight.getFlightNumber())) {
                int bookedTickets = customer.numOfTicketsBookedByUser.remove(0);
                numOfTickets = Math.min(numOfTickets, bookedTickets);
                flight.setNoOfSeatsInTheFlight(flight.getNoOfSeats() + numOfTickets);
                if (numOfTickets == bookedTickets) {
                    iterator.remove();
                } else {
                    customer.numOfTicketsBookedByUser.add(bookedTickets - numOfTickets);
                }
                System.out.println("Flight successfully cancelled.");
                return;
            }
        }
        System.out.println("Flight not found.");
    }

    public String toString(int serialNum, Flight flights, Customer customer) {
            return String.format("| %-5d| %-41s | %-9s | \t%-9d | %-21s | %-22s | %-10s  |   %-6sHrs |  %-4s  | %-10s |", serialNum, flights.getFlightSchedule(), flights.getFlightNumber(), customer.numOfTicketsBookedByUser.get(serialNum - 1), flights.getFromWhichCity(), flights.getToWhichCity(), flights.fetchArrivalTime(), flights.getFlightTime(), flights.getGate(), flightStatus(flights));
        }
    
        private String flightStatus(Flight flight) {
            // Example implementation, adjust as needed
            return flight.getNoOfSeats() > 0 ? "Available" : "Full";
    }

    @Override
    public void displayFlightsRegisteredByOneUser(String userID) {
        System.out.println();
        System.out.print("+------+-------------------------------------------+-----------+------------------+-----------------------+------------------------+---------------------------+-------------+--------+-----------------+\n");
        System.out.printf("| Num  | FLIGHT SCHEDULE\t\t\t   | FLIGHT NO |  Booked Tickets  | \tFROM ====>>       | \t====>> TO\t   | \t    ARRIVAL TIME       | FLIGHT TIME |  GATE  |  FLIGHT STATUS  |%n");
        System.out.print("+------+-------------------------------------------+-----------+------------------+-----------------------+------------------------+---------------------------+-------------+--------+-----------------+\n");
        for (Customer customer : AdminUser.customersCollection) {
            List<Flight> f = customer.getFlightsRegisteredByUser();
            int size = customer.getFlightsRegisteredByUser().size();
            if (userID.equals(customer.getUserID())) {
                for (int i = 0; i < size; i++) {
                    System.out.println(toString((i + 1), f.get(i), customer));
                    System.out.print("+------+-------------------------------------------+-----------+------------------+-----------------------+------------------------+---------------------------+-------------+--------+-----------------+\n");
                }
            }
        }
    }

    /*overloaded toString() method for displaying all users in a flight....*/

    public String toString(int serialNum, Customer customer, int index) {
        return String.format("%10s| %-10d | %-10s | %-32s | %-7s | %-27s | %-35s | %-23s |       %-7s  |", "", (serialNum + 1), customer.randomIDDisplay(customer.getUserID()), customer.getName(),
                customer.getAge(), customer.getEmail(), customer.getAddress(), customer.getPhone(), customer.numOfTicketsBookedByUser.get(index));
    }

    @Override
    public void displayHeaderForUsers(Flight flight, List<Customer> c) {
        System.out.printf("\n%65s Displaying Registered Customers for Flight No. \"%-6s\" %s \n\n", "+++++++++++++", flight.getFlightNumber(), "+++++++++++++");
        System.out.printf("%10s+------------+------------+----------------------------------+---------+-----------------------------+-------------------------------------+-------------------------+----------------+\n", "");
        System.out.printf("%10s| SerialNum  |   UserID   | Passenger Names                  | Age     | EmailID\t\t       | Home Address\t\t\t     | Phone Number\t       | Booked Tickets |%n", "");
        System.out.printf("%10s+------------+------------+----------------------------------+---------+-----------------------------+-------------------------------------+-------------------------+----------------+\n", "");
        int size = flight.getListOfRegisteredCustomersInAFlight().size();
        for (int i = 0; i < size; i++) {
            System.out.println(toString(i, c.get(i), flightIndex(c.get(i).flightsRegisteredByUser, flight)));
            System.out.printf("%10s+------------+------------+----------------------------------+---------+-----------------------------+-------------------------------------+-------------------------+----------------+\n", "");
        }
    }

    public void displayRegisteredUsersForAllFlight() {
        System.out.println();
        for (Flight flight : flightManager.getFlightList()) {
            List<Customer> c = flight.getListOfRegisteredCustomersInAFlight();
            int size = flight.getListOfRegisteredCustomersInAFlight().size();
            if (size != 0) {
                displayHeaderForUsers(flight, c);
            }
        }
    }

    int flightIndex(List<Flight> flightList, Flight flight) {
        int i = -1;
        for (Flight flight1 : flightList) {
            if (flight1.equals(flight)) {
                i = flightList.indexOf(flight1);
            }
        }
        return i;
    }

    @Override
    public void displayRegisteredUsersForASpecificFlight(String flightNum) {
        System.out.println();
        for (Flight flight : flightManager.getFlightList()) {
            List<Customer> c = flight.getListOfRegisteredCustomersInAFlight();
            if (flight.getFlightNumber().equalsIgnoreCase(flightNum)) {
                displayHeaderForUsers(flight, c);
            }
        }
    }


}
