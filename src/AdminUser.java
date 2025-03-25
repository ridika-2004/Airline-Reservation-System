import java.util.Scanner;

public class AdminUser extends User {
    private String username;
    private RolesAndPermissions rolesAndPermissions;
    private Customer customerManager;
    private FlightReservation bookingManager;
    private Flight flightManager;

    public AdminUser(String username, RolesAndPermissions rp, Customer cm, FlightReservation bm, Flight fm) {
        this.username = username;
        this.rolesAndPermissions = rp;
        this.customerManager = cm;
        this.bookingManager = bm;
        this.flightManager = fm;
    }

    @Override
    public void showMenu() {
        int desiredOption;
        System.out.printf("%-20sLogged in Successfully as \"%s\"..... For further Proceedings, enter a value from below....", "", username);

        do {
            displayAdminMenu();
            desiredOption = scanner.nextInt();

            switch (desiredOption) {
                case 1 -> handleAddPassenger();
                case 2 -> handleSearchPassenger();
                case 3 -> handleUpdatePassenger();
                case 4 -> handleDeletePassenger();
                case 5 -> customerManager.displayCustomersData(false);
                case 6 -> handleDisplayFlightsByPassenger();
                case 7 -> handleDisplayPassengersByFlight();
                case 8 -> handleDeleteFlight();
                case 0 -> System.out.println("Thanks for Using BAV Airlines Ticketing System...!!!");
                default -> {
                    System.out.println("Invalid Choice...You've Have to login again...");
                    desiredOption = 0;
                }
            }
        } while (desiredOption != 0);
    }

    private void displayAdminMenu() {
        System.out.printf("\n\n%-60s+++++++++ 2nd Layer Menu +++++++++%50sLogged in as \"%s\"\n", "", "", username);
        System.out.printf("%-30s (a) Enter 1 to add new Passenger....\n", "");
        System.out.printf("%-30s (b) Enter 2 to search a Passenger....\n", "");
        System.out.printf("%-30s (c) Enter 3 to update the Data of the Passenger....\n", "");
        System.out.printf("%-30s (d) Enter 4 to delete a Passenger....\n", "");
        System.out.printf("%-30s (e) Enter 5 to Display all Passengers....\n", "");
        System.out.printf("%-30s (f) Enter 6 to Display all flights registered by a Passenger...\n", "");
        System.out.printf("%-30s (g) Enter 7 to Display all registered Passengers in a Flight....\n", "");
        System.out.printf("%-30s (h) Enter 8 to Delete a Flight....\n", "");
        System.out.printf("%-30s (i) Enter 0 to Go back to the Main Menu/Logout....\n", "");
        System.out.print("Enter the desired Choice :   ");
    }

    private void handleAddPassenger() {
        customerManager.addNewCustomer();
    }

    private void handleSearchPassenger() {
        customerManager.displayCustomersData(false);
        System.out.print("Enter the CustomerID to Search :\t");
        String customerID = scanner.nextLine();
        System.out.println();
        customerManager.searchUser(customerID);
    }

    private void handleUpdatePassenger() {
        customerManager.displayCustomersData(false);
        System.out.print("Enter the CustomerID to Update its Data :\t");
        String customerID = scanner.nextLine();
        if (customersCollection.size() > 0) {
            customerManager.editUserInfo(customerID);
        } else {
            System.out.printf("%-50sNo Customer with the ID %s Found...!!!\n", " ", customerID);
        }
    }

    private void handleDeletePassenger() {
        customerManager.displayCustomersData(false);
        System.out.print("Enter the CustomerID to Delete its Data :\t");
        String customerID = scanner.nextLine();
        if (customersCollection.size() > 0) {
            customerManager.deleteUser(customerID);
        } else {
            System.out.printf("%-50sNo Customer with the ID %s Found...!!!\n", " ", customerID);
        }
    }

    private void handleDisplayFlightsByPassenger() {
        System.out.print("\n\nEnter the ID of the user to display all flights registered by that user...");
        String id = scanner.nextLine();
        bookingManager.displayFlightsRegisteredByOneUser(id);
    }

    private void handleDisplayPassengersByFlight() {
        System.out.print("Do you want to display Passengers of all flights or a specific flight.... 'Y/y' for displaying all flights and 'N/n' to look for a specific flight.... ");
        char choice = scanner.nextLine().charAt(0);
        if ('y' == choice || 'Y' == choice) {
            bookingManager.displayRegisteredUsersForAllFlight();
        } else if ('n' == choice || 'N' == choice) {
            flightManager.displayFlightSchedule();
            System.out.print("Enter the Flight Number to display the list of passengers registered in that flight... ");
            String flightNum = scanner.nextLine();
            bookingManager.displayRegisteredUsersForASpecificFlight(flightNum);
        } else {
            System.out.println("Invalid Choice...No Response...!");
        }
    }

    private void handleDeleteFlight() {
        flightManager.displayFlightSchedule();
        System.out.print("Enter the Flight Number to delete the flight : ");
        String flightNum = scanner.nextLine();
        flightManager.deleteFlight(flightNum);
    }
}