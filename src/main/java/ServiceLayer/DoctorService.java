package ServiceLayer;

import DatabaseLayer.DatabaseConnection;
import Models.Doctor;

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
}
