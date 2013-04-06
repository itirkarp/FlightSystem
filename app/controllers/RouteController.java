package controllers;

import java.util.HashMap;
import javax.persistence.PersistenceException;
import models.Route;
import play.*;
import play.data.Form;
import play.mvc.*;

import views.html.*;

public class RouteController extends Controller {

    static Form<Route> routeForm = Form.form(Route.class);
    static final HashMap<String, String> errorMessages = new HashMap<String, String>(){
        {
            put("ROUT_PK", "Cannot create route. This route already exists");
            put("ROUT_FROM", "Cannot create route. The source airport does not exist");
            put("ROUT_TO", "Cannot create route. The destination airport does not exist");
            put("ROUT_OPERATED_BY", "Cannot create route. The airline does not exist");
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
        return ok();
    }

    public static Result delete(String id) {
        Route.find.ref(id).delete();
        return ok();
    }
    
    public static Result save() {
        Form<Route> filledForm = routeForm.bindFromRequest();
        if (filledForm.hasErrors()) {
            flash("error", "There were errors in the form:");
            return badRequest(route_create.render(filledForm));
        } else {
            try {
                Route.create(filledForm.get());
            } catch(PersistenceException e) {
                String[] temp = e.getMessage().split("S1784498.");
                temp = temp[1].split("\\) violated");
                String constraintName = temp[0];
                flash("error", errorMessages.get(constraintName));
                return badRequest(route_create.render(filledForm));
            }
        }
        return ok(route_index.render(Route.all()));
    }
    
}
