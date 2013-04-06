package models;

import java.util.*;
import play.db.ebean.*;
import play.data.validation.Constraints.*;
import javax.persistence.*;

@Entity
public class Route extends Model {

    @Id
//    @Required(message = "Route ID is required.")
//    @MaxLength(value = 5, message = "Route ID cannot be more than 5 characters.")
    public String route_id;
//    @Required(message = "Arrival time is required.")
//    @Max(value = 9999, message = "Arrival time cannot be more than 4 characters.")
    public int arr_time;
//    @Required(message = "Destination airport is required.")
//    @MaxLength(value = 3, message = "Destination airport cannot be more than 3 characters.")
    public String airpt_id_to;
//    @Required(message = "Departure time is required.")
//    @Max(value = 9999, message = "Departure time cannot be more than 4 characters.")
    public int dep_time;
//    @Max(value = 999, message = "**************")
    public int overbook_f;
//    @Max(value = 999, message = "^^^^^^^^^^^^^^")
    public int overbook_i;
//    @Required(message = "Source airport is required.")
//    @MaxLength(value = 3, message = "Source airport cannot be more than 3 characters.")
    public String airpt_id_from;
//    @Required(message = "Airline ID is required.")
//    @MaxLength(value = 2, message = "Airline ID cannot be more than 2 characters.")
    public String airln_id;
//    @Required(message = "Day number is required.")
//    @Max(value = 7, message = "Day number cannot be more than 7.")
//    @Min(value = 1, message = "Day number cannot be less than 1.")
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