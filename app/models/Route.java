package models;

import java.util.*;
import play.db.ebean.*;
import play.data.validation.Constraints.*;
import javax.persistence.*;

@Entity
public class Route extends Model {

    @Id
    @Required(message = "Route ID is required.")
    @MaxLength(value = 5, message = "Route ID cannot be more than 5 characters")
    public String route_id;
    @Required(message = "Arrival time is required.")
    @MaxLength(value = 4, message = "Arrival time cannot be more than 4 characters")
    public int arr_time;
    @Required(message = "Destination airport is required.")
    @MaxLength(value = 3, message = "Destination airport cannot be more than 3 characters")
    public String airpt_id_to;
    @Required(message = "Departure time is required.")
    @MaxLength(value = 4)
    public int dep_time;
    @MaxLength(value = 3)
    public int overbook_f;
    @MaxLength(value = 3)
    public int overbook_i;
    @Required(message = "Source airport is required.")
    @MaxLength(value = 3)
    public String airpt_id_from;
    @Required(message = "Airline ID is required.")
    @MaxLength(value = 2)
    public String airln_id;
    @Required(message = "Day number is required.")
    @MaxLength(value = 1)
    public int day_no;

    public static Finder<String, Route> find = new Finder(String.class, Route.class);

    public static List<Route> all() {
        return find.all();
    }

    public static void create(Route route) {
        route.save();
    }

    public static void update(String airln_id, String airln_name) {
//        Route airline = find.ref(airln_id);
//        airline.airln_name = airln_name;
//        airline.update();
    }
}