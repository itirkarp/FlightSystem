package models;

import java.util.*;
import play.db.ebean.*;
import play.data.validation.Constraints.*;
import javax.persistence.*;

@Entity
@Table(name = "route_seg")
public class RouteSegment extends Model {

    @EmbeddedId
    public RouteSegmentPK primary_key;
    @Required(message = "Arrival time is required.")
//    @Range(min = 0, max = 2400, message = "Time must be between 0000 and 2400")
    public Integer arr_time;
    @Required(message = "Departure time is required.")
//    @Range(min = 0, max = 2400, message = "Time must be between 0000 and 2400")
    public Integer dep_time;
    @Required(message = "Destination airport is required.")
    @MaxLength(value = 3, message = "Destination airport cannot be more than 3 characters.")
    public String airpt_id_to;
    @Required(message = "Source airport is required.")
    @MaxLength(value = 3, message = "Source airport cannot be more than 3 characters.")
    public String airpt_id_from;
    
    @ManyToOne
    @JoinColumn(name="route_id", insertable = false, updatable = false)
    public Route route;
    
    public static Finder<String, RouteSegment> find = new Finder(String.class, RouteSegment.class);

    public static List<RouteSegment> all() {
        return find.all();
    }

    public static void create(Integer arr_time, Integer dep_time, String airpt_id_to, String airpt_id_from, Route route) {
        RouteSegment segment = new RouteSegment(arr_time, dep_time, airpt_id_to, airpt_id_from, route);
        segment.primary_key = new RouteSegmentPK(route.route_id, 1);
        segment.save();
    }

    private RouteSegment(Integer arr_time, Integer dep_time, String airpt_id_to, String airpt_id_from, Route route) {
        this.arr_time = arr_time;
        this.dep_time = dep_time;
        this.airpt_id_from = airpt_id_from;
        this.airpt_id_to = airpt_id_to;
        this.route = route;
    }

}