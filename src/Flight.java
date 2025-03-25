import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Flight extends FlightDistance {

    // Constants
    private static final int NUM_OF_FLIGHTS = 15;
    private static final double GROUND_SPEED = 450.0;
    private static final int MINUTES_IN_HOUR = 60;
    private static final int FLIGHT_DAY_INCREMENT = 7;
    private static final int MINUTES_MOD_DIVISOR = 5;
    private static final int MINUTES_ROUND_THRESHOLD = 3;
    private static final int MINUTES_QUARTER = 15;

    // Fields
    private final String flightSchedule;
    private final String flightNumber;
    private final String fromWhichCity;
    private final String gate;
    private final String toWhichCity;
    private final double distanceInMiles;
    private final double distanceInKm;
    private final String flightTime;
    private int numOfSeatsInTheFlight;
    private final List<Customer> listOfRegisteredCustomersInAFlight;
    private int customerIndex;
    private static int nextFlightDay = 0;
    private static final List<Flight> flightList = new ArrayList<>();

    // Constructors
    public Flight() {
        this.flightSchedule = null;
        this.flightNumber = null;
        this.numOfSeatsInTheFlight = 0;
        this.toWhichCity = null;
        this.fromWhichCity = null;
        this.gate = null;
        this.distanceInMiles = 0;
        this.distanceInKm = 0;
        this.flightTime = null;
        this.listOfRegisteredCustomersInAFlight = new ArrayList<>();
    }

    public Flight(String flightSchedule, String flightNumber, int numOfSeatsInTheFlight, String[][] chosenDestinations, String[] distanceBetweenTheCities, String gate) {
        this.flightSchedule = flightSchedule;
        this.flightNumber = flightNumber;
        this.numOfSeatsInTheFlight = numOfSeatsInTheFlight;
        this.fromWhichCity = chosenDestinations[0][0];
        this.toWhichCity = chosenDestinations[1][0];
        this.distanceInMiles = Double.parseDouble(distanceBetweenTheCities[0]);
        this.distanceInKm = Double.parseDouble(distanceBetweenTheCities[1]);
        this.flightTime = calculateFlightTime(distanceInMiles);
        this.listOfRegisteredCustomersInAFlight = new ArrayList<>();
        this.gate = gate;
    }

    // Methods
    public void flightScheduler() {
        RandomGenerator randomGenerator = new RandomGenerator();
        for (int i = 0; i < NUM_OF_FLIGHTS; i++) {
            String[][] chosenDestinations = randomGenerator.randomDestinations();
            String[] distanceBetweenTheCities = calculateDistance(
                    Double.parseDouble(chosenDestinations[0][1]),
                    Double.parseDouble(chosenDestinations[0][2]),
                    Double.parseDouble(chosenDestinations[1][1]),
                    Double.parseDouble(chosenDestinations[1][2])
            );
            String flightSchedule = createNewFlightsAndTime();
            String flightNumber = randomGenerator.randomFlightNumbGen(2, 1).toUpperCase();
            int numOfSeatsInTheFlight = randomGenerator.randomNumOfSeats();
            String gate = randomGenerator.randomFlightNumbGen(1, 30).toUpperCase();
            flightList.add(new Flight(flightSchedule, flightNumber, numOfSeatsInTheFlight, chosenDestinations, distanceBetweenTheCities, gate));
        }
    }

    public void addNewCustomerToFlight(Customer customer) {
        listOfRegisteredCustomersInAFlight.add(customer);
    }

    public void addTicketsToExistingCustomer(Customer customer, int numOfTickets) {
        customer.addExistingFlightToCustomerList(customerIndex, numOfTickets);
    }

    public boolean isCustomerAlreadyAdded(List<Customer> customersList, Customer customer) {
        for (Customer existingCustomer : customersList) {
            if (existingCustomer.getUserID().equals(customer.getUserID())) {
                customerIndex = customersList.indexOf(existingCustomer);
                return true;
            }
        }
        return false;
    }

    public String calculateFlightTime(double distanceBetweenTheCities) {
        double time = distanceBetweenTheCities / GROUND_SPEED;
        int hours = (int) time;
        int minutes = (int) ((time - hours) * MINUTES_IN_HOUR);
        minutes = roundMinutesToNearestFive(minutes);
        return formatFlightTime(hours, minutes);
    }

    private int roundMinutesToNearestFive(int minutes) {
        int modulus = minutes % MINUTES_MOD_DIVISOR;
        if (modulus < MINUTES_ROUND_THRESHOLD) {
            minutes -= modulus;
        } else {
            minutes += MINUTES_MOD_DIVISOR - modulus;
        }
        return minutes >= MINUTES_IN_HOUR ? minutes - MINUTES_IN_HOUR : minutes;
    }

    private String formatFlightTime(int hours, int minutes) {
        return String.format("%02d:%02d", hours, minutes);
    }

    public String fetchArrivalTime() {
        LocalDateTime departureDateTime = LocalDateTime.parse(flightSchedule, DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy, HH:mm a "));
        String[] flightTimeParts = flightTime.split(":");
        int hours = Integer.parseInt(flightTimeParts[0]);
        int minutes = Integer.parseInt(flightTimeParts[1]);
        LocalDateTime arrivalTime = departureDateTime.plusHours(hours).plusMinutes(minutes);
        return arrivalTime.format(DateTimeFormatter.ofPattern("EE, dd-MM-yyyy HH:mm a"));
    }

    public void deleteFlight(String flightNumber) {
        Iterator<Flight> iterator = flightList.iterator();
        while (iterator.hasNext()) {
            Flight flight = iterator.next();
            if (flight.getFlightNumber().equalsIgnoreCase(flightNumber)) {
                iterator.remove();
                displayFlightSchedule();
                return;
            }
        }
        System.out.println("Flight with given Number not found...");
    }

    @Override
    public String[] calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double distance = Math.sin(degreeToRadian(lat1)) * Math.sin(degreeToRadian(lat2)) +
                Math.cos(degreeToRadian(lat1)) * Math.cos(degreeToRadian(lat2)) * Math.cos(degreeToRadian(theta));
        distance = Math.acos(distance);
        distance = radianToDegree(distance);
        distance *= 60 * 1.1515;
        return new String[]{
                String.format("%.2f", distance * 0.8684),
                String.format("%.2f", distance * 1.609344),
                Double.toString(Math.round(distance * 100.0) / 100.0)
        };
    }

    private double degreeToRadian(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double radianToDegree(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public void displayFlightSchedule() {
        System.out.println();
        System.out.print("+------+-------------------------------------------+-----------+------------------+-----------------------+------------------------+---------------------------+-------------+--------+------------------------+\n");
        System.out.printf("| Num  | FLIGHT SCHEDULE\t\t\t   | FLIGHT NO | Available Seats  | \tFROM ====>>       | \t====>> TO\t   | \t    ARRIVAL TIME       | FLIGHT TIME |  GATE  |   DISTANCE(MILES/KMS)  |%n");
        System.out.print("+------+-------------------------------------------+-----------+------------------+-----------------------+------------------------+---------------------------+-------------+--------+------------------------+\n");
        int i = 0;
        for (Flight flight : flightList) {
            i++;
            System.out.println(flight.toString(i));
            System.out.print("+------+-------------------------------------------+-----------+------------------+-----------------------+------------------------+---------------------------+-------------+--------+------------------------+\n");
        }
    }

    @Override
    public String toString(int i) {
        return String.format("| %-5d| %-41s | %-9s | \t%-9s | %-21s | %-22s | %-10s  |   %-6sHrs |  %-4s  |  %-8s / %-11s|",
                i, flightSchedule, flightNumber, numOfSeatsInTheFlight, fromWhichCity, toWhichCity, fetchArrivalTime(), flightTime, gate, distanceInMiles, distanceInKm);
    }

    public String createNewFlightsAndTime() {
        Calendar calendar = Calendar.getInstance();
        nextFlightDay += Math.random() * FLIGHT_DAY_INCREMENT;
        calendar.add(Calendar.DATE, nextFlightDay);
        calendar.add(Calendar.HOUR, nextFlightDay);
        calendar.set(Calendar.MINUTE, ((calendar.get(Calendar.MINUTE) * 3) - (int) (Math.random() * 45)));
        Date date = calendar.getTime();
        LocalDateTime localDateTime = Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        localDateTime = getNearestHourQuarter(localDateTime);
        return localDateTime.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy, HH:mm a "));
    }

    public LocalDateTime getNearestHourQuarter(LocalDateTime datetime) {
        int minutes = datetime.getMinute();
        int mod = minutes % MINUTES_QUARTER;
        LocalDateTime newDatetime = mod < MINUTES_QUARTER / 2 ? datetime.minusMinutes(mod) : datetime.plusMinutes(MINUTES_QUARTER - mod);
        return newDatetime.truncatedTo(ChronoUnit.MINUTES);
    }

    // Getters
    public int getNoOfSeats() {
        return numOfSeatsInTheFlight;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setNoOfSeatsInTheFlight(int numOfSeatsInTheFlight) {
        this.numOfSeatsInTheFlight = numOfSeatsInTheFlight;
    }

    public String getFlightTime() {
        return flightTime;
    }

    public List<Flight> getFlightList() {
        return flightList;
    }

    public List<Customer> getListOfRegisteredCustomersInAFlight() {
        return listOfRegisteredCustomersInAFlight;
    }

    public String getFlightSchedule() {
        return flightSchedule;
    }

    public String getFromWhichCity() {
        return fromWhichCity;
    }

    public String getGate() {
        return gate;
    }

    public String getToWhichCity() {
        return toWhichCity;
    }
}