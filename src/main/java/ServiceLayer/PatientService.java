package ServiceLayer;

import DatabaseLayer.DatabaseConnection;
import Models.Patient;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PatientService {
    private DatabaseConnection singleConnection;

    public PatientService() {
        singleConnection = DatabaseConnection.getSingleInstance();
    }

    public boolean addPatient(Patient patient) {
        try{
            String query = "INSERT INTO patient(Name, Age, Address, Contact, Email,Selected_Doctor) VALUES('"+patient.getName()+ "','"+ patient.getAge() +"','"+patient.getAddress()+"','"+patient.getContact()+"','" +patient.getEmail()+ "','" +patient.getSelected_Doctor()+ "')";
            return singleConnection.ExecuteSQL(query);
        }
        catch(Exception e) {
            System.out.println("Error in adding patient" + e.getMessage());
            return false;
        }
    }

    public boolean updatePatient(Patient patient) {
        try {
            String query = "UPDATE patient SET Name='" + patient.getName() + "', Age='" + patient.getAge() + "', Address='" + patient.getAddress() + "', Contact='" + patient.getContact() + "', Email='" + patient.getEmail() + "', Selected_Doctor= '" + patient.getSelected_Doctor() + "' WHERE patientID=" + patient.getPatientID();
            return singleConnection.ExecuteSQL(query);
        } catch (Exception e) {
            System.out.println("Error in updating patient: " + e.getMessage());
            return false;
        }
    }

    public ResultSet getAllPatients() {
        try {
            String query = "SELECT * FROM patient";
            singleConnection.setPreparedStatement(query);
            return singleConnection.ExecutePreparedStatement();
        } catch (Exception e) {
            System.out.println("Error in fetching patients: " + e.getMessage());
            return null;
        }
    }

    public boolean deletePatient(int patientID) {
        try {
            String query = "DELETE FROM patient WHERE patientID=" + patientID;
            return singleConnection.ExecuteSQL(query);
        } catch (Exception e) {
            System.out.println("Error in deleting patient: " + e.getMessage());
            return false;
        }
    }

    public List<Patient> getAllPatientsList() {
        List<Patient> patients = new ArrayList<>();
        try {
            String query = "SELECT * FROM patient";
            ResultSet rs = singleConnection.executeSelectQuery(query);

            while (rs != null && rs.next()) {
                Patient p = new Patient(
                        rs.getInt("PatientID"),
                        rs.getString("Name"),
                        rs.getInt("Age"),
                        rs.getString("Address"),
                        rs.getInt("Contact"),
                        rs.getString("Email"),
                        rs.getString("Selected_Doctor")
                );
                patients.add(p);
            }
        } catch (Exception e) {
            System.out.println("Error mapping patients: " + e.getMessage());
        }
        return patients;
    }


}