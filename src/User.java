import java.util.*;

public abstract class User {

    protected Scanner scanner = new Scanner(System.in);

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

        if (choice == 1) AdminManual.adminManual();
        else UserManual.userManual();

    }

    public static List<Customer> getCustomersCollection() {
        return new ArrayList<>(AdminUser.customersCollection);
    }

    protected void displayFlightSchedule(Flight flightManager) {
        flightManager.displayFlightSchedule();
        flightManager.displayMeasurementInstructions();
    }
}