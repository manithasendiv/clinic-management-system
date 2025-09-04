package Models;

public class Doctor {
    private String doctorId;
    private String name;
    private String specialization;
    private String email;
    private String phoneNumber;

    public Doctor(String doctorId, String name, String specialization, String email, String phoneNumber) {
        this.doctorId = doctorId;
        this.name = name;
        this.specialization = specialization;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
