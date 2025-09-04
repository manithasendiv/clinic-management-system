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
            String query = "INSERT INTO patient(Name, Age, Address, Contact, Email) VALUES('"+patient.getName()+ "','"+ patient.getAge() +"','"+patient.getAddress()+"','"+patient.getContact()+"','" +patient.getEmail()+ "')";
            return singleConnection.ExecuteSQL(query);
        }
        catch(Exception e) {
            System.out.println("Error in adding patient" + e.getMessage());
            return false;
        }
    }
}