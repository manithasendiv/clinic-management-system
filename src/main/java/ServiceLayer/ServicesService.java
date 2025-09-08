package ServiceLayer;

import DatabaseLayer.DatabaseConnection;
import Models.Patient;
import Models.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ServicesService {
    private DatabaseConnection singleConn;

    public ServicesService(){
        singleConn =  DatabaseConnection.getSingleInstance();
    }

    public List<Patient> getPatients(){
        List<Patient> patientList = new ArrayList<>();
        try{
            String query1 = "SELECT * FROM patient";
            ResultSet resultSet = singleConn.executeSelectQuery(query1);
            while(resultSet.next()){
                patientList.add(new Patient(resultSet.getInt("PatientID"),resultSet.getString("Name"),resultSet.getInt("Age"),resultSet.getString("PhoneNumber")));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return patientList;
    }


   public Patient getPatientDetails(int PatientID){
        Patient patient = null;
        ResultSet rs = null;
        try{
           String query2 = "SELECT * FROM patient JOIN description ON patient.PatientID = description.PatientID WHERE patient.PatientID =?";
            singleConn.setPreparedStatement(query2);
            singleConn.preparedStatement.setInt(1, PatientID);
             rs = singleConn.ExecutePreparedStatement();
                if(rs != null && rs.next()){
                    patient = new Patient(
                            PatientID,
                            rs.getString("Name"),
                            rs.getInt("Age"),
                           rs.getString("PhoneNumber"),
                           rs.getString("Gender"),
                            rs.getString("RegDate"),
                            rs.getString("Illness"),
                            rs.getString("BloodType"),
                            rs.getString("Allergies")
                    );
                }

       }catch (Exception ex){
           System.out.println("Cannot Receive Patient Details");
        }finally {
            // Close the result set if needed
            if (rs != null) {
                try {
                    rs.close();
               } catch (SQLException e) {
                    System.err.println("Error closing result set: " + e.getMessage());
                }
           }
        }
      return patient;
   }


    public boolean AddNote(Service service) {
        try {
            String query3 = "INSERT INTO notes (ServiceID, Note) VALUES ("
                    + service.getServiceID() + ", '"
                    + service.getSpecialNotes() + "')";
            boolean result = singleConn.ExecuteSQL(query3);
            return result;
        } catch (Exception ex) {
            System.out.println("Cannot Insert a Note: " + ex.getMessage());
            return false;
        }
    }

    public boolean AddFile(Service service){
        try{
            return true;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public Stack<Service> getService(int id){
        Stack<Service> serviceStack = new Stack<>();
        try{
            String query = "select TimeStamp,DoctorName,Name from service where PatientID='"+id+"' order by TimeStamp DESC";
            ResultSet resultSet = singleConn.executeSelectQuery(query);
            while (resultSet.next()) {
                serviceStack.push(
                        new Service(
                                id,
                                resultSet.getString("Name"),
                                resultSet.getString("DoctorName"),
                                resultSet.getString("TimeStamp")
                        )
                );
            }
            return serviceStack;
        } catch (RuntimeException | SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


}
