package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlRow;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import models.FlightInfo;
import play.Logger;
import play.data.DynamicForm;
import play.mvc.*;
import views.html.booking.*;

public class BookingController extends Controller {

    public static Result index() {
        return ok(booking_index.render());
    }

    public static Result create() {
        return ok(booking_create.render());
    }

    public static Result save() {
        return ok();
    }
    
    public static Result flights() {
        DynamicForm form = DynamicForm.form().bindFromRequest();
        List<SqlRow> rows = Ebean.createSqlQuery("select unique f.flight_id, f.route_id, f.dep_date, f.dep_time, f.arr_time, s.seats_avail from route r, flight f,  flight_seg seg, seats_avail s where r.route_id = f.route_id and f.flight_id = seg.flight_id and seg.seg_no = s.seg_no and r.route_id = s.route_id and r.airpt_id_from = :origin and r.airpt_id_to = :destination and class_id = :class and f.dep_date = :date")
                            .setParameter("origin", form.get("origin")).setParameter("destination", form.get("destination"))
                            .setParameter("class", form.get("class_id")).setParameter("date", form.get("date")).findList();
        List<FlightInfo> flightInfos = new ArrayList<FlightInfo>();
        for (SqlRow row : rows) {
            flightInfos.add(new FlightInfo(row.getInteger("flight_id"), row.getString("route_id"), 
                    row.getDate("dep_date"), row.getInteger("dep_time"), row.getInteger("arr_time"), 
                    row.getInteger("seats_avail")));
        }
        return ok(flights.render(flightInfos));
    }

}
