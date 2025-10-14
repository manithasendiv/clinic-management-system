package Controllers;

import Models.Patient;
import ServiceLayer.PatientService;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.Date;

public class PatientController {
    Patient ObjPatient;
    PatientService ObjPatientService;

    public PatientController() {
        ObjPatientService = new PatientService();
    }

    public Patient addPatient(int patientID, String Name, int Age, String Address, int Contact, String Email,String Selected_Doctor) {
        ObjPatient = new Patient(patientID, Name, Age, Address, Contact,Email,Selected_Doctor);
        return ObjPatient;
    }

    public boolean addPatientToDataBase() {
        return ObjPatientService.addPatient(ObjPatient);
    }

    public boolean updatePatient(){
        return ObjPatientService.updatePatient(ObjPatient);
    }
    public ResultSet getAllPatients(){
        return ObjPatientService.getAllPatients();
    }

    public boolean deletePatient(int patientID){
        return ObjPatientService.deletePatient(patientID);
    }
}
