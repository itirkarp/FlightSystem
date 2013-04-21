package models;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceException;
import javax.validation.constraints.Min;
import play.data.validation.Constraints.Max;
import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Flight extends Model {

    @Id
    @Required(message = "Flight ID is required.")
    @Max(value = 99, message = "Flight ID cannot be more than 99")
    @Min(value = 1, message = "Flight ID cannot be less than 1")
    public Integer flight_id;
    @Required(message = "Route ID is required.")
    @MaxLength(value = 5, message = "Route ID cannot be more than 5 characters")
    public String route_id;
    @Required(message = "Departure date is required.")
    public Date dep_date;
    @Required(message = "Arrival time is required.")
    @Max(value = 2400, message = "Arrival time cannot be more than 2400.")
    @Min(value = 0, message = "Arrival time cannot be less then 0.")
    public Integer arr_time;
    @Required(message = "Departure time is required.")
    @Max(value = 2400, message = "Departure time cannot be more than 2400.")
    @Min(value = 0, message = "Departure time cannot be less then 0.")
    public Integer dep_time;
    @Required(message = "Aircraft ID is required.")
    @MaxLength(value = 6, message = "Aircraft ID cannot be more than 6 characters")
    public String aircraft_id;
    @Required(message = "Aircraft type ID is required.")
    @MaxLength(value = 6, message = "Aircraft type ID cannot be more than 6 characters")
    public String aircr_type_id;
    
    public static Model.Finder<Integer, Flight> find = new Model.Finder(Integer.class, Flight.class);

    public Flight(Integer flight_id, String route_id, Date dep_date, Integer arr_time, Integer dep_time, String aircraft_id, String aircr_type_id) {
        this.flight_id = flight_id;
        this.route_id = route_id;
        this.dep_date = dep_date;
        this.arr_time = arr_time;
        this.dep_time = dep_time;
        this.aircraft_id = aircraft_id;
        this.aircr_type_id = aircr_type_id;
    }

    public static List<Flight> all() {
        return find.all();
    }

    public static void create(Flight flight) throws PersistenceException {
        flight.save();
    }

    public static void update(Integer flight_id, String route_id, Date dep_date, Integer arr_time, Integer dep_time, String aircraft_id, String aircr_type_id) {
        Flight flight = find.ref(flight_id);
        flight.route_id = route_id;
        flight.dep_date = dep_date;
        flight.arr_time = arr_time;
        flight.dep_time = dep_time;
        flight.aircraft_id = aircraft_id;
        flight.aircr_type_id = aircr_type_id;
        flight.update();
    }

    public static void deleteFlight(Integer flight_id) throws SQLException {
        Connection connection = null;
        CallableStatement callableStatement = null;
        connection = play.db.DB.getConnection();
        String storedProc = "{call SP_DELETE_FLIGHT(?)}";
        callableStatement = connection.prepareCall(storedProc);
        callableStatement.setInt(1, flight_id);
        callableStatement.executeUpdate();
    }
}
