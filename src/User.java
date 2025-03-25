import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class User {
    protected static String[][] adminUserNameAndPassword = new String[10][2];
    protected static List<Customer> customersCollection = new ArrayList<>();
    protected Scanner scanner = new Scanner(System.in);

    static {
        adminUserNameAndPassword[0][0] = "root";
        adminUserNameAndPassword[0][1] = "root";
    }

    public abstract void showMenu();

    protected void displayMainMenu() {
        System.out.println("\n\n\t\t(a) Press 0 to Exit.");
        System.out.println("\t\t(b) Press 1 to Login as admin.");
        System.out.println("\t\t(c) Press 2 to Register as admin.");
        System.out.println("\t\t(d) Press 3 to Login as Passenger.");
        System.out.println("\t\t(e) Press 4 to Register as Passenger.");
        System.out.println("\t\t(f) Press 5 to Display the User Manual.");
        System.out.print("\t\tEnter the desired option:    ");
    }

    protected void manualInstructions() {
        System.out.printf("%n%n%50s %s Welcome to BAV Airlines User Manual %s", "", "+++++++++++++++++", "+++++++++++++++++");
        System.out.println("\n\n\t\t(a) Press 1 to display Admin Manual.");
        System.out.println("\t\t(b) Press 2 to display User Manual.");
        System.out.print("\nEnter the desired option :    ");
        int choice = scanner.nextInt();
        while (choice < 1 || choice > 2) {
            System.out.print("ERROR!!! Invalid entry...Please enter a value either 1 or 2....Enter again....");
            choice = scanner.nextInt();
        }

        if (choice == 1) {
            System.out.println(
                    "\n\n(1) Admin have the access to all users data...Admin can delete, update, add and can perform search for any customer...\n" +
                            "(2) In order to access the admin module, you've to get yourself register by pressing 2, when the main menu gets displayed...\n" +
                            "(3) Provide the required details i.e., name, email, id...Once you've registered yourself, press 1 to login as an admin... \n" +
                            "(4) Once you've logged in, 2nd layer menu will be displayed on the screen...From here on, you can select from variety of options...\n" +
                            "(5) Pressing \"1\" will add a new Passenger, provide the program with required details to add the passenger...\n" +
                            "(6) Pressing \"2\" will search for any passenger, given the admin(you) provides the ID from the table printing above....  \n" +
                            "(7) Pressing \"3\" will let you update any passengers data given the user ID provided to program...\n" +
                            "(8) Pressing \"4\" will let you delete any passenger given its ID provided...\n" +
                            "(9) Pressing \"5\" will let you display all registered passenger...\n" +
                            "(10) Pressing \"6\" will let you display all registered passengers...After selecting, program will ask, if you want to display passengers for all flights(Y/y) or a specific flight(N/n)\n" +
                            "(11) Pressing \"7\" will let you delete any flight given its flight number provided...\n" +
                            "(12) Pressing \"0\" will make you logged out of the program...You can login again any time you want during the program execution....\n");
        }
        else {
            System.out.println(
                    "\n\n(1) Local user has the access to its data only...He/She won't be able to change/update other users data...\n" +
                            "(2) In order to access local users benefits, you've to get yourself register by pressing 4, when the main menu gets displayed...\n" +
                            "(3) Provide the details asked by the program to add you to the users list...Once you've registered yourself, press \"3\" to login as a passenger...\n" +
                            "(4) Once you've logged in, 3rd layer menu will be displayed...From here on, you embarked on the journey to fly with us...\n" +
                            "(5) Pressing \"1\" will display available/scheduled list of flights...To get yourself booked for a flight, enter the flight number and number of tickets for the flight...Max num of tickets at a time is 10 ...\n" +
                            "(6) Pressing \"2\" will let you update your own data...You won't be able to update other's data... \n" +
                            "(7) Pressing \"3\" will delete your account... \n" +
                            "(8) Pressing \"4\" will display randomly designed flight schedule for this runtime...\n" +
                            "(9) Pressing \"5\" will let you cancel any flight registered by you...\n" +
                            "(10) Pressing \"6\" will display all flights registered by you...\n" +
                            "(11) Pressing \"0\" will make you logout of the program...You can login back at anytime with your credentials...for this particular run-time... \n");
        }
    }

    public static List<Customer> getCustomersCollection() {
        return customersCollection;
    }

    protected void displayFlightSchedule(Flight flightManager) {
        flightManager.displayFlightSchedule();
        flightManager.displayMeasurementInstructions();
    }
}