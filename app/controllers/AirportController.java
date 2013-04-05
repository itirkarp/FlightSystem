package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class AirportController extends Controller {

    public static Result index() {
        return ok(airport_index.render("airport stuff will go here"));
    }
}
