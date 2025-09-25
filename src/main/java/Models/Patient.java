package Models;

public class Patient {
    private int patientID;
    private String Name;
    private int Age;
    private String Address;
    private int Contact;
    private String Email;
    private String Selected_Doctor;

    public Patient(int patientID, String name, int age, String address, int contact, String email, String selected_Doctor) {
        this.patientID = patientID;
        Name = name;
        Age = age;
        Address = address;
        Contact = contact;
        Email = email;
        Selected_Doctor = selected_Doctor;
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

    public String getSelected_Doctor() {
        return Selected_Doctor;
    }
}
