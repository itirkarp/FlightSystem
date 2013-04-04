package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Airline extends Controller {

    public static Result index() {
        return ok(airline_index.render("airline stuff will go here"));
    }
}
