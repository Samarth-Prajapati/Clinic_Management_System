/*
 * NAME : SAMARTH BHAVIN PRAJAPATI
 * BATCH 6
 * ENROLLMENT NUMBER : 22002171210137
 * ROLL NUMBER : 171
 * TOPIC : CLINIC MANAGEMENT SYSTEM
 */

import java.util.*;
import java.sql.*;

public class App {
    static Scanner sc = new Scanner(System.in);
    static final String Dr1 = "DR. DINESH MISTRY";
    static final String Dr2 = "DR. JASH MISTRY";
    static final String UserName = "ADMIN";
    static final String Password = "LJENG";
    static Statement st;

    // LOGIN

    static void Login() {
        boolean login = false;
        do {
            System.out.println("ENTER USER NAME : ");
            String userName = sc.nextLine();
            System.out.println("ENTER PASSWORD : ");
            String password = sc.nextLine();
            if (userName.equals(UserName) && password.equals(Password)) {
                System.out.println("WELCOME " + userName);
                login = true;
            } else {
                System.out.println("INVALID USERNAME OR PASSWORD, TRY AGAIN");
                login = false;
            }
        } while (login == false);
    }

    // ADD PATIENT USING QUERY

    static void AddPatient() throws Exception {
        boolean checkid = false;
        int patient_id;
        do {
            System.out.println("ENTER PATIENT ID : ");
            patient_id = sc.nextInt();
            if (patient_id < 0) {
                throw new InvalidID("Invalid ID, Try Again");
            } else {
                checkid = true;
            }
        } while (checkid == false);
        sc.nextLine();
        System.out.println("ENTER PATIENT NAME : ");
        String patient_name = sc.nextLine();
        boolean checkage = false;
        int patient_age;
        do {
            System.out.println("ENTER PATIENT AGE : ");
            patient_age = sc.nextInt();
            if (patient_age < 0) {
                throw new InvalidAge("Invalid Age, Try Again");
            } else {
                checkage = true;
            }
        } while (checkage == false);
        sc.nextLine();

        // VALIDATION FOR PHONE NUMBER

        String patient_contact;
        boolean call = false;
        do {
            System.out.println("ENTER PHONE NUMBER : ");
            patient_contact = sc.nextLine();
            if (patient_contact.length() == 10) {
                for (int i = 0; i < patient_contact.length(); i++) {
                    if (patient_contact.charAt(i) >= '0' && patient_contact.charAt(i) <= '9') {
                        call = true;
                    } else {
                        System.out.println("INVALID PHONE NUMBER");
                        call = false;
                    }
                }
            } else {
                System.out.println("TRY AGAIN");
            }
        } while (call == false);
        System.out.println("ENTER PATIENT ADDRESS : ");
        String patient_address = sc.nextLine();
        System.out.println("ENTER SYMPTOMS : ");
        String patient_symptoms = sc.nextLine();
        String finalDr = null;
        boolean drs = false;
        do {
            System.out.println("SELECT DOCTOR : \n1. DR. DINESH MISTRY\n2. DR.JASH MISTRY");
            int doctor = sc.nextInt();
            switch (doctor) {
                case 1:
                    finalDr = Dr1;
                    drs = true;
                    break;
                case 2:
                    finalDr = Dr2;
                    drs = true;
                    break;
                default:
                    System.out.println("INVALID CHOICE");
            }
        } while (drs == false);
        String sql = "INSERT INTO shreeji_clinic(PATIENT_ID,PATIENT_NAME,PATIENT_AGE,PATIENT_CONTACT,SYMPTOMS,DOCTOR_NAME) VALUES("
                + patient_id + "," + "'" + patient_name + "'" + "," + patient_age + "," + patient_contact + "," + "'"
                + patient_symptoms + "'" + "," + "'" + finalDr + "'" + ")";
        st.executeUpdate(sql);
        System.out.println(patient_name + " ADDED SUCCESSFULLY");
    }

    // UPDATING PATIENT DETAILS

    static void UpdatePatient() throws Exception {
        System.out.println("ENTER PATIENT NAME : ");
        sc.nextLine();
        String name = sc.nextLine();
        String sql = "select * from shreeji_clinic where PATIENT_NAME=" + "'" + name + "'";
        ResultSet rs = st.executeQuery(sql);
        if (!rs.isBeforeFirst()) {
            System.out.println("PATIENT NOT FOUND");
        } else {
            boolean up = false;
            do {
                System.out.println("1. AGE\n2. PHONE NUMBER\n3. SYMPTOMS");
                int choice4 = sc.nextInt();
                switch (choice4) {
                    case 1:
                        System.out.println("ENTER NEW AGE : ");
                        int age = sc.nextInt();
                        String sql1 = "UPDATE shreeji_clinic SET PATIENT_AGE=" + age + " WHERE PATIENT_NAME='" + name
                                + "'";
                        st.executeUpdate(sql1);
                        System.out.println("AGE UPDATED SUCCESSFULLY");
                        up = true;
                        break;
                    case 2:
                        String contact;
                        boolean call = false;
                        do {
                            System.out.println("ENTER NEW PHONE NUMBER : ");
                            contact = sc.next();
                            if (contact.length() == 10) {
                                for (int i = 0; i < contact.length(); i++) {
                                    if (contact.charAt(i) >= '0' && contact.charAt(i) <= '9') {
                                        call = true;
                                    } else {
                                        System.out.println("INVALID PHONE NUMBER");
                                        call = false;
                                    }
                                }
                            } else {
                                System.out.println("TRY AGAIN");
                            }
                        } while (call == false);
                        String sql2 = "UPDATE shreeji_clinic SET PATIENT_CONTACT=" + contact + " WHERE PATIENT_NAME='"
                                + name + "'";
                        st.executeUpdate(sql2);
                        System.out.println("PHONE NUMBER UPDATED SUCCESSFULLY");
                        up = true;
                        break;
                    case 3:
                        System.out.println("ENTER NEW SYMPTOM : ");
                        String symptom = sc.next();
                        String sql3 = "UPDATE shreeji_clinic SET SYMPTOMS='" + symptom + "'" + " WHERE PATIENT_NAME='"
                                + name + "'";
                        st.executeUpdate(sql3);
                        System.out.println("SYMPTOM UPDATED SUCCESSFULLY");
                        up = true;
                        break;
                    default:
                        System.out.println("INVALID INPUT, TRY AGAIN");
                }
            } while (up == false);
        }
    }

    // SEARCHING PATIENT DETAILS

    static void SearchPatient() throws Exception {
        System.out.println("ENTER PATIENT NAME : ");
        String name = sc.next();
        String sql = "select * from shreeji_clinic where PATIENT_NAME=" + "'" + name + "'";
        ResultSet rs = st.executeQuery(sql);
        if (!rs.isBeforeFirst()) {
            throw new PatientNotFoundException("PATIENT NOT FOUND.");
        } else {
            while (rs.next()) {
                System.out.println("PATIENT_ID : " + rs.getInt(1));
                System.out.println("PATIENT_NAME : " + rs.getString(2));
                System.out.println("PATIENT_AGE : " + rs.getInt(3));
                System.out.println("PATIENT_CONTACT : " + rs.getString(4));
                System.out.println("PATIENT_ADDRESS : " + rs.getString(5));
                System.out.println("SYMPTOMS : " + rs.getString(6));
                System.out.println("DOCTOR_NAME : " + rs.getString(7));
            }
        }
    }

    // DISPLAY ALL PATIENT DETAILS

    static void Display() throws Exception {
        String sql = "select * from shreeji_clinic";
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            System.out.println("PATIENT_ID : " + rs.getInt(1));
            System.out.println("PATIENT_NAME : " + rs.getString(2));
            System.out.println("PATIENT_AGE : " + rs.getInt(3));
            System.out.println("PATIENT_CONTACT : " + rs.getString(4));
            System.out.println("PATIENT_ADDRESS : " + rs.getString(5));
            System.out.println("SYMPTOMS : " + rs.getString(6));
            System.out.println("DOCTOR_NAME : " + rs.getString(7));
            System.out.println();
        }
    }

    public static void main(String[] args) throws Exception {

        // CONNETING TO DATABASE

        String dburl = "jdbc:mysql://localhost:3306/CLINIC_MANAGEMENT_SYSTEM";
        String dbuser = "root";
        String dbpass = "";
        String driver = "com.mysql.cj.jdbc.Driver";
        Class.forName(driver);
        Connection con = DriverManager.getConnection(dburl, dbuser, dbpass);
        if (con != null) {
            System.out.println("CONNECTION SUCCESSFUL");
        } else {
            System.out.println("CONNECTION FAILED");
        }
        st = con.createStatement();
        System.out.println(" -WELCOME TO SHREEJI CLINIC- ");
        Login();
        boolean maincode = false;
        do {
            System.out.println("1. DOCTOR\n2. PATIENT\n3. EXIT");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    boolean drr = false;
                    do {
                        System.out.println("1. DR. DINESH MISTRY\n2. DR. JASH MISTRY");
                        int choice2 = sc.nextInt();
                        switch (choice2) {
                            case 1:
                                System.out.println("PATIENTS ARE :");
                                String sql1 = "select PATIENT_ID,PATIENT_NAME from shreeji_clinic where DOCTOR_NAME='DR. DINESH MISTRY'";
                                ResultSet res = st.executeQuery(sql1);
                                while (res.next()) {
                                    System.out.println(res.getInt(1) + " " + res.getString(2));
                                }
                                drr = true;
                                break;
                            case 2:
                                System.out.println("PATIENTS ARE :");
                                String sql2 = "select PATIENT_ID,PATIENT_NAME from shreeji_clinic where DOCTOR_NAME='DR. JASH MISTRY'";
                                ResultSet res2 = st.executeQuery(sql2);
                                while (res2.next()) {
                                    System.out.println(res2.getInt(1) + " " + res2.getString(2));
                                }
                                drr = true;
                                break;
                            default:
                                System.out.println("INVALID INPUT, TRY AGAIN");
                        }
                    } while (drr == false);
                    break;
                case 2:
                    boolean r = false;
                    do {
                        System.out.println(
                                "1. ADD PATIENT DETAILS\n2. UPDATE PATIENT DETAILS\n3. SEARCH PATIENT DETAILS\n4. DISPLAY\n5. BACK");
                        int choice3 = sc.nextInt();
                        switch (choice3) {
                            case 1:
                                AddPatient();
                                break;
                            case 2:
                                UpdatePatient();
                                break;
                            case 3:
                                SearchPatient();
                                break;
                            case 4:
                                Display();
                                break;
                            case 5:
                                r = true;
                                break;
                            default:
                                System.out.println("INVALID INPUT, TRY AGAIN");
                        }
                    } while (r == false);
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("INVALID INPUT, TRY AGAIN");
            }
        } while (maincode == false);
    }
}

// EXCEPTION HANDLING

class PatientNotFoundException extends Exception {
    PatientNotFoundException(String patient) {
        super(patient);
    }
}

class InvalidID extends Exception {
    InvalidID(String id) {
        super(id);
    }
}

class InvalidAge extends Exception {
    InvalidAge(String age) {
        super(age);
    }
}
