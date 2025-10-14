package ServiceLayer;

import DatabaseLayer.DatabaseConnection;
import Models.Patient;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ReportService {

    private DatabaseConnection db = DatabaseConnection.getSingleInstance();

    public HashMap<String, Object> getBasicStats(String selectedMonth) {
        HashMap<String, Object> stats = new HashMap<>();
        try {
            String monthFilter = "";
            if (!selectedMonth.equals("All")) {
                Month monthEnum = Month.valueOf(selectedMonth.toUpperCase());
                int monthNumber = monthEnum.getValue();
                monthFilter = " WHERE MONTH(createdAt) = " + monthNumber;
            }

            // Total patients
            ResultSet rs = db.executeSelectQuery("SELECT COUNT(*) AS total FROM patient" + monthFilter);
            if (rs.next()) stats.put("totalPatients", rs.getInt("total"));

            // Average age
            rs = db.executeSelectQuery("SELECT AVG(Age) AS avgAge FROM patient" + monthFilter);
            if (rs.next()) stats.put("averageAge", rs.getDouble("avgAge"));

            // Top doctor
            rs = db.executeSelectQuery("SELECT SUBSTRING_INDEX(Selected_Doctor, ' - ', 1) AS doctor, COUNT(*) AS count " +
                    "FROM patient" + monthFilter + " GROUP BY doctor ORDER BY count DESC LIMIT 1");
            if (rs.next()) stats.put("topDoctor", rs.getString("doctor"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return stats;
    }

    // --- Monthly Patient Count by Month / Doctor ---
    public HashMap<String, Integer> getMonthlyPatientCountByDoctor(String selectedMonth) {
        HashMap<String, Integer> monthly = new HashMap<>();
        try {
            String sql;
            if(selectedMonth.equals("All")) {
                sql = "SELECT SUBSTRING_INDEX(Selected_Doctor, ' - ', 1) AS doctor, " +
                        "DATE_FORMAT(createdAt, '%Y-%m') AS month, COUNT(*) AS count " +
                        "FROM patient " +
                        "GROUP BY doctor, month " +
                        "ORDER BY month, doctor";
            } else {
                Month monthEnum = Month.valueOf(selectedMonth.toUpperCase());
                int monthNumber = monthEnum.getValue();

                sql = "SELECT SUBSTRING_INDEX(Selected_Doctor, ' - ', 1) AS doctor, " +
                        "COUNT(*) AS count " +
                        "FROM patient " +
                        "WHERE MONTH(createdAt) = " + monthNumber +
                        " GROUP BY doctor ORDER BY doctor";
            }

            ResultSet rs = db.executeSelectQuery(sql);
            while(rs.next()) {
                String monthLabel;
                if(selectedMonth.equals("All")) {
                    String monthStr = rs.getString("month"); // YYYY-MM
                    int m = Integer.parseInt(monthStr.split("-")[1]);
                    monthLabel = Month.of(m).name(); // Convert month number to name
                } else {
                    monthLabel = selectedMonth; // Specific month
                }

                String doctor = rs.getString("doctor");
                String key = monthLabel + " - " + doctor;
                monthly.put(key, rs.getInt("count"));
            }

        } catch(Exception e) { e.printStackTrace(); }
        return monthly;
    }


    // --- Patients per Doctor (Filtered by Month) ---
    public HashMap<String, Integer> getPatientsPerDoctorByMonth(String selectedMonth) {
        HashMap<String, Integer> doctorCounts = new HashMap<>();
        try {
            String sql;
            if(selectedMonth.equals("All")) {
                sql = "SELECT SUBSTRING_INDEX(Selected_Doctor, ' - ', 1) AS doctor, COUNT(*) AS count " +
                        "FROM patient GROUP BY doctor ORDER BY doctor";
            } else {
                Month monthEnum = Month.valueOf(selectedMonth.toUpperCase());
                int monthNumber = monthEnum.getValue();
                sql = "SELECT SUBSTRING_INDEX(Selected_Doctor, ' - ', 1) AS doctor, COUNT(*) AS count " +
                        "FROM patient WHERE MONTH(createdAt) = " + monthNumber +
                        " GROUP BY doctor ORDER BY doctor";
            }

            ResultSet rs = db.executeSelectQuery(sql);
            while(rs.next()) {
                doctorCounts.put(rs.getString("doctor"), rs.getInt("count"));
            }

        } catch(Exception e) { e.printStackTrace(); }
        return doctorCounts;
    }


    // --- Gender Distribution (Filtered by Month) ---
    public HashMap<String, Integer> getGenderDistribution(String selectedMonth) {
        HashMap<String, Integer> gender = new HashMap<>();
        try {
            String monthFilter = "";
            if(!selectedMonth.equals("All")) {
                Month monthEnum = Month.valueOf(selectedMonth.toUpperCase());
                int monthNumber = monthEnum.getValue();
                monthFilter = " WHERE MONTH(createdAt) = " + monthNumber;
            }

            ResultSet rs = db.executeSelectQuery("SELECT Gender, COUNT(*) AS count FROM patient" + monthFilter + " GROUP BY Gender");
            while(rs.next()) gender.put(rs.getString("Gender"), rs.getInt("count"));
        } catch (Exception e) { e.printStackTrace(); }
        return gender;
    }

    // --- Age Distribution (Filtered by Month) ---
    public HashMap<String, Integer> getAgeDistribution(String selectedMonth) {
        HashMap<String, Integer> age = new HashMap<>();
        try {
            String monthFilter = "";
            if(!selectedMonth.equals("All")) {
                Month monthEnum = Month.valueOf(selectedMonth.toUpperCase());
                int monthNumber = monthEnum.getValue();
                monthFilter = " WHERE MONTH(createdAt) = " + monthNumber;
            }

            String sql = "SELECT CASE " +
                    "WHEN Age BETWEEN 0 AND 20 THEN '0-20' " +
                    "WHEN Age BETWEEN 21 AND 40 THEN '21-40' " +
                    "WHEN Age BETWEEN 41 AND 60 THEN '41-60' " +
                    "ELSE '61+' END AS AgeRange, COUNT(*) AS count " +
                    "FROM patient" + monthFilter + " GROUP BY AgeRange";

            ResultSet rs = db.executeSelectQuery(sql);
            while(rs.next()) age.put(rs.getString("AgeRange"), rs.getInt("count"));

        } catch (Exception e) { e.printStackTrace(); }
        return age;
    }





//    public DefaultPieDataset<String> buildDoctorDataset(List<Patient> patients) {
//        Map<String, Integer> doctorCount = new HashMap<>();
//        for (Patient p : patients) {
//            doctorCount.put(p.getSelected_Doctor(), doctorCount.getOrDefault(p.getSelected_Doctor(), 0) + 1);
//        }
//
//        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
//        doctorCount.forEach(dataset::setValue);
//        return dataset;
//    }
//
//    public DefaultCategoryDataset buildAgeDataset(List<Patient> patients) {
//        Map<String, Integer> ageGroups = new TreeMap<>();
//        for (Patient p : patients) {
//            int ageRange = (p.getAge() / 10) * 10;
//            String label = ageRange + "-" + (ageRange + 9);
//            ageGroups.put(label, ageGroups.getOrDefault(label, 0) + 1);
//        }
//
//        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        ageGroups.forEach((range, count) ->
//                dataset.addValue(count, "Patients", range));
//        return dataset;
//    }
//
//    public DefaultCategoryDataset buildTimeDataset(List<Patient> patients) {
//        Map<String, Integer> monthCount = new TreeMap<>();
//        for (Patient p : patients) {
//            LocalDate date = LocalDate.now(); // 🔹 use createdAt if stored in Patient
//            String month = date.getMonth().toString() + " " + date.getYear();
//            monthCount.put(month, monthCount.getOrDefault(month, 0) + 1);
//        }
//
//        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        monthCount.forEach((month, count) ->
//                dataset.addValue(count, "Patients", month));
//        return dataset;
//    }
}
