
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.data.DynamicForm;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.RequiredValidator;
import play.i18n.Lang;
import play.libs.F;
import play.libs.F.*;
import models.Airline;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import play.data.Form;

public class AirlineControllerTest {

    @Test
    @Ignore
    public void simpleCheck() {
        int a = 1 + 1;
        assertThat(a).isEqualTo(2);
    }

    @Test
    @Ignore
    public void renderTemplate() {
        Content html = views.html.index.render("Your new application is ready.");
        assertThat(contentType(html)).isEqualTo("text/html");
        assertThat(contentAsString(html)).contains("home");
    }

    @Test
    public void indexRendersAirlineInfo() {
        List<Airline> airlines = Lists.newArrayList();
        airlines.add(new Airline("QA", "Qantas"));
        airlines.add(new Airline("JE", "Jetstar"));
        Content html = views.html.airline_index.render(airlines);
        assertThat(contentAsString(html)).contains("Qantas");
        assertThat(contentAsString(html)).contains("Jetstar");
    }

    @Test
    public void createRendersNewAirlineForm() {
        Form<Airline> airlineForm = Form.form(Airline.class);
        Content html = views.html.airline_create.render(airlineForm);
        assertThat(contentAsString(html)).contains("Airline ID");
        assertThat(contentAsString(html)).contains("Airline Name");
        assertThat(contentAsString(html)).contains("Create");
    }

    @Test
    public void editRendersEditAirlineForm() {
        Form<Airline> airlineForm = Form.form(Airline.class);
        Content html = views.html.airline_edit.render(airlineForm.fill(new Airline("QA", "Qantas")));
        assertThat(contentAsString(html)).contains("QA");
        assertThat(contentAsString(html)).contains("Qantas");
        assertThat(contentAsString(html)).contains("Save");
    }

    
}
