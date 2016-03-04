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


}
