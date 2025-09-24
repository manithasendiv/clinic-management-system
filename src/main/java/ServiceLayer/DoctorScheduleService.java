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
            String query = "INSERT INTO doctor_schedule(scheduleID,doctorId, day, time) VALUES(" + doctorSchedule.getDoctor().getDoctorId() + ",'" + doctorSchedule.getDay() + "','" + doctorSchedule.getTime() + "')";
            return singleConnection.ExecuteSQL(query);
        }
        catch(Exception e) {
            System.out.println("Error in adding doctor schedule: " + e.getMessage());
            return false;
        }

    }

    public ResultSet getAllSchedule() {
        try {
            String query = "SELECT * FROM doctor_schedule";
            singleConnection.setPreparedStatement(query);
            return singleConnection.ExecutePreparedStatement();

        } catch (Exception e) {
            System.out.println("Error in getting all schedule: " + e.getMessage());
            return null;
        }
    }

    public boolean updateDoctorSchedule(DoctorSchedule doctorSchedule) {
        try {
            String query = "UPDATE doctor_schedule SET doctorId = " + doctorSchedule.getDoctor().getDoctorId() + ", day = '" + doctorSchedule.getDay() + "', time = '" + doctorSchedule.getTime() + "' WHERE scheduleID = " + doctorSchedule.getScheduleId();
            return singleConnection.ExecuteSQL(query);
        } catch (Exception e) {
            System.out.println("Error in updating doctor schedule: " + e.getMessage());
            return false;
        }
    }

    public boolean removeDoctorSchedule(int sid) {
        try {
            String query = "DELETE FROM doctor_schedule WHERE scheduleID = " + sid;
            return singleConnection.ExecuteSQL(query);
        } catch (Exception e) {
            System.out.println("Error in deleting doctor schedule: " + e.getMessage());
            return false;
        }
    }
}
