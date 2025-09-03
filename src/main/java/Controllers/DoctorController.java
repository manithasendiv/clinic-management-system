package Controllers;


import Models.Doctor;
import ServiceLayer.DoctorService;

public class DoctorController {
    Doctor ObjDoctor;
    DoctorService ObjDoctorService;

    public DoctorController() {
        ObjDoctorService = new DoctorService();
    }

    public Doctor addDoctor(int doctorId, String name, String specialization, String email, String phoneNumber) {
        ObjDoctor = new Doctor(doctorId, name, specialization, email, phoneNumber);
        return ObjDoctor;
    }

    public boolean addDoctorToDataBase() {
        return ObjDoctorService.addDoctor(ObjDoctor);
    }
}
