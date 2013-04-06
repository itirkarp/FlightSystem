package controllers;

import models.Airline;
import models.Route;
import play.*;
import play.data.Form;
import play.mvc.*;

import views.html.*;

public class RouteController extends Controller {

    static Form<Route> routeForm = Form.form(Route.class);
    
    public static Result index() {
        Logger.error("****************************************");
        Logger.error(java.lang.String.format("%04d", 800));
        return ok(route_index.render(Route.all()));
    }

    public static Result create() {
        return ok();
    }
    
    public static Result edit(String id) {
        Route route = Route.find.ref(id);
        return ok();
    }

    public static Result delete(String id) {
        Route.find.ref(id).delete();
        return ok();
    }
}
