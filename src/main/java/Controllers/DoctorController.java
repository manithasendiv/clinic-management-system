package Controllers;


import Models.Doctor;
import ServiceLayer.DoctorService;

import java.sql.ResultSet;

public class DoctorController {
    Doctor ObjDoctor;
    DoctorService ObjDoctorService;

    public DoctorController() {
        ObjDoctorService = new DoctorService();
    }

    public Doctor addDoctor(String doctorId, String name, String specialization, String email, String phoneNumber) {
        ObjDoctor = new Doctor(doctorId, name, specialization, email, phoneNumber);
        return ObjDoctor;
    }

    public boolean addDoctorToDataBase() {
        return ObjDoctorService.addDoctor(ObjDoctor);
    }

    public boolean updateDoctor(Doctor doctor) {
        return ObjDoctorService.updateDoctor(doctor);
    }

    public ResultSet getAllDoctors() {
        return ObjDoctorService.getAllDoctors();
    }
}
