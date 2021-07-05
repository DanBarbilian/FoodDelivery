package Services;

import Entities.DeliveryMan;
import Entities.Individual;
import Utils.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class DeliveryManService {
    private static DeliveryManService instance;

    public static DeliveryManService getInstance() {
        if (instance == null) {
            instance = new DeliveryManService();
        }
        return instance;
    }

    public final Connection connection;
    public DeliveryManService() {
        connection = DataBase.getDatabase().getConnection();
    }

    public final Logger logger = Logger.getInstance();
    private final IndividualService indService = IndividualService.getInstance();

    public void addDeliveryMan(DeliveryMan deliveryMan) throws SQLException {
        logger.logOperation("Added a new delivery man.");
        String query = "SELECT 1 FROM individual WHERE `idIndividual` = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, deliveryMan.getId().toString());
        ResultSet result = statement.executeQuery();

        if (!result.next()) {
            indService.addIndividual(deliveryMan);
        }

        query = "INSERT INTO deliveryman VALUES (?, ?);";
        statement = connection.prepareStatement(query);

        statement.setString(1, deliveryMan.getId().toString());
        statement.setInt(2, deliveryMan.getCompletedDeliveries());

        statement.executeUpdate();
    }

    public ArrayList<DeliveryMan> getDeliveryMen() throws SQLException {
        logger.logOperation("Got all delivery men.");
        String query = "SELECT * FROM deliveryman";
        PreparedStatement statement = connection.prepareStatement(query);

        ResultSet result = statement.executeQuery();
        ArrayList<DeliveryMan> deliveryMen = new ArrayList<>();

        while (result.next()) {
            UUID id = UUID.fromString(result.getString("deliveryManId"));
            Individual individual = indService.getIndividual(id);

            if (individual != null) {
                deliveryMen.add(new DeliveryMan(individual.getId(), individual.getName(), individual.getPhoneNumber(),
                        individual.getEmailAddress(), result.getInt("completedDeliveries")));
            }
        }
        return deliveryMen;
    }

    public void updateDeliveryMan(DeliveryMan deliveryMan) throws SQLException {
        logger.logOperation("Updated a delivery man.");
        String query = "SELECT 1 FROM individual WHERE `idIndividual` = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, deliveryMan.getId().toString());
        ResultSet result = statement.executeQuery();

        if (!result.next()) {
            indService.addIndividual(deliveryMan);
        } else {
            indService.updateIndividual(deliveryMan);
        }

        query = "UPDATE client SET `completedDeliveries` = ? WHERE `idIndividual` = ?;";
        statement = connection.prepareStatement(query);

        statement.setInt(1, deliveryMan.getCompletedDeliveries());
        statement.setString(2, deliveryMan.getId().toString());


        statement.executeUpdate();
    }

    public void deleteDeliveryMan(UUID id) throws SQLException {
        logger.logOperation("Deleted a delivery man.");
        String query = "DELETE FROM deliveryman WHERE `deliverymanId` = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, id.toString());
        statement.executeUpdate();
        indService.deleteIndividual(id);
    };
}
