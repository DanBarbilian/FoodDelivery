package Entities;

import Utils.CSVable;

import java.util.ArrayList;
import java.util.UUID;

public class DeliveryMan extends Individual implements CSVable {
    protected int completedDeliveries = 0;

    public DeliveryMan(UUID deliveryManId, String name, String number, String mail, int completedDeliveries) {
        super(deliveryManId, name, number, mail);
        this.completedDeliveries = completedDeliveries;
    }

    public int getCompletedDeliveries() {
        return completedDeliveries;
    }

    public void setCompletedDeliveries(int completedDeliveries) {
        this.completedDeliveries = completedDeliveries;
    }


    @Override
    public String[] convertToString() {
        ArrayList asStr = new ArrayList<String>();
        asStr.add(this.Name);
        asStr.add(this.emailAddress);
        asStr.add(this.phoneNumber);
        asStr.add(Integer.toString(this.completedDeliveries));
        return (String[])asStr.toArray(new String[0]);
    }

    @Override
    public Object parser(String csv) {
        var chunks = csv.split(",");
        UUID deliveryManId = UUID.fromString(chunks[0]);
        String Name = chunks[1];
        String emailAddress = chunks[2];
        String phoneNumber = chunks[3];
        int completedOrders = Integer.parseInt(chunks[4]);

        return new DeliveryMan(deliveryManId, Name, phoneNumber, emailAddress, completedOrders);
    }
}
