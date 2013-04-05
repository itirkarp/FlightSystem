package controllers;

import models.Airport;
import play.*;
import play.data.Form;
import play.mvc.*;

import views.html.*;

public class AirportController extends Controller {

    static Form<Airport> airportForm = Form.form(Airport.class);

    public static Result index() {
        return ok(airport_index.render(Airport.all()));
    }
    
    public static Result create() {
        return ok(airport_create.render(airportForm));
    }
    
    public static Result save() {
        Form<Airport> filledForm = airportForm.bindFromRequest();
        if (filledForm.hasErrors()) {
            return badRequest(airport_create.render(airportForm));
        } else {
            Airport.create(filledForm.get());
        }
        return ok(airport_index.render(Airport.all()));
    }
    
    public static Result edit(String id) {
        Airport airport = Airport.find.ref(id);
        return ok(airport_edit.render(airportForm.fill(airport)));
    }
    
    public static Result update(String id) {
        Form<Airport> filledForm = airportForm.bindFromRequest();
        if (filledForm.hasErrors()) {
            return badRequest(airport_edit.render(airportForm));
        } else {
            Airport.update(filledForm.get().airpt_id, filledForm.get().airpt_name,filledForm.get().country );
        }
        return ok(airport_index.render(Airport.all()));
    }
    
    public static Result delete(String airpt_id) {
        Airport.find.ref(airpt_id).delete();
        return ok(airport_index.render(Airport.all()));
    }
}
