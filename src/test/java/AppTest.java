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
  public void noBandsDisplay() {
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
}
