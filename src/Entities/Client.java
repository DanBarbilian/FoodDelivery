package Entities;

import Utils.CSVable;

import java.util.ArrayList;
import java.util.UUID;

public class Client extends Individual implements CSVable<Client> {
    private Location clientAddress;

    public Client(UUID clientId, String name, String number, String mail, Location address) {
        super(clientId, name, number, mail);
        this.clientAddress = address;
    }

    public void setClientAddress(Location address) {
        clientAddress = address;
    }

    public Location getClientAddress() {
        return clientAddress;
    }

    @Override
    public String toString() {
        return "Client{" +
                 super.toString() + " - " +
                clientAddress.toString() +
                '}';
    }

    @Override
    public String[] convertToString() {
        ArrayList asStr = new ArrayList<String>();
        asStr.add(this.Name);
        asStr.add(this.emailAddress);
        asStr.add(this.phoneNumber);
        asStr.add(this.clientAddress.toString());
        return (String[])asStr.toArray(new String[0]);
    }

    @Override
    public Object parser(String csv) {
        var chunks = csv.split(",");
        UUID clientId = UUID.fromString(chunks[0]);
        String Name = chunks[1];
        String emailAddress = chunks[2];
        String phoneNumber = chunks[3];
        Integer clientLocId = Integer.parseInt(chunks[4]);
        String clientCity = chunks[5];
        String clientStreet = chunks[6];
        int clientStreetNo = Integer.parseInt(chunks[7]);

        return new Client(clientId, Name, phoneNumber, emailAddress, new Location(clientLocId,
                clientCity,clientStreet,clientStreetNo, "", ""));
    }
}
