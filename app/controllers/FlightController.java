package controllers;

import java.sql.SQLException;
import java.util.HashMap;
import models.Aircraft;
import models.AircraftType;
import models.Flight;
import models.Route;
import play.data.Form;
import play.mvc.*;
import views.html.flight.*;


public class FlightController extends Controller {

    static Form<Flight> flightForm = Form.form(Flight.class);
    static final HashMap<String, String> errorMessages = new HashMap<String, String>() {
        {
            put("FLIT_PK", "Cannot save flight. This flight already exists.");
            put("FLIT_FOR", "Cannot save flight. Route ID does not exist.");
        }
    };

    public static Result index() {
        return ok(flight_index.render(Flight.all()));
    }

    public static Result create() {
        return ok(flight_create.render(flightForm, Route.all(), AircraftType.all(), Aircraft.all()));
    }

    public static Result save() throws SQLException {
        Form<Flight> filledForm = flightForm.bindFromRequest();
        if (filledForm.hasErrors()) {
            flash("error", "There were errors in the form:");
            return badRequest(flight_create.render(filledForm, Route.all(), AircraftType.all(), Aircraft.all()));
        } else {
            try {
                Flight.create(filledForm.get());
            } catch (SQLException e) {
                String[] temp = e.getMessage().split("S1784498.");
                if (temp.length > 1) {
                    temp = temp[1].split("\\) violated");
                    String constraintName = temp[0];
                    flash("error", errorMessages.get(constraintName));
                } else {
                    flash("error", "Cannot create flight. A database error occurred: " + e.getMessage());
                }
                return badRequest(flight_create.render(filledForm, Route.all(), AircraftType.all(), Aircraft.all()));
            }
        }
        return ok(flight_index.render(Flight.all()));
    }

    public static Result edit(Integer id) {
        Flight flight = Flight.find.ref(id);
        return ok(flight_edit.render(flight, flightForm.fill(flight), AircraftType.all(), Aircraft.all(), Route.all()));
    }

    public static Result update(Integer id) {
        Form<Flight> filledForm = flightForm.bindFromRequest();
        if (filledForm.hasErrors()) {
            flash("error", "There were errors in the form:");
            Flight flight = Flight.find.ref(id);
            return badRequest(flight_edit.render(flight, filledForm, AircraftType.all(), Aircraft.all(), Route.all()));
        } else {
            try {
                Flight.update(filledForm.get().flight_id, filledForm.get().route_id, filledForm.get().dep_date, filledForm.get().arr_time, filledForm.get().dep_time, filledForm.get().aircraft_id);
            } catch (SQLException e) {
                String[] temp = e.getMessage().split("S1784498.");
                if (temp.length > 1) {
                    temp = temp[1].split("\\) violated");
                    String constraintName = temp[0];
                    flash("error", errorMessages.get(constraintName));
                } else {
                    flash("error", "Cannot save flight. A database error occurred: " + e.getMessage());
                }
            }
        }
        return ok(flight_index.render(Flight.all()));
    }
    
    public static Result delete(Integer id) {
        try {
            Flight.deleteFlight(id);
        } catch (SQLException e) {
            flash("error", errorMessages.get(e.getMessage().substring(0, 9)));
        }
        return ok(flight_index.render(Flight.all()));
    }

}
