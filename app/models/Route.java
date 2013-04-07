package models;

import java.util.*;
import play.db.ebean.*;
import play.data.validation.Constraints.*;
import javax.persistence.*;

@Entity
public class Route extends Model {

    @Id
    @Required(message = "Route ID is required.")
    @MaxLength(value = 5, message = "Route ID cannot be more than 5 characters.")
    public String route_id;
    @Required(message = "Arrival time is required.")
    @Max(value = 2400, message = "Arrival time cannot be more than 2400.")
    @Min(value = 0, message = "Arrival time cannot be less then 0.")
    public Integer arr_time;
    @Required(message = "Destination airport is required.")
    @MaxLength(value = 3, message = "Destination airport cannot be more than 3 characters.")
    public String airpt_id_to;
    @Required(message = "Departure time is required.")
    @Max(value = 2400, message = "Departure time cannot be more than 2400.")
    @Min(value = 0, message = "Departure time cannot be less then 0.")
    public Integer dep_time;
    public Integer overbook_f;
    public Integer overbook_i;
    @Required(message = "Source airport is required.")
    @MaxLength(value = 3, message = "Source airport cannot be more than 3 characters.")
    public String airpt_id_from;
    @Required(message = "Airline ID is required.")
    @MaxLength(value = 2, message = "Airline ID cannot be more than 2 characters.")
    public String airln_id;
    @Required(message = "Day number is required.")
    @Max(value = 7, message = "Day number cannot be more than 7.")
    @Min(value = 1, message = "Day number cannot be less than 1.")
    public Integer day_no;

    public static Finder<String, Route> find = new Finder(String.class, Route.class);

    public static List<Route> all() {
        return find.all();
    }

    public static void create(Route route) {
        route.save();
    }

    public static void update(String route_id, int arr_time, String airpt_id_to, 
                    int dep_time, String airpt_id_from, String airln_id, int day_no) {
        Route route = find.ref(route_id);
        route.arr_time = arr_time;
        route.airpt_id_to = airpt_id_to;
        route.dep_time = dep_time;
        route.airpt_id_from = airpt_id_from;
        route.airln_id = airln_id;
        route.day_no = day_no;
        route.update();
    }
}