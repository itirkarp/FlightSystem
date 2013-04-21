package controllers;

import java.sql.SQLException;
import java.util.HashMap;
import javax.persistence.PersistenceException;
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
            put("ORA-20006", "Cannot delete Flight. A flight segment exists for this flight.");
            put("AIRC_PK", "Cannot create aircraft. This aircraft already exists.");
        }
    };

    public static Result index() {
        return ok(flight_index.render(Flight.all()));
    }

    public static Result create() {
        return ok(flight_create.render(flightForm, Route.all(), AircraftType.all(), Aircraft.all()));
    }

    public static Result save() {
        Form<Flight> filledForm = flightForm.bindFromRequest();
        if (filledForm.hasErrors()) {
            flash("error", "There were errors in the form:");
            return badRequest(flight_create.render(filledForm, Route.all(), AircraftType.all(), Aircraft.all()));
        } else {
            try {
                Flight.create(filledForm.get());
            } catch (PersistenceException e) {
                if (e.getMessage().contains("AIRC_PK")) {
                    flash("error", "Cannot create aircraft. This aircraft already exists.");
                } else {
                    flash("error", "Cannot create airline. A database error occured.");
                }
                return badRequest(flight_create.render(filledForm, Route.all(), AircraftType.all(), Aircraft.all()));
            }
        }
        return ok(flight_index.render(Flight.all()));
    }

    public static Result edit(Integer id) {
        Flight flight = Flight.find.ref(id);
        return ok(flight_edit.render(flight, flightForm.fill(flight), AircraftType.all(), Aircraft.all()));
    }

    public static Result update(Integer id) {
        Form<Flight> filledForm = flightForm.bindFromRequest();
        if (filledForm.hasErrors()) {
            flash("error", "There were errors in the form:");
            Flight flight = Flight.find.ref(id);
            return badRequest(flight_edit.render(flight, filledForm, AircraftType.all(), Aircraft.all()));
        } else {
            Flight.update(filledForm.get().flight_id, filledForm.get().route_id, filledForm.get().dep_date, filledForm.get().arr_time, filledForm.get().dep_time, filledForm.get().aircraft_id, filledForm.get().aircr_type_id);
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
