import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.List;

public class VenueTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void venue_instantiatesCorrectly_true() {
    Venue testVenue = new Venue("The Club");
    assertTrue(testVenue instanceof Venue);
  }

  @Test
  public void getName_returnsName_string() {
    Venue testVenue = new Venue("The Club");
    assertEquals("The Club", testVenue.getName());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Venue.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfVenuesAreTheSame() {
    Venue testVenue1 = new Venue("Shiny Room");
    Venue testVenue2 = new Venue("Shiny Room");
    assertTrue(testVenue1.equals(testVenue2));
  }

  @Test
  public void save_savesVenueToDatabase_true() {
    Venue testVenue = new Venue("Jiggy House");
    testVenue.save();
    assertTrue(Venue.all().contains(testVenue));
  }

  @Test
  public void getId_returnsId() {
    Venue testVenue = new Venue("Jiggy House");
    testVenue.save();
    assertTrue(testVenue.getId() > 0);
  }

  @Test
  public void find_returnsVenueFromDatabase() {
    Venue testVenue = new Venue("Jiggy House");
    testVenue.save();
    assertEquals(testVenue, Venue.find(testVenue.getId()));
  }

  @Test
  public void delete_deletesVenueFromDatabase() {
    Venue testVenue = new Venue("Jiggy House");
    testVenue.save();
    testVenue.delete();
    assertFalse(Venue.all().contains(testVenue));
  }

  @Test
  public void deleteAll_deletesAllVenues() {
    Venue testVenue1 = new Venue("Jiggy House");
    Venue testVenue2 = new Venue("Cantina");
    testVenue1.save();
    testVenue2.save();
    Venue.deleteAll();
    assertTrue(Venue.all().size() == 0);
  }


}
