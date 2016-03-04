import org.sql2o.*;
import java.util.List;

public class Venue {
  private String name;
  private int id;

  public Venue(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public static List<Venue> all() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM venues";
      return con.createQuery(sql).executeAndFetch(Venue.class);
    }
  }

  @Override
  public boolean equals(Object otherVenue) {
    if(!(otherVenue instanceof Venue)) {
      return false;
    } else {
      Venue newVenue = (Venue) otherVenue;
      return this.getName().equals(newVenue.getName());
    }
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO venues (name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  public int getId() {
    return id;
  }

  public static Venue find(int id) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM venues WHERE id=:id;";
      Venue venue = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Venue.class);
      return venue;
    }
  }

  public void delete() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM venues WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public static void deleteAll() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM venues *";
      con.createQuery(sql)
        .executeUpdate();
    }
  }

}
