package Entities;

import java.util.UUID;

public class Individual {
    private UUID Id;
    protected String Name;
    protected String phoneNumber;
    protected String emailAddress;

    public Individual(UUID id, String name, String number, String mail) {
        Id = id;
        this.Name = name;
        this.phoneNumber = number;
        this.emailAddress = mail;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String number) {
        this.phoneNumber = number;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String mail) {
        this.emailAddress = mail;
    }

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public String toString(){
        return "Individual{" +
                "id='" + Id.toString() + '\'' +
                "name='" + Name + '\'' +
                ", email='" + emailAddress + '\'' +
                ", phone=" + phoneNumber +
                '}';
    }
}
