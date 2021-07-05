package Services;

import Entities.Location;
import Utils.Logger;

import java.sql.*;
import java.util.ArrayList;

public class LocationService {
    private static LocationService instance;

    public static LocationService getInstance() {
        if (instance == null) {
            instance = new LocationService();
        }
        return instance;
    }

    public final Connection connection;
    public LocationService() {
        connection = DataBase.getDatabase().getConnection();
    }

    public final Logger logger = Logger.getInstance();

    public Location getLocation(Integer id) throws SQLException {
        logger.logOperation(String.format("Got location with id: %d", id));
        String query = "SELECT * FROM location WHERE `locationId` = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet result = statement.executeQuery();

        if (result.next()) {
            String city = result.getString("city");
            String street = result.getString("street");
            Integer streetNumber = result.getInt("street_number");
            String building = result.getString("building");
            String entrance = result.getString("entrance");
            logger.logOperation(String.format("Found location with id: %d", id));
            return new Location(id, city, street, streetNumber, building, entrance);
        }
        logger.logOperation(String.format("Location with id: %d not found!", id));
        return null;
    }

    public ArrayList<Location> getLocations() throws SQLException {
        logger.logOperation("Got all locations.");
        String query = "SELECT * FROM location";
        PreparedStatement statement = connection.prepareStatement(query);

        ResultSet result = statement.executeQuery();
        ArrayList<Location> locations = new ArrayList<>();

        while (result.next()) {
            Integer id = result.getInt("locationId");
            String city = result.getString("city");
            String street = result.getString("street");
            Integer streetNumber = result.getInt("street_number");
            String building = result.getString("building");
            String entrance = result.getString("entrance");
            locations.add(new Location(id, city, street, streetNumber, building, entrance));
        }
        return locations;
    }


    public Integer addLocation(Location location) throws SQLException {
        logger.logOperation("Added a new location: " + location.toString());
        String query = "INSERT INTO location (city, street, street_number, building, entrance) VALUES (?, ?, ?, ?, ?);";
        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, location.city());
        statement.setString(2, location.street());
        statement.setInt(3, location.streetNumber());
        statement.setString(4, location.building());
        statement.setString(5, location.entrance());

        statement.executeUpdate();
        ResultSet id = statement.getGeneratedKeys();
        while (id.next()) {
            return id.getInt( 1 );
        }
        return null;
    }

    public void updateLocation(Location location) throws SQLException {
        logger.logOperation("Updated a location.");
        String query = "UPDATE location SET `city` = ?, `street` = ?, `street_number` = ?, `building` = ?, `entrance` = ? WHERE `idLocation` = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, location.city());
        statement.setString(2, location.street());
        statement.setInt(3, location.streetNumber());
        statement.setString(4, location.building());
        statement.setString(5, location.entrance());
        statement.setInt(6, location.id());
        statement.executeUpdate();
    }


    public void deleteLocation(Integer id) throws SQLException {
        logger.logOperation("Deleted a location.");
        String query = "DELETE FROM location WHERE `locationId` = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    public void deleteLocations() throws SQLException {
        logger.logOperation("Deleted all locations.");
        String query = "DELETE FROM location;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeUpdate();
    }
}
