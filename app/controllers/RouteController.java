package controllers;

import com.avaje.ebean.Ebean;
import java.util.ArrayList;
import java.util.HashMap;
import javax.persistence.PersistenceException;
import models.Route;
import models.RouteSegment;
import play.*;
import play.data.Form;
import play.data.validation.ValidationError;
import play.mvc.*;

import views.html.route.*;

public class RouteController extends Controller {

    static Form<Route> routeForm = Form.form(Route.class);
    static final HashMap<String, String> errorMessages = new HashMap<String, String>() {
        {
            put("ROUT_PK", "Cannot save route. This route already exists");
            put("ROUT_FROM", "Cannot save route. The source airport does not exist");
            put("ROUT_TO", "Cannot save route. The destination airport does not exist");
            put("ROUT_OPERATED_BY", "Cannot save route. The airline does not exist");
        }
    };

    public static Result index() {
        return ok(route_index.render(Route.all()));
    }

    public static Result create() {
        return ok(route_create.render(routeForm));
    }

    public static Result edit(String id) {
        Route route = Route.find.ref(id);
        return ok(route_edit.render(route, routeForm.fill(route)));
    }

    public static Result update(String id) {
        Form<Route> filledForm = routeForm.bindFromRequest();
        Route route = Route.find.ref(id);
        if (filledForm.hasErrors()) {
            flash("error", "There were errors in the form:");
            return badRequest(route_edit.render(route, filledForm));
        } else if (filledForm.get().areAirportsSame()) {
            ArrayList<ValidationError> errorList = new ArrayList<ValidationError>() {{
                    add(new ValidationError("Source and destination airports should be different.", 
                            "Source and destination airports should be different."));
                }};
            filledForm.errors().put("airpt_id_from", errorList);
            flash("error", "There were errors in the form:");
            return badRequest(route_edit.render(route, filledForm));
        } else {
            Route newRoute = filledForm.get();
            try {
                
                Route.update(route.route_id, newRoute.arr_time, newRoute.airpt_id_to,
                        newRoute.dep_time, newRoute.airpt_id_from, newRoute.airln_id, newRoute.day_no);
            } catch (PersistenceException e) {
                String[] temp = e.getMessage().split("S1784498.");
                if (temp.length > 1) {
                    temp = temp[1].split("\\) violated");
                    String constraintName = temp[0];
                    flash("error", errorMessages.get(constraintName));
                } else {
                    flash("error", "Cannot create route. A database error occurred: " + e.getMessage());
                }
                return badRequest(route_edit.render(newRoute, filledForm));
            }
        }
        return ok(route_index.render(Route.all()));
    }

    public static Result delete(String id) {
        Route.find.ref(id).delete();
        return ok(route_index.render(Route.all()));
    }

    public static Result save() {
        Form<Route> filledForm = routeForm.bindFromRequest();
        if (filledForm.hasErrors()) {
            flash("error", "There were errors in the form:");
            return badRequest(route_create.render(filledForm));
        } else if (filledForm.get().areAirportsSame()) {
            ArrayList<ValidationError> errorList = new ArrayList<ValidationError>() {{
                    add(new ValidationError("Source and destination airports should be different.", 
                            "Source and destination airports should be different."));
                }};
            filledForm.errors().put("airpt_id_from", errorList);
            flash("error", "There were errors in the form:");
            return badRequest(route_create.render(filledForm));
        } else {
            Ebean.beginTransaction();
            try {
                Route route = filledForm.get();
                Route.create(route);
                RouteSegment.create(route.arr_time, route.dep_time, route.airpt_id_to, route.airpt_id_from, route);
                Ebean.commitTransaction();
            } catch (PersistenceException e) {
                String[] temp = e.getMessage().split("S1784498.");
                if (temp.length > 1) {
                    temp = temp[1].split("\\) violated");
                    String constraintName = temp[0];
                    flash("error", errorMessages.get(constraintName));
                } else {
                    flash("error", "Cannot create route. A database error occurred: " + e.getMessage());
                }
                Ebean.rollbackTransaction();
                return badRequest(route_create.render(filledForm));
            } finally {
                Ebean.endTransaction();  
            }
        }
        return ok(route_index.render(Route.all()));
    }
}
