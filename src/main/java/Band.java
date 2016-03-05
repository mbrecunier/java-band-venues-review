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

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO bands (name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  public int getId() {
    return id;
  }

  public static Band find(int id) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM bands WHERE id=:id;";
      Band band = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Band.class);
      return band;
    }
  }

  public void delete() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM bands_venues WHERE band_id = :id;" + "DELETE FROM bands WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public static void deleteAll() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM bands *";
      con.createQuery(sql)
        .executeUpdate();
    }
  }

  public List<Venue> getVenues() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT venues.* FROM bands JOIN bands_venues ON bands.id = bands_venues.band_id JOIN venues ON bands_venues.venue_id = venues.id WHERE bands.id = :id";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Venue.class);
    }
  }

  public void addVenue(Venue venue) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO bands_venues (band_id, venue_id) VALUES (:band_id, :venue_id)";
      con.createQuery(sql)
        .addParameter("band_id", this.id)
        .addParameter("venue_id", venue.getId())
        .executeUpdate();
    }
  }

  public void update(String newName) {
    this.name = newName;
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE bands SET name = :newName WHERE id = :id";
      con.createQuery(sql)
        .addParameter("newName", newName)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

}
