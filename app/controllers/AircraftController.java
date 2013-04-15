package controllers;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import javax.persistence.PersistenceException;
import models.Aircraft;
import play.mvc.*;
import views.html.aircraft.*;
import play.data.Form;
import play.mvc.Result;


public class AircraftController extends Controller {

    static Form<Aircraft> aircraftForm = Form.form(Aircraft.class);
    static final HashMap<String, String> errorMessages = new HashMap<String, String>() {
        {
            put("ORA-20004", "Cannot delete aircraft type. A aircraft exists for this aircraft type.");
            put("AIRT_PK", "Cannot create aircraft type. This aircraft type already exists.");
        }
    };

    public static Result index() {
        return ok(aircraft_index.render(Aircraft.all()));
    }

    public static Result create() {
        return ok(aircraft_create.render(aircraftForm));
    }

    public static Result save() {
        Form<Aircraft> filledForm = aircraftForm.bindFromRequest();
        if (filledForm.hasErrors()) {
            flash("error", "There were errors in the form:");
            return badRequest(aircraft_create.render(filledForm));
        } else {
            try {
                Aircraft.create(filledForm.get());
            } catch (PersistenceException e) {
                if (e.getMessage().contains("AIRT_PK")) {
                    flash("error", "Cannot create aircraft type. This aircraft type already exists.");
                } else {
                    flash("error", "Cannot create airline. A database error occured.");
                }
                return badRequest(aircraft_create.render(filledForm));
            }
        }
        return ok(aircraft_index.render(Aircraft.all()));
    }

    public static Result edit(String id) {
        Aircraft aircraft = Aircraft.find.ref(id);
        return ok(aircraft_edit.render(aircraft, aircraftForm.fill(aircraft)));
    }

    public static Result update(String id) {
        Form<Aircraft> filledForm = aircraftForm.bindFromRequest();
        if (filledForm.hasErrors()) {
            flash("error", "There were errors in the form:");
            Aircraft aircraft = Aircraft.find.ref(id);
            return badRequest(aircraft_edit.render(aircraft, filledForm));
        } else {
            Aircraft.update(filledForm.get().aircraft_id, filledForm.get().aircraft_name, filledForm.get().aircr_type_ID, filledForm.get().seats_qty_F, filledForm.get().seats_qty_B, filledForm.get().seats_qty_E);
        }
        return ok(aircraft_index.render(Aircraft.all()));
    }

    public static Result delete(String id) {
        Aircraft.find.ref(id).delete();
        return ok(aircraft_index.render(Aircraft.all()));
    }

    public static void deleteAircraft(String aircr_type_id) throws SQLException {
        Connection connection = null;
        CallableStatement callableStatement = null;
        connection = play.db.DB.getConnection();
        String storedProc = "{call SP_DELETE_AIRCRAFT_TYPE(?)}";
        callableStatement = connection.prepareCall(storedProc);
        callableStatement.setString(1, aircr_type_id);
        callableStatement.executeUpdate();
    }
}
