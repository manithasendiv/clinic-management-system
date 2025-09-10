package ServiceLayer;

import DatabaseLayer.DatabaseConnection;
import Models.Patient;

public class PatientService {
    private DatabaseConnection singleConnection;

    public PatientService() {
        singleConnection = DatabaseConnection.getSingleInstance();
    }

    public boolean addPatient(Patient patient) {
        try{
            String query = "INSERT INTO patient(Name, Age, Address, Contact, Email,Selected_Doctor,Date,Time) VALUES('"+patient.getName()+ "','"+ patient.getAge() +"','"+patient.getAddress()+"','"+patient.getContact()+"','" +patient.getEmail()+ "','" +patient.getSelected_Doctor()+ "','" +patient.getDate()+ "','" +patient.getTime()+ "')";
            return singleConnection.ExecuteSQL(query);
        }
        catch(Exception e) {
            System.out.println("Error in adding patient" + e.getMessage());
            return false;
        }
    }

    public boolean updatePatient(Patient patient) {
        try {
            String query = "UPDATE patient SET " +
                    "Name='" + patient.getName() + "', " +
                    "Age='" + patient.getAge() + "', " +
                    "Address='" + patient.getAddress() + "', " +
                    "Contact='" + patient.getContact() + "', " +
                    "Email='" + patient.getEmail() + "' " +
                    "WHERE PatientID=" + patient.getPatientID();

            return singleConnection.ExecuteSQL(query);
        } catch (Exception e) {
            System.out.println("Error in updating patient: " + e.getMessage());
            return false;
        }
    }

}