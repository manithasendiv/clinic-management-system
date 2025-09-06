package Models;

public class Patient {
    private int patientID;
    private String name;
    private int age;
    private String PhoneNumber;
    private String gender;
    private String regDate;
    private String illness;
    private String bloodType;
    private String allergies;

    public Patient(int patientID, String name, int age, String phoneNumber) {
        this.patientID = patientID;
        this.name = name;
        this.age = age;
        PhoneNumber = phoneNumber;
    }

    public Patient(int patientID, String name, int age, String phoneNumber, String gender, String regDate, String illness, String bloodType, String allergies) {
        this.patientID = patientID;
        this.name = name;
        this.age = age;
        PhoneNumber = phoneNumber;
        this.gender = gender;
        this.regDate = regDate;
        this.illness = illness;
        this.bloodType = bloodType;
        this.allergies = allergies;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getIllness() {
        return illness;
    }

    public void setIllness(String illness) {
        this.illness = illness;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
