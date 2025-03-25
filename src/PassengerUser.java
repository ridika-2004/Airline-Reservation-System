public class PassengerUser extends User {
    private String username;
    private String userId;
    private Customer customerManager;
    private FlightReservation bookingManager;
    private Flight flightManager;

    public PassengerUser(String username, String userId, Customer cm, FlightReservation bm, Flight fm) {
        this.username = username;
        this.userId = userId;
        this.customerManager = cm;
        this.bookingManager = bm;
        this.flightManager = fm;
    }

    @Override
    public void showMenu() {
        int desiredChoice;
        System.out.printf("\n\n%-20sLogged in Successfully as \"%s\"..... For further Proceedings, enter a value from below....", "", username);

        do {
            displayPassengerMenu();
            desiredChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (desiredChoice) {
                case 1 -> handleBookFlight();
                case 2 -> customerManager.editUserInfo(userId);
                case 3 -> handleDeleteAccount();
                case 4 -> displayFlightSchedule(flightManager);
                case 5 -> bookingManager.cancelFlight(userId, AdminUser.customersCollection);
                case 6 -> bookingManager.displayFlightsRegisteredByOneUser(userId);
                case 0 -> { /* Do nothing - will exit */ }
                default -> {
                    System.out.println("Invalid Choice...You've Have to login again...");
                    desiredChoice = 0;
                }
            }
        } while (desiredChoice != 0);
    }

    private void displayPassengerMenu() {
        System.out.printf("\n\n%-60s+++++++++ 3rd Layer Menu +++++++++%50sLogged in as \"%s\"\n", "", "", username);
        System.out.printf("%-40s (a) Enter 1 to Book a flight....\n", "");
        System.out.printf("%-40s (b) Enter 2 to update your Data....\n", "");
        System.out.printf("%-40s (c) Enter 3 to delete your account....\n", "");
        System.out.printf("%-40s (d) Enter 4 to Display Flight Schedule....\n", "");
        System.out.printf("%-40s (e) Enter 5 to Cancel a Flight....\n", "");
        System.out.printf("%-40s (f) Enter 6 to Display all flights registered by \"%s\"....\n", "", username);
        System.out.printf("%-40s (g) Enter 0 to Go back to the Main Menu/Logout....\n", "");
        System.out.print("Enter the desired Choice :   ");
    }

    private void handleBookFlight() {
        flightManager.displayFlightSchedule();
        System.out.print("\nEnter the desired flight number to book :\t ");
        String flightToBeBooked = scanner.nextLine();
        System.out.print("Enter the Number of tickets for " + flightToBeBooked + " flight :   ");
        int numOfTickets = scanner.nextInt();
        while (numOfTickets > 10) {
            System.out.print("ERROR!! You can't book more than 10 tickets at a time for single flight....Enter number of tickets again : ");
            numOfTickets = scanner.nextInt();
        }
        bookingManager.bookFlight(flightToBeBooked, numOfTickets, userId, flightManager.getFlightList(), AdminUser.customersCollection);
    }

    private void handleDeleteAccount() {
        System.out.print("Are you sure to delete your account...It's an irreversible action...Enter Y/y to confirm...");
        char confirmationChar = scanner.nextLine().charAt(0);
        if (confirmationChar == 'Y' || confirmationChar == 'y') {
            customerManager.deleteUser(userId);
            System.out.printf("User %s's account deleted Successfully...!!!", username);
        } else {
            System.out.println("Action has been cancelled...");
        }
    }
}