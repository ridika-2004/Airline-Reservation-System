import java.util.Scanner;

public class MainSystem {
    private User currentUser;
    private RolesAndPermissions rolesAndPermissions;
    private Customer customerManager;
    private FlightReservation bookingManager;
    private Flight flightManager;
    private Scanner scanner;
    private int adminCount = 1;

    public MainSystem() {
        this.rolesAndPermissions = new RolesAndPermissions();
        this.customerManager = new Customer();
        this.bookingManager = new FlightReservation();
        this.flightManager = new Flight();
        this.scanner = new Scanner(System.in);
        flightManager.flightScheduler();
    }

    public void run() {
        displayWelcome();
        int option = getMainMenuOption();

        while (option != 0) {
            switch (option) {
                case 1 -> handleAdminLogin();
                case 2 -> handleAdminRegistration();
                case 3 -> handlePassengerLogin();
                case 4 -> customerManager.addNewCustomer();
                case 5 -> new User() {
                    @Override public void showMenu() {}
                }.manualInstructions();
            }

            if (option != 0) {
                currentUser = new User() {
                    @Override public void showMenu() {
                        displayMainMenu();
                    }
                };
                currentUser.displayMainMenu();
                option = scanner.nextInt();
                while (option < 0 || option > 5) {
                    System.out.print("ERROR!! Please enter value between 0 - 5. Enter the value again :\t");
                    option = scanner.nextInt();
                }
            }
        }
        System.out.println("Thank you for using BAV Airlines System. Goodbye!");
    }

    private void displayWelcome() {
        System.out.println("\n\t\t\t\t\t+++++++++++++ Welcome to BAV AirLines +++++++++++++");
        System.out.println("\n***** Default Username && Password is root-root *****");
    }

    private int getMainMenuOption() {
        currentUser = new User() {
            @Override public void showMenu() {
                displayMainMenu();
            }
        };
        currentUser.displayMainMenu();
        int option = scanner.nextInt();
        while (option < 0 || option > 5) {
            System.out.print("ERROR!! Please enter value between 0 - 5. Enter the value again :\t");
            option = scanner.nextInt();
        }
        return option;
    }

    private void handleAdminLogin() {
        scanner.nextLine(); // Consume newline
        System.out.print("\nEnter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        int privilege = rolesAndPermissions.isPrivilegedUserOrNot(username, password);
        if (privilege == -1) {
            System.out.println("ERROR: Invalid credentials");
        } else if (privilege == 0) {
            System.out.println("You have standard privileges (view only)");
            customerManager.displayCustomersData(true);
        } else {
            currentUser = new AdminUser(username, rolesAndPermissions, customerManager,
                    bookingManager, flightManager);
            currentUser.showMenu();
        }
    }

    private void handleAdminRegistration() {
        scanner.nextLine(); // Consume newline
        System.out.print("\nEnter new Admin Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter new Admin Password: ");
        String password = scanner.nextLine();

        while (rolesAndPermissions.isPrivilegedUserOrNot(username, password) != -1) {
            System.out.print("Username already exists. Enter new Username: ");
            username = scanner.nextLine();
            System.out.print("Enter new Password: ");
            password = scanner.nextLine();
        }

        User.adminUserNameAndPassword[adminCount][0] = username;
        User.adminUserNameAndPassword[adminCount][1] = password;
        adminCount++;
        System.out.println("Admin registration successful!");
    }

    private void handlePassengerLogin() {
        scanner.nextLine(); // Consume newline
        System.out.print("\nEnter Passenger Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        String[] result = rolesAndPermissions.isPassengerRegistered(email, password).split("-");
        if (Integer.parseInt(result[0]) == 1) {
            currentUser = new PassengerUser(email, result[1], customerManager,
                    bookingManager, flightManager);
            currentUser.showMenu();
        } else {
            System.out.println("ERROR: Invalid credentials or passenger not registered");
        }
    }
}