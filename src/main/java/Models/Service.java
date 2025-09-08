package Models;

import java.io.File;

public class Service {
    private int serviceID;
    private String serviceName;
    private String doctor;
    private String checkedTime;
    private String specialNotes;
    private File file;

    public Service(File file, int serviceID) {
        this.file = file;
        this.serviceID = serviceID;
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

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
