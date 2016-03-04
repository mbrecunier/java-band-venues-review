import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.List;

public class BandTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void band_instantiatesCorrectly_true() {
    Band testBand = new Band("Stars");
    assertTrue(testBand instanceof Band);
  }

  @Test
  public void getName_returnsName_string() {
    Band testBand = new Band("Stars");
    assertEquals("Stars", testBand.getName());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Band.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfBandsAreTheSame() {
    Band testBand1 = new Band("Killers");
    Band testBand2 = new Band("Killers");
    assertTrue(testBand1.equals(testBand2));
  }

  @Test
  public void save_savesBandToDatabase_true() {
    Band testBand = new Band("Constant Banana");
    testBand.save();
    assertTrue(Band.all().contains(testBand));
  }

  @Test
  public void getId_returnsId() {
    Band testBand = new Band("Constant Banana");
    testBand.save();
    assertTrue(testBand.getId() > 0);
  }

  @Test
  public void find_returnsBandFromDatabase() {
    Band testBand = new Band("Constant Banana");
    testBand.save();
    assertEquals(testBand, Band.find(testBand.getId()));
  }

  @Test
  public void delete_deletesBandFromDatabase() {
    Band testBand = new Band("Constant Banana");
    testBand.save();
    testBand.delete();
    assertFalse(Band.all().contains(testBand));
  }

  @Test
  public void deleteAll_deletesAllBands() {
    Band testBand1 = new Band("Constant Banana");
    Band testBand2 = new Band("Horse Mouth");
    testBand1.save();
    testBand2.save();
    Band.deleteAll();
    assertTrue(Band.all().size() == 0);
  }

  @Test
  public void getVenues_returnsEmptyAtFirst() {
    Band testBand = new Band("Tree Huggers");
    testBand.save();
    assertTrue(testBand.getVenues().size() == 0);
  }

  @Test
  public void addVenue_addsVenueToBand() {
    Venue testVenue = new Venue("The Moon");
    testVenue.save();
    Band testBand = new Band("Orange Flaggers");
    testBand.save();
    testBand.addVenue(testVenue);
    assertTrue(testBand.getVenues().contains(testVenue));
  }


}
