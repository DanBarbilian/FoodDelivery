package Main;

import Entities.Client;
import Entities.Location;
import Services.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Collectors;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static final DataBase db = DataBase.getDatabase();
    private static final ClientService clientService = ClientService.getInstance();
    private static final IndividualService individualService = IndividualService.getInstance();
    private static final DeliveryManService deliveryManService = DeliveryManService.getInstance();
    private static final LocationService locationService = LocationService.getInstance();

    public static void printMenuItems() {
        System.out.println("1. Add client.");
        System.out.println("2. Delete client.");
        System.out.println("3. Update client.");
        System.out.println("4. List clients.");

        System.out.println("5. Add delivery man.");
        System.out.println("6. Delete delivery man.");
        System.out.println("7. Update delivery man.");
        System.out.println("8. List delivery men.");

        System.out.println("9. Add location.");
        System.out.println("10. Delete location.");
        System.out.println("11. Update location.");
        System.out.println("12. List location.");
    }

    private static String scanLine() {
        System.out.print("> ");
        return scanner.nextLine();
    }


    public static void main(String[] args) {

        boolean quit = false;

        while (!quit) {
            // print instructions
            System.out.println("--- Food Delivery ---");

            // print the menu items every time
            printMenuItems();

            // allow for input
            String option = scanLine();

            // show the input
            System.out.println("\nEntered " + option);

            // act depending on the input
            try {
                switch (option) {
                    case "q":
                        System.out.println("Quitting application...");
                        quit = true;
                        break;
                    case "1":
                        System.out.print("Name: ");
                        var name = scanner.nextLine();
                        System.out.print("Email address: ");
                        var email = scanner.nextLine();
                        System.out.print("Phone number: ");
                        var phone = scanner.nextLine();
                        System.out.print("City: ");
                        var city = scanner.nextLine();
                        System.out.print("Street: ");
                        var street = scanner.nextLine();
                        System.out.print("Number: ");
                        var number = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Building: ");
                        var building = scanner.nextLine();
                        System.out.print("Entrance: ");
                        var entrance = scanner.nextLine();

                        Location loc = new Location(0, city, street, number, building, entrance);
                        Client client = new Client(UUID.randomUUID(), name, phone, email, loc);
                        clientService.addClient(client);
                        break;
                    case "2":
                        System.out.print("Id: ");
                        var id = scanner.nextLine();
                        clientService.deleteClient(UUID.fromString(id));
                        break;
                    case "4":
                        ArrayList<Client> clients = clientService.getClients();
                        System.out.println(clients.size());
                        System.out.println(clients.stream().map(Object::toString).collect(Collectors.joining(",\n")));
                        break;
                    case "9":
                        System.out.print("City: ");
                        city = scanner.nextLine();
                        System.out.print("Street: ");
                        street = scanner.nextLine();
                        System.out.print("Number: ");
                        number = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Building: ");
                        building = scanner.nextLine();
                        System.out.print("Entrance: ");
                        entrance = scanner.nextLine();

                        locationService.addLocation(new Location(0, city, street, number, building, entrance));
                        break;
                    case "12":
                        ArrayList<Location> locations = locationService.getLocations();
                        System.out.println(locations.stream().map(Object::toString).collect(Collectors.joining(",\n")));
                        break;
                    default:
                        System.out.println("An error has occured: Not the type of input expected! ");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            System.out.println();
        }
    }


//        Location loc = new Location(3, "Bucuresti", "Splaiul Independetei", 35, "Bloc 32", "A-II");
//        DataBase db = DataBase.getDatabase();
//        try {
////            db.addLocation(loc);
//            ArrayList<Location> locs = db.getLocations();
//            System.out.println(locs.stream().map(Object::toString).collect(Collectors.joining(",")));
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
////        String url = "jdbc:mysql://localhost:3306/delivery";
//        String username = "java";
//        String password = "password";
//
//        System.out.println("Connecting database...");
//
//        try {
//            Connection connection = DriverManager.getConnection(url, username, password);
//            System.out.println("Database connected!");
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }

}
