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


}
