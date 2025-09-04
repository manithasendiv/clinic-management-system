package Models;

public class Patient {
    private int patientID;
    private String Name;
    private int Age;
    private String Address;
    private int Contact;
    private String Email;

    public Patient(int patientID, String name, int age, String address, int contact, String email) {
        this.patientID = patientID;
        Name = name;
        Age = age;
        Address = address;
        Contact = contact;
        Email = email;
    }

    public int getPatientID() {
        return patientID;
    }

    public String getName() {
        return Name;
    }

    public int getAge() {
        return Age;
    }

    public String getAddress() {
        return Address;
    }

    public int getContact() {
        return Contact;
    }

    public String getEmail() {
        return Email;
    }
}
