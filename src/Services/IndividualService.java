package Services;

import Entities.Individual;
import Utils.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;


public class IndividualService {
    private static IndividualService instance;

    public static IndividualService getInstance() {
        if (instance == null) {
            instance = new IndividualService();
        }
        return instance;
    }

    public final Connection connection;
    public IndividualService() {
        connection = DataBase.getDatabase().getConnection();
    }

    public final Logger logger = Logger.getInstance();

    public Individual getIndividual(UUID id) throws SQLException {
        logger.logOperation(String.format("Got individual with id: %s", id.toString()));
        String query = "SELECT * FROM individual where idIndividual = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, id.toString());
        ResultSet result = statement.executeQuery();

        if (result.next()) {
            String name = result.getString("Name");
            String phone = result.getString("phoneNumber");
            String email = result.getString("emailAddress");
            return new Individual(id, name, phone, email);
        }
        return null;
    }

    public void addIndividual(Individual individual) throws SQLException {
        logger.logOperation("Added a new individual.");
        String query = "INSERT INTO individual VALUES (?, ?, ?, ?);";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setString(1, individual.getId().toString());
        statement.setString(2, individual.getName());
        statement.setString(3, individual.getPhoneNumber());
        statement.setString(4, individual.getEmailAddress());

        statement.executeUpdate();
    }

    public void updateIndividual(Individual individual) throws SQLException {
        logger.logOperation("Updated an individual.");
        String query = "UPDATE individual SET `Name` = ?, `phoneNumber` = ?, `emailAddress` = ? WHERE `idIndividual` = ?;";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setString(1, individual.getName());
        statement.setString(2, individual.getPhoneNumber());
        statement.setString(3, individual.getEmailAddress());
        statement.setString(4, individual.getId().toString());

        statement.executeUpdate();
    }

    public void deleteIndividual(UUID id) throws SQLException {
        logger.logOperation("Deleted an individual.");
        String query = "DELETE FROM individual WHERE `idIndividual` = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, id.toString());
        statement.executeUpdate();
    }

    public void deleteIndividuals() throws SQLException {
        logger.logOperation("Deleted all individuals.");
        String query = "DELETE FROM individual;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeUpdate();
    }
}
