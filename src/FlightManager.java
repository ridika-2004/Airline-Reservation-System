import java.util.List;

class FlightManager {
    private List<Flight> flightList;

    public FlightManager(){
        this.flightList = flightList;
    }

    public FlightManager(List<Flight> flightList) {
        this.flightList = flightList;
    }

    public Flight findFlightByNumber(String flightNo, List<Flight> flightList) {
        return flightList.stream()
                .filter(f -> flightNo.equalsIgnoreCase(f.getFlightNumber()))
                .findFirst()
                .orElse(null);
    }

    public List<Flight> getFlightList() {
        return flightList;
    }

    
    public String getFlightStatus(Flight flight, List<Flight> flightList) {
        return flightList.contains(flight) ? "As Per Schedule" : "Cancelled";
    }
}