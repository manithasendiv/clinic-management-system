package ServiceLayer;

import DatabaseLayer.DatabaseConnection;
import Models.Doctor;
import Models.DoctorSchedule;

import java.sql.ResultSet;

public class DoctorScheduleService {
    private DatabaseConnection singleConnection;

    public DoctorScheduleService() {
        singleConnection = DatabaseConnection.getSingleInstance();
    }

    public boolean addDoctorSchedule(DoctorSchedule doctorSchedule) {
        try{
            String query = "INSERT INTO schedules(doctorName, availableDate, availableTime) VALUES("+ "'" +doctorSchedule.getDoctor().getName()+ "'" + ",'" + doctorSchedule.getDay() + "','" + doctorSchedule.getTime() + "')";
            return singleConnection.ExecuteSQL(query);
        }
        catch(Exception e) {
            System.out.println("Error in adding doctor schedule: " + e.getMessage());
            return false;
        }

    }

    public boolean updateDoctorSchedule(String scheduleID, String doctorName, String date, String time) {
        try {
            String query = "UPDATE schedules SET doctorName = '" + doctorName + "', availableDate = '" + date + "', availableTime = '" + time + "' WHERE scheduleID = '" + scheduleID + "'";
            return singleConnection.ExecuteSQL(query);
        } catch (Exception e) {
            System.out.println("Error in updating doctor schedule: " + e.getMessage());
            return false;
        }
    }

    public boolean removeDoctorSchedule(String sid) {
        try {
            String query = "DELETE FROM schedules WHERE scheduleID = '" + sid + "'";
            return singleConnection.ExecuteSQL(query);
        } catch (Exception e) {
            System.out.println("Error in deleting doctor schedule: " + e.getMessage());
            return false;
        }
    }

    public ResultSet getAllSchedule() {
        try {
            String query = "SELECT * FROM schedules";
            singleConnection.setPreparedStatement(query);
            return singleConnection.ExecutePreparedStatement();

        } catch (Exception e) {
            System.out.println("Error in getting all schedule: " + e.getMessage());
            return null;
        }
    }
}
