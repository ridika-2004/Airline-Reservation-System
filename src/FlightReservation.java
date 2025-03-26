import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class FlightReservation {

    //        ************************************************************ Fields ************************************************************
    Flight flight = new Flight(null,null,0,null,null,null);
    int flightIndexInFlightList;

    void bookFlight(String flightNo, int numOfTickets, String userID) {
        for (Flight f1 : flight.getFlightList()) {
            if (!flightNo.equalsIgnoreCase(f1.getFlightNumber())) continue;
            
            Customer customer = Customer.customerCollection.stream().filter(c -> userID.equals(c.getUserID())).findFirst().orElse(null);
            
            if (customer == null) {
                System.out.println("Invalid Flight Number...! No flight with the ID \"" + flightNo + "\" was found...");
                return;
            }
    
            f1.setNoOfSeatsInTheFlight(f1.getNoOfSeats() - numOfTickets);
    
            if (!f1.isCustomerAlreadyAdded(f1.getListOfRegisteredCustomersInAFlight(), customer)) {
                f1.addNewCustomerToFlight(customer);
            }
    
            if (isFlightAlreadyAddedToCustomerList(customer.flightsRegisteredByUser, f1)) {
                addNumberOfTicketsToAlreadyBookedFlight(customer, numOfTickets);
                int flightIdx = new DisplayFlightClass().flightIndex(flight.getFlightList(), flight);
                if (flightIdx != -1) {
                    customer.addExistingFlightToCustomerList(flightIdx, numOfTickets);
                }
            } else {
                customer.addNewFlightToCustomerList(f1);
                addNumberOfTicketsForNewFlight(customer, numOfTickets);
            }
            
            System.out.printf("\n %50s You've booked %d tickets for Flight \"%5s\"...", "", numOfTickets, flightNo.toUpperCase());
            return;
        }
    
        System.out.println("Invalid Flight Number...! No flight with the ID \"" + flightNo + "\" was found...");
    }

    void cancelFlight(String userID) {
        Scanner read = new Scanner(System.in);
        Customer customer = Customer.customerCollection.stream()
                .filter(c -> userID.equals(c.getUserID()))
                .findFirst()
                .orElse(null);
    
        if (customer == null || customer.getFlightsRegisteredByUser().isEmpty()) {
            System.out.println("No Flight Has been Registered by you.");
            return;
        }
    
        System.out.printf("%50s %s Here is the list of all the Flights registered by you %s", " ", "++++++++++++++", "++++++++++++++");
        displayFlightsRegisteredByOneUser(userID);
        
        System.out.print("Enter the Flight Number of the Flight you want to cancel: ");
        String flightNum = read.nextLine();
        System.out.print("Enter the number of tickets to cancel: ");
        int numOfTickets = read.nextInt();
        
        Iterator<Flight> flightIterator = customer.getFlightsRegisteredByUser().iterator();
        int index = 0;
        boolean isFound = false;
        
        while (flightIterator.hasNext()) {
            Flight flight = flightIterator.next();
            if (!flightNum.equalsIgnoreCase(flight.getFlightNumber())) {
                index++;
                continue;
            }
            
            isFound = true;
            int numOfTicketsForFlight = customer.getNumOfTicketsBookedByUser().get(index);
            
            while (numOfTickets > numOfTicketsForFlight) {
                System.out.print("ERROR!!! Number of tickets cannot be greater than " + numOfTicketsForFlight + " for this flight. Please enter the number of tickets again: ");
                numOfTickets = read.nextInt();
            }
            
            int ticketsToBeReturned = flight.getNoOfSeats();
            if (numOfTicketsForFlight == numOfTickets) {
                ticketsToBeReturned += numOfTicketsForFlight;
                customer.numOfTicketsBookedByUser.remove(index);
                flightIterator.remove();
            } else {
                ticketsToBeReturned += numOfTickets;
                customer.numOfTicketsBookedByUser.set(index, numOfTicketsForFlight - numOfTickets);
            }
            
            flight.setNoOfSeatsInTheFlight(ticketsToBeReturned);
            break;
        }
        
        if (!isFound) {
            System.out.println("ERROR!!! Couldn't find Flight with ID \"" + flightNum.toUpperCase() + "\".....");
        }
    }

    void addNumberOfTicketsToAlreadyBookedFlight(Customer customer, int numOfTickets) {
        int newNumOfTickets = customer.numOfTicketsBookedByUser.get(flightIndexInFlightList) + numOfTickets;
        customer.numOfTicketsBookedByUser.set(flightIndexInFlightList, newNumOfTickets);
    }

    void addNumberOfTicketsForNewFlight(Customer customer, int numOfTickets) {
        customer.numOfTicketsBookedByUser.add(numOfTickets);
    }

    boolean isFlightAlreadyAddedToCustomerList(List<Flight> flightList, Flight flight) {
        boolean addedOrNot = false;
        for (Flight flight1 : flightList) {
            if (flight1.getFlightNumber().equalsIgnoreCase(flight.getFlightNumber())) {
                this.flightIndexInFlightList = flightList.indexOf(flight1);
                addedOrNot = true;
                break;
            }
        }
        return addedOrNot;
    }

    public void displayFlightsRegisteredByOneUser(String userID) {
        new DisplayFlightClass().displayFlightsRegisteredByOneUser(userID);
    }


    public void displayHeaderForUsers(Flight flight, List<Customer> c) {
        new DisplayFlightClass().displayHeaderForUsers(flight, c);
    }


    public void displayRegisteredUsersForAllFlight() {
        new DisplayFlightClass().displayRegisteredUsersForAllFlight();
    }

    
    public void displayRegisteredUsersForASpecificFlight(String flightNum) {
        new DisplayFlightClass().displayRegisteredUsersForASpecificFlight(flightNum);
    }

}
