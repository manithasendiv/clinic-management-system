package Models;

public class DoctorSchedule {
    private int scheduleId;
    private Doctor doctor;
    private String day;
    private String time;

    public DoctorSchedule(int scheduleId, Doctor doctor, String day, String time) {
        this.scheduleId = scheduleId;
        this.doctor = doctor;
        this.day = day;
        this.time = time;
    }

    public int getScheduleId() {
        return scheduleId;
    }
    public Doctor getDoctor() {
        return doctor;
    }
    public String getDay() {
        return day;
    }
    public String getTime() {
        return time;
    }
}
