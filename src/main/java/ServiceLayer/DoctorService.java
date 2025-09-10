package ServiceLayer;

import DatabaseLayer.DatabaseConnection;
import Models.Doctor;

import java.sql.ResultSet;

public class DoctorService {
    private DatabaseConnection singleConnection;

    public DoctorService() {
        singleConnection = DatabaseConnection.getSingleInstance();
    }

    public boolean addDoctor(Doctor doctor) {
        try{
            String query = "INSERT INTO doctor(DocName, specialization, email, contact) VALUES('"+doctor.getName()+ "','"+ doctor.getSpecialization() +"','" +doctor.getEmail()+ "','"+doctor.getPhoneNumber()+"')";
            return singleConnection.ExecuteSQL(query);
        }
        catch(Exception e) {
            System.out.println("Error in adding doctor" + e.getMessage());
            return false;
        }
    }

    public boolean updateDoctor(Doctor doctor) {
        try{
            String query = "UPDATE doctor SET DocName = '"+doctor.getName()+"', specialization = '"+doctor.getSpecialization()+"', email = '"+doctor.getEmail()+"', contact = '"+doctor.getPhoneNumber()+"' WHERE DocID = "+"'"+doctor.getDoctorId()+"'";
            return singleConnection.ExecuteSQL(query);
        }
        catch(Exception e) {
            System.out.println("Error in updating doctor" + e.getMessage());
            return false;
        }
    }

    public ResultSet getAllDoctors() {
        try {
            String query = "SELECT * FROM doctor";
            singleConnection.setPreparedStatement(query);
            return singleConnection.ExecutePreparedStatement();

        } catch (Exception e) {
            System.out.println("Error in getting all doctors: " + e.getMessage());
            return null;
        }
    }

    public ResultSet getDoctorByName(String name) {
        try {
            String query = "SELECT * FROM doctor WHERE DocName = '" + name + "'";
            singleConnection.setPreparedStatement(query);
            return singleConnection.ExecutePreparedStatement();

        } catch (Exception e) {
            System.out.println("Error in getting doctor by name: " + e.getMessage());
            return null;
        }
    }
}
