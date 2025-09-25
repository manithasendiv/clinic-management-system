package Controllers;

import Models.Doctor;
import Models.DoctorSchedule;
import ServiceLayer.DoctorScheduleService;

import javax.xml.transform.Result;
import java.sql.ResultSet;

public class DoctorScheduleController {
    public DoctorSchedule ObjDoctorSchedule;
    DoctorScheduleService ObjDoctorScheduleService;

    public DoctorScheduleController(){
        ObjDoctorScheduleService = new DoctorScheduleService();
    }

    public void addDoctorSchedule(int scheduleID, Doctor doctor, String day, String time) {
        ObjDoctorSchedule = new DoctorSchedule(scheduleID, doctor, day, time);
    }

    public boolean addDoctorScheduleToDataBase() {
        return ObjDoctorScheduleService.addDoctorSchedule(ObjDoctorSchedule);
    }

    public boolean updateDoctorSchedule(String scheduleID, String docName, String date, String time) {
        return ObjDoctorScheduleService.updateDoctorSchedule(scheduleID, docName, date , time);
    }

    public boolean removeDoctorSchedule(String sid) {
        return ObjDoctorScheduleService.removeDoctorSchedule(sid);
    }

    public ResultSet getAllSchedule() {
        return ObjDoctorScheduleService.getAllSchedule();
    }


}
