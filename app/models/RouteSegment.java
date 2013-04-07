package models;

import com.avaje.ebean.validation.Range;
import java.util.*;
import play.db.ebean.*;
import play.data.validation.Constraints.*;
import javax.persistence.*;

@Entity
@Table(name = "route_seg")
public class RouteSegment extends Model {

    @Required
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int seg_no;
    @Required(message = "Arrival time is required.")
//    @Range(min = 0, max = 2400, message = "Time must be between 0000 and 2400")
    public int arr_time;
    @Required(message = "Departure time is required.")
//    @Range(min = 0, max = 2400, message = "Time must be between 0000 and 2400")
    public int dep_time;
    @Required(message = "Destination airport is required.")
    @MaxLength(value = 3, message = "Destination airport cannot be more than 3 characters.")
    public String airpt_id_to;
    @Required(message = "Source airport is required.")
    @MaxLength(value = 3, message = "Source airport cannot be more than 3 characters.")
    public String airpt_id_from;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="route_id")
    public Route route;
    
    public static Finder<String, RouteSegment> find = new Finder(String.class, RouteSegment.class);

    public static List<RouteSegment> all() {
        return find.all();
    }

    public static void create(RouteSegment routeSegment) {
        routeSegment.save();
    }

}