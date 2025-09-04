package Controllers;

import Models.Patient;
import ServiceLayer.PatientService;

public class PatientController {
    Patient ObjPatient;
    PatientService ObjPatientService;

    public PatientController() {
        ObjPatientService = new PatientService();
    }

    public Patient addPatient(int patientID, String Name, int Age, String Address, int Contact, String Email ) {
        ObjPatient = new Patient(patientID, Name, Age, Address, Contact,Email);
        return ObjPatient;
    }

    public boolean addPatientToDataBase() {
        return ObjPatientService.addPatient(ObjPatient);
    }
}
