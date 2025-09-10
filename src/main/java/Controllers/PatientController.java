package Controllers;

import Models.Patient;
import ServiceLayer.PatientService;

import java.sql.Time;
import java.util.Date;

public class PatientController {
    Patient ObjPatient;
    PatientService ObjPatientService;

    public PatientController() {
        ObjPatientService = new PatientService();
    }

    public Patient addPatient(int patientID, String Name, int Age, String Address, int Contact, String Email,String Selected_Doctor, String Date, String Time ) {
        ObjPatient = new Patient(patientID, Name, Age, Address, Contact,Email,Selected_Doctor,Date,Time);
        return ObjPatient;
    }

    public boolean addPatientToDataBase() {
        return ObjPatientService.addPatient(ObjPatient);
    }

    public boolean updatePatient(Patient patient){
        return ObjPatientService.updatePatient(patient);
    }
}
