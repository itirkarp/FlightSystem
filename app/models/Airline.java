package models;

import java.util.*;
import play.db.ebean.*;
import play.data.validation.Constraints.*;
import javax.persistence.*;

@Entity
public class Airline extends Model {

    @Id
    public String airln_id;
    @Required
    public String airln_name;
    
    public static Finder<String, Airline> find = new Finder(String.class, Airline.class);

    public static List<Airline> all() {
        return find.all();
    }

    public static void create(Airline airline) {
        airline.save();
    }
    
    public static void update(String airln_id, String airln_name) {
        Airline airline = find.ref(airln_id);
        airline.airln_name = airln_name;
        airline.update();
    }    
}