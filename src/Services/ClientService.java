package Services;

import Entities.Client;
import Entities.Individual;
import Entities.Location;
import Utils.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;


public class ClientService {
    private static ClientService instance;

    public static ClientService getInstance() {
        if (instance == null) {
            instance = new ClientService();
        }
        return instance;
    }

    public final Connection connection;
    public ClientService() {
        connection = DataBase.getDatabase().getConnection();
    }

    public final Logger logger = Logger.getInstance();
    private final IndividualService indService = IndividualService.getInstance();
    private final LocationService locService = LocationService.getInstance();

    public void addClient(Client client) throws SQLException {
        logger.logOperation("Added a new client: " + Arrays.toString(client.convertToString()));
        String query = "SELECT 1 FROM individual WHERE `idIndividual` = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, client.getId().toString());
        ResultSet result = statement.executeQuery();

        if (!result.next()) {
            indService.addIndividual(client);
        }

        Location loc = client.getClientAddress();
        query = "SELECT 1 FROM location WHERE `locationId` = ?;";
        statement = connection.prepareStatement(query);
        statement.setInt(1, loc.id());
        result = statement.executeQuery();

        Integer locId = loc.id();

        if (!result.next()) {
            locId = locService.addLocation(loc);
        }

        query = "INSERT INTO client VALUES (?, ?);";
        statement = connection.prepareStatement(query);

        statement.setString(1, client.getId().toString());
        statement.setInt(2, locId);

        System.out.println(statement.toString());

        statement.executeUpdate();
    }

    public ArrayList<Client> getClients() throws SQLException {
        logger.logOperation("Got all clients.");
        String query = "SELECT * FROM client";
        PreparedStatement statement = connection.prepareStatement(query);

        ResultSet result = statement.executeQuery();
        ArrayList<Client> clients = new ArrayList<>();

        while (result.next()) {
            UUID id = UUID.fromString(result.getString("clientId"));
            Individual individual = indService.getIndividual(id);

            Integer locId = result.getInt("client_address");
            Location location = locService.getLocation(locId);

            if (individual != null && location != null) {
                clients.add(new Client(individual.getId(), individual.getName(), individual.getPhoneNumber(),
                        individual.getEmailAddress(), location));
            }
        }
        return clients;
    }

    public void updateClient(Client client) throws SQLException {
        logger.logOperation("Updated a client.");
        String query = "SELECT 1 FROM individual WHERE `idIndividual` = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, client.getId().toString());
        ResultSet result = statement.executeQuery();

        if (!result.next()) {
            indService.addIndividual(client);
        } else {
            indService.updateIndividual(client);
        }

        Location loc = client.getClientAddress();
        query = "SELECT 1 FROM location WHERE `locationId` = ?;";
        statement = connection.prepareStatement(query);
        statement.setInt(1, loc.id());
        result = statement.executeQuery();

        if (!result.next()) {
            locService.addLocation(loc);
        } else {
            locService.updateLocation(client.getClientAddress());
        }

        query = "UPDATE client SET `client_address` = ? WHERE `idIndividual` = ?;";
        statement = connection.prepareStatement(query);

        statement.setInt(1, loc.id());
        statement.setString(2, client.getId().toString());

        statement.executeUpdate();
    }

    public void deleteClient(UUID id) throws SQLException {
        logger.logOperation("Deleted a client.");
        String query = "DELETE FROM client WHERE `clientId` = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, id.toString());
        statement.executeUpdate();
        indService.deleteIndividual(id);
    }

}
