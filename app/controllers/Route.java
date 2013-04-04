package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Route extends Controller {

    public static Result index() {
        return ok(route_index.render("route stuff will go here"));
    }
}
