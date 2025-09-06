package Models;

import java.io.File;

public class Service {
    private int serviceID;
    private String serviceName;
    private Doctor doctor;
    private String checkedTime;
    private String specialNotes;
    private File file;


    public Service(String specialNotes, int serviceID) {
        this.specialNotes = specialNotes;
        this.serviceID = serviceID;
    }

    public Service(int serviceID, String serviceName, Doctor doctor, String checkedTime, String specialNotes, File file) {
        this.serviceID = serviceID;
        this.serviceName = serviceName;
        this.doctor = doctor;
        this.checkedTime = checkedTime;
        this.specialNotes = specialNotes;
        this.file = file;
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


    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
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

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
