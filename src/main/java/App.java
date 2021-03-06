import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.List;

public class App {

  public static void main(String[] args) {
    // staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/bands", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      List<Band> bands = Band.all();
      model.put("bands", bands);
      model.put("template", "templates/bands.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/bands/add", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      Band newBand = new Band(name);
      newBand.save();
      response.redirect("/bands");
      return null;
    });

    get("/bands/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Band band = Band.find(id);
      List<Venue> venues = Venue.all();
      model.put("band", band);
      model.put("venues", venues);
      model.put("template", "templates/band.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/bands/:id/addvenue", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int bandId = Integer.parseInt(request.params("id"));
      int venueId = Integer.parseInt(request.queryParams("venue"));
      Band band = Band.find(bandId);
      Venue venue = Venue.find(venueId);
      List<Venue> venues = Venue.all();
      band.addVenue(venue);
      model.put("band", band);
      model.put("venues", venues);
      model.put("template", "templates/band.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/bands/:id/update", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int bandId = Integer.parseInt(request.params("id"));
      Band band = Band.find(bandId);
      model.put("band", band);
      model.put("template", "templates/update.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/bands/:id/update", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int bandId = Integer.parseInt(request.params("id"));
      Band band = Band.find(bandId);
      String newName = request.queryParams("name");
      band.update(newName);
      model.put("band", band);
      model.put("template", "templates/band.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/bands/deleted/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int bandId = Integer.parseInt(request.params("id"));
      Band band = Band.find(bandId);
      band.delete();
      response.redirect("/bands");
      return null;
    });

    post("/bands/clear", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Band.deleteAll();
      response.redirect("/bands");
      return null;
    });

    get("/venues", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      List<Venue> venues = Venue.all();
      model.put("venues", venues);
      model.put("template", "templates/venues.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/venues/add", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      Venue newVenue = new Venue(name);
      newVenue.save();
      response.redirect("/venues");
      return null;
    });

    get("/venues/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int venueId = Integer.parseInt(request.params("id"));
      Venue venue = Venue.find(venueId);
      List<Band> bands = Band.all();
      model.put("bands", bands);
      model.put("venue", venue);
      model.put("template", "templates/venue.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/venues/:id/addband", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int venueId = Integer.parseInt(request.params("id"));
      int bandId = Integer.parseInt(request.queryParams("band"));
      Venue venue = Venue.find(venueId);
      Band band = Band.find(bandId);
      List<Band> bands = Band.all();
      venue.addBand(band);
      model.put("venue", venue);
      model.put("bands", bands);
      model.put("template", "templates/venue.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/venues/deleted/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int venueId = Integer.parseInt(request.params("id"));
      Venue venue = Venue.find(venueId);
      venue.delete();
      response.redirect("/venues");
      return null;
    });

  }
}
