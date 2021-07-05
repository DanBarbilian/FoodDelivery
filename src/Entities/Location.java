package Entities;

public record Location(Integer id, String city, String street, Integer streetNumber, String building, String entrance) {

    public Location(Integer id, String city, String street, Integer streetNumber, String building, String entrance) {
        this.id = id;
        this.city = city;
        this.street = street;
        this.streetNumber = streetNumber;
        this.building = building;
        this.entrance = entrance;
    }

    @Override
    public String toString() {
        return "Location{" +
                "city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", streetNumber=" + streetNumber +
                ", building='" + building + '\'' +
                ", entrance=" + entrance +
                '}';
    }
}
