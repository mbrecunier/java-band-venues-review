import org.sql2o.*;
import java.util.List;

public class Band {
  private String name;
  private int id;

  public Band(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public static List<Band> all() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM bands";
      return con.createQuery(sql).executeAndFetch(Band.class);
    }
  }

  @Override
  public boolean equals(Object otherBand) {
    if(!(otherBand instanceof Band)) {
      return false;
    } else {
      Band newBand = (Band) otherBand;
      return this.getName().equals(newBand.getName());
    }
  }

}
