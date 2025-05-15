package org.example;

import java.sql.*;
import java.util.Map;

public class DBOperations {


     //Krijon nje tabele ne databaze me emrin dhe kolonat e specifikuara

    public static void createTable(String tableName, Map<String, String> columns) throws SQLException {
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
        sb.append(tableName).append(" (");
        for (Map.Entry<String, String> entry : columns.entrySet()) {
            sb.append(entry.getKey()).append(" ").append(entry.getValue()).append(", ");
        }

        sb.setLength(sb.length() - 2);
        sb.append(");");

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            // Ekzekuton krijimin e tabeles
            stmt.execute(sb.toString());
            System.out.println("Table " + tableName + " created or already exists.");
        }
    }


    public static void dropTable(String tableName) throws SQLException {
        String sql = "DROP TABLE IF EXISTS " + tableName + " CASCADE;";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            // Ekzekuton fshirjen e tabeles
            stmt.execute(sql);
            System.out.println("Table " + tableName + " dropped.");
        }
    }


     // Kontrollon nese ekziston nje email i dhene ne tabelen student_test per te shmangur futjen e emaileve te njejta.


    public static boolean existsEmail(String email) throws SQLException {
        String sql = "SELECT 1 FROM student_test WHERE email = ? LIMIT 1;";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                // Nese ka te dhena, dmth emaili ekziston
                return rs.next();
            }
        }
    }


     //Fut nje student te ri ne database nese emaili nuk ekziston ende.

    public static void insertStudent(String emri, String mbiemri, String email) throws SQLException {
        // Kontrollo nese emaili ekziston perpara se te futet studenti
        if (existsEmail(email)) {
            System.out.println("Emaili '" + email + "' ekziston.. Studentet duhet te kene emaili unik.");
            return;
        }

        String sql = "INSERT INTO student_test (emri, mbiemri, email) VALUES (?, ?, ?);";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Vendos vlerat ne query
            pstmt.setString(1, emri);
            pstmt.setString(2, mbiemri);
            pstmt.setString(3, email);
            // Ekzekuto futjen
            pstmt.executeUpdate();
            System.out.println("Studenti u shtua me sukses: " + emri + " " + mbiemri);
        }
    }

 static void getStudentById(int id) throws SQLException {
        String sql = "SELECT * FROM student_test WHERE id = ?;";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Student: " +
                            rs.getInt("id") + " | " +
                            rs.getString("emri") + " | " +
                            rs.getString("mbiemri") + " | " +
                            rs.getString("email"));
                } else {
                    System.out.println("Nuk u gjet studenti me id " + id);
                }
            }
        }
    }


     //Updateon te dhenat e nje studenti duke kontrolluar qe emaili i ri te jete unik.

    public static void updateStudent(int id, String emri, String mbiemri, String email) throws SQLException {
        // Kontrollo nese emaili i ri ekziston per nje student tjeter (perveç id-se aktuale)
        String checkSql = "SELECT id FROM student_test WHERE email = ? AND id <> ?;";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setString(1, email);
            checkStmt.setInt(2, id);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Emaili '" + email + "' ekziston. Nuk mund ta perdorni kete email.");
                    return;
                }
            }

            // Nese emaili nuk ekziston per ndonje student tjeter, bej update
            String sql = "UPDATE student_test SET emri = ?, mbiemri = ?, email = ? WHERE id = ?;";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, emri);
                pstmt.setString(2, mbiemri);
                pstmt.setString(3, email);
                pstmt.setInt(4, id);
                int rows = pstmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("Studenti me id " + id + " u update-ua.");
                } else {
                    System.out.println("Nuk u gjet studenti me id " + id);
                }
            }
        }
    }


     // Fshin nje student nga database sipas id-se se tij.

    public static void deleteStudent(int id) throws SQLException {
        String sql = "DELETE FROM student_test WHERE id = ?;";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Studenti me id " + id + " u fshi.");
            } else {
                System.out.println("Nuk u gjet studenti me id " + id);
            }
        }
    }


     // Merr dhe printon listen e te gjithe studenteve te regjistruar ne database.

    public static void getAllStudents() throws SQLException {
        String sql = "SELECT * FROM student_test ORDER BY id;";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("Lista e studenteve:");
            boolean kaStudent = false;
            while (rs.next()) {
                kaStudent = true;
                System.out.println(rs.getInt("id") + " | " +
                        rs.getString("emri") + " | " +
                        rs.getString("mbiemri") + " | " +
                        rs.getString("email"));
            }
            if (!kaStudent) {
                System.out.println("Nuk ka student.");
            }
        }
    }


     //Merr dhe printon te dhenat e kurseve dhe notave per nje student te caktuar.

    public static void getStudentCourses(int studentId) throws SQLException {
        String sql = "SELECT s.emri, s.mbiemri, k.emri_kursit, sk.nota " +
                "FROM student_test s " +
                "JOIN student_kurs sk ON s.id = sk.student_id " +
                "JOIN kurs k ON sk.kurs_id = k.id " +
                "WHERE s.id = ?;";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (!rs.isBeforeFirst()) {
                    System.out.println("Nuk u gjet asnje kurs per studentin me ID " + studentId);
                    return;
                }

                String emri = null;
                String mbiemri = null;
                System.out.println("Kurset dhe notat per studentin me ID " + studentId + ":");
                while (rs.next()) {
                    if (emri == null) {
                        emri = rs.getString("emri");
                        mbiemri = rs.getString("mbiemri");
                        System.out.println("Studenti: " + emri + " " + mbiemri);
                    }
                    System.out.println(" - Kursi: " + rs.getString("emri_kursit") + ", Nota: " + rs.getDouble("nota"));
                }
            }
        }
    }



}
