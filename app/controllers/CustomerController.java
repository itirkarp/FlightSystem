package controllers;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import javax.persistence.PersistenceException;
import models.Customer;
import play.data.Form;
import play.mvc.*;
import views.html.customer.*;


public class CustomerController extends Controller{
    
    static Form<Customer> customerForm = Form.form(Customer.class);
    
    static final HashMap<String, String> errorMessages = new HashMap<String, String>() {
        {
            put("CUST_PK", "Cannot create customer. This customer ID already exists.");
        }
    };
    
    public static Result index() {
        return ok(customer_index.render(Customer.all()));
    }
    
    public static Result create() {
        return ok(customer_create.render(customerForm));
    }
    
    public static Result save() {
        Form<Customer> filledForm = customerForm.bindFromRequest();
        if (filledForm.hasErrors()) {
            flash("error", "There were errors in the form:");
            return badRequest(customer_create.render(filledForm));
        } else {
            try {
                Customer.create(filledForm.get());
            } catch (PersistenceException e) {
                if (e.getMessage().contains("CUST_PK")) {
                    flash("error", "Cannot create customer. This customer ID already exists.");
                } else {
                    flash("error", "Cannot create customer. A database error occured.");
                }
                return badRequest(customer_create.render(filledForm));
            }
        }
        return ok(customer_index.render(Customer.all()));
    }
}
