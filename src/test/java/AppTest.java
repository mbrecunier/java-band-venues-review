import org.junit.*;
import static org.junit.Assert.*;
import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.*;


public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();
  public WebDriver getDefaultDriver() {
      return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Bands And Venues");
  }

  @Test
  public void noBandsDisplayAtFirst() {
    goTo("http://localhost:4567/bands");
    assertThat(pageSource()).contains("No bands yet!");
  }

  @Test
  public void allBandsDisplay() {
    Band testBand = new Band("Milky Way");
    testBand.save();
    goTo("http://localhost:4567/bands");
    assertThat(pageSource()).contains("Milky Way");
  }

  @Test
  public void newlyAddedBandsDisplayCorrectly() {
    goTo("http://localhost:4567/bands");
    fill("#name").with("Salsa Kings");
    submit(".btn-success");
    assertThat(pageSource()).contains("Salsa Kings");
  }

  @Test
  public void eachBandHasIndividualPage() {
    Band testBand = new Band("Salad Bar");
    testBand.save();
    String bandRoute = String.format("http://localhost:4567/bands/%d", testBand.getId());
    goTo(bandRoute);
    assertThat(pageSource()).contains("Venues for Salad Bar");
  }

  @Test
  public void addingVenueToBandDisplays() {
    Band testBand = new Band("Salad Bar");
    testBand.save();
    Venue testVenue = new Venue("Withering Tree");
    testVenue.save();
    testBand.addVenue(testVenue);
    String bandRoute = String.format("http://localhost:4567/bands/%d", testBand.getId());
    goTo(bandRoute);
    assertThat(pageSource()).contains("Withering Tree");
  }

  @Test
  public void updatingBandNameDisplaysCorrectly() {
    Band testBand = new Band("Salad Bar");
    testBand.save();
    String bandRoute = String.format("http://localhost:4567/bands/%d/update", testBand.getId());
    goTo(bandRoute);
    fill("#name").with("Salad Tavern");
    submit(".btn-info");
    assertThat(pageSource()).contains("Salad Tavern");
  }

  @Test
  public void noVenuesDisplayAtFirst() {
    goTo("http://localhost:4567/venues");
    assertThat(pageSource()).contains("No venues yet!");
  }

  @Test
  public void allVenuesDisplay() {
    Venue testVenue = new Venue("Blues House");
    testVenue.save();
    goTo("http://localhost:4567/venues");
    assertThat(pageSource()).contains("Blues House");
  }

  @Test
  public void newlyAddedVenuesDisplayCorrectly() {
    goTo("http://localhost:4567/venues");
    fill("#name").with("The Cave");
    submit(".btn-success");
    assertThat(pageSource()).contains("The Cave");
  }

  @Test
  public void eachVenueHasIndividualPage() {
    Venue testVenue = new Venue("The Room");
    testVenue.save();
    String venueRoute = String.format("http://localhost:4567/venues/%d", testVenue.getId());
    goTo(venueRoute);
    assertThat(pageSource()).contains("Bands for The Room");
  }

  @Test
  public void addingBandToVenueDisplays() {
    Band testBand = new Band("Salad Bar");
    testBand.save();
    Venue testVenue = new Venue("Withering Tree");
    testVenue.save();
    testVenue.addBand(testBand);
    String venueRoute = String.format("http://localhost:4567/venues/%d", testVenue.getId());
    goTo(venueRoute);
    assertThat(pageSource()).contains("Salad Bar");
  }
}
