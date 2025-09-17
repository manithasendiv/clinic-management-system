package Models;

import java.io.File;

public class Service {
    private int serviceID;
    private String serviceName;
    private String doctor;
    private String checkedTime;
    private String specialNotes;
    private String file;
    private int fileID;
    private int patientID;

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public int getFileID() {
        return fileID;
    }

    public Service(int fileID, String file) {
        this.fileID = fileID;
        this.file = file;
    }

    public Service(String specialNotes, int serviceID) {
        this.specialNotes = specialNotes;
        this.serviceID = serviceID;
    }

    public Service(int serviceID, String serviceName, String doctor, String checkedTime) {
        this.serviceID = serviceID;
        this.serviceName = serviceName;
        this.doctor = doctor;
        this.checkedTime = checkedTime;
    }

    public int getPatientID() {
        return patientID;
    }

    public Service(String doctor, String serviceName, int patientID) {
        this.doctor = doctor;
        this.serviceName = serviceName;
        this.patientID = patientID;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }


    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getCheckedTime() {
        return checkedTime;
    }

    public void setCheckedTime(String checkedTime) {
        this.checkedTime = checkedTime;
    }

    public String getSpecialNotes() {
        return specialNotes;
    }

    public void setSpecialNotes(String specialNotes) {
        this.specialNotes = specialNotes;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
