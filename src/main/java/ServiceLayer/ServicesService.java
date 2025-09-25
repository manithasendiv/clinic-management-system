package ServiceLayer;

import DatabaseLayer.DatabaseConnection;
import Models.PatientReport;
import Models.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicesService {
    private DatabaseConnection singleConn;

    public ServicesService(){
        singleConn =  DatabaseConnection.getSingleInstance();
    }

    public List<PatientReport> getPatients(){
        List<PatientReport> patientReportList = new ArrayList<>();
        try{
            String query1 = "SELECT * FROM patient";
            ResultSet resultSet = singleConn.executeSelectQuery(query1);
            while(resultSet.next()){
                patientReportList.add(new PatientReport(resultSet.getInt("PatientID"),resultSet.getString("Name"),resultSet.getInt("Age"),resultSet.getString("PhoneNumber")));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return patientReportList;
    }


   public PatientReport getPatientDetails(int PatientID){
        PatientReport patientReport = null;
        ResultSet rs = null;
        try{
           String query2 = "SELECT * FROM patient JOIN description ON patient.PatientID = description.PatientID WHERE patient.PatientID =?";
            singleConn.setPreparedStatement(query2);
            singleConn.preparedStatement.setInt(1, PatientID);
             rs = singleConn.ExecutePreparedStatement();
                if(rs != null && rs.next()){
                    patientReport = new PatientReport(
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
      return patientReport;
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

    public boolean AddFile(String fname, String filePath, int patientId) {
        String query = "INSERT INTO documents (Name, File, PatientID) VALUES (?, ?, ?)";

        try (PreparedStatement ps = singleConn.getConnection().prepareStatement(query)) {
            ps.setString(1, fname);       // The display name (maybe original file name)
            ps.setString(2, filePath);    // The stored path on disk
            ps.setInt(3, patientId);      // The patient this file belongs to

            int rows = ps.executeUpdate();
            return rows > 0;  // true if insert worked
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    public ArrayList<Service> getService(int id){
        ArrayList<Service> serviceList = new ArrayList<>();
        try {
            String query = "SELECT ServiceID,TimeStamp, DoctorName, Name FROM service WHERE PatientID='" + id + "' ORDER BY TimeStamp DESC";
            ResultSet resultSet = singleConn.executeSelectQuery(query);
            while (resultSet.next()) {
                serviceList.add(new Service(
                        resultSet.getInt("ServiceID"),
                        resultSet.getString("Name"),
                        resultSet.getString("DoctorName"),
                        resultSet.getString("TimeStamp")
                ));
            }
            return serviceList;
        } catch (RuntimeException | SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public ArrayList<Service> getDocuments(int id){
        ArrayList<Service> serviceList = new ArrayList<>();
        try {
            String query = "SELECT DocumentID,Name,File FROM documents WHERE PatientID='" + id + "' ORDER BY TimeStamp DESC";
            ResultSet resultSet = singleConn.executeSelectQuery(query);
            while (resultSet.next()) {
                serviceList.add(new Service(
                        resultSet.getInt("DocumentID"),
                        resultSet.getString("Name"),
                        resultSet.getString("File")
                ));
            }
            return serviceList;
        } catch (RuntimeException | SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<Service> getNotes(int id){
        ArrayList<Service> serviceArrayList = new ArrayList<>();
        try{
            String query = "SELECT notes.ServiceID,notes.Note,notes.TimeStamp,notes.NoteID FROM notes JOIN service ON notes.ServiceID = service.ServiceID WHERE PatientID ="+id;
            ResultSet resultSet = singleConn.executeSelectQuery(query);
            while(resultSet.next()){
                serviceArrayList.add(new Service(
                        resultSet.getInt("ServiceID"),
                        resultSet.getString("Note"),
                        resultSet.getString("TimeStamp"),
                        resultSet.getInt("NoteID")));
            }
            return serviceArrayList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
//    public boolean updateNote(int serviceid,String note){
//        try{
//            String query ="UPDATE notes SET Note = " +note+"WHERE ServiceID="+serviceid;
//            return singleConn.ExecuteSQL(query);
//        } catch (Exception e) {
//            System.out.println("Could not update Note"+e.getMessage());
//            return false;
//        }
//    }
    public boolean deleteNote(int nid){
        try{
           String query ="DELETE FROM notes Where NoteID="+nid;
          return singleConn.ExecuteSQL(query);
         } catch (Exception e) {
           System.out.println("Could not update Note"+e.getMessage());
           return false;
        }
    }


    public boolean addService(Service service){
        try{
            String query = "INSERT INTO service (Name, DoctorName, PatientID) VALUES ('"
                    + service.getServiceName() + "', '"
                    + service.getDoctor() + "', "
                    + service.getPatientID() + ")";

            boolean result = singleConn.ExecuteSQL(query);
            return result;
        }catch (Exception ex){
            System.out.println("Cannot Insert a Service: " + ex.getMessage());
            return false;
        }
    }


    public boolean deleteService(int sid){
        try {
            String query = "DELETE FROM service WHERE ServiceID = " + sid;
            return singleConn.ExecuteSQL(query);
        } catch (Exception e) {
            System.out.println("Error in deleting doctor schedule: " + e.getMessage());
            return false;
        }
    }

    public String getSingleServiceNote(int id) {
        String sql = "SELECT Note FROM notes WHERE ServiceID = " + id;
        String note = null;

        try {
            ResultSet rs = singleConn.executeSelectQuery(sql);

            if (rs != null && rs.next()) {
                note = rs.getString("Note");
            }

            if (rs != null) {
                rs.close(); // close ResultSet to free resources
            }

        } catch (Exception e) {
            System.out.println("Error Retrieving Data: " + e.getMessage());
        }

        return note;
    }

    public boolean addPatientDetails(PatientReport patientReport){
        try{
            String query = "INSERT INTO description (PatientID, Gender, Illness,Allergies,BloodType) VALUES ('"
                    + patientReport.getPatientID() + "', '"
                    + patientReport.getGender() + "', "
                    + patientReport.getIllness() +"', "+ patientReport.getAllergies()+"', "+ patientReport.getBloodType()+ ")";

            boolean result = singleConn.ExecuteSQL(query);
            return result;
        }catch (Exception ex){
            System.out.println("Cannot Insert a Service: " + ex.getMessage());
            return false;
        }
    }

    public boolean updatePatientDetails(PatientReport patientReport) {
        try {
            String query = "UPDATE description SET "
                    + "Gender = '" + patientReport.getGender() + "', "
                    + "Illness = '" + patientReport.getIllness() + "', "
                    + "Allergies = '" + patientReport.getAllergies() + "', "
                    + "BloodType = '" + patientReport.getBloodType() + "' "
                    + "WHERE PatientID = '" + patientReport.getPatientID() + "'";

            boolean result = singleConn.ExecuteSQL(query);
            return result;
        } catch (Exception ex) {
            System.out.println("Cannot Update Patient Details: " + ex.getMessage());
            return false;
        }
    }



}
