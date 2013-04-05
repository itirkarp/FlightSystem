package controllers;

import play.*;
import play.mvc.*;
import models.Airline;
import play.data.Form;
import views.html.*;

public class AirlineController extends Controller {

    static Form<Airline> airlineForm = Form.form(Airline.class);

    public static Result index() {
        return ok(airline_index.render(Airline.all()));
    }

    public static Result create() {
        return ok(airline_create.render(airlineForm));
    }
    
    public static Result save() {
        Form<Airline> filledForm = airlineForm.bindFromRequest();
        if (filledForm.hasErrors()) {
            return badRequest(airline_create.render(airlineForm));
        } else {
            Airline.create(filledForm.get());
        }
        return ok(airline_index.render(Airline.all()));
    }
    
    public static Result edit(String id) {
        Airline airline = Airline.find.ref(id);
        return ok(airline_edit.render(airline, airlineForm.fill(airline)));
    }

    public static Result update(String id) {
        Form<Airline> filledForm = airlineForm.bindFromRequest();
        if (filledForm.hasErrors()) {
            return badRequest(airline_edit.render(Airline.find.ref(id), airlineForm));
        } else {
            Airline.update(filledForm.get().airln_id, filledForm.get().airln_name);
        }
        return ok(airline_index.render(Airline.all()));
    }

    public static Result delete(String airln_id) {
        Airline.find.ref(airln_id).delete();
        return ok(airline_index.render(Airline.all()));
    }
}
