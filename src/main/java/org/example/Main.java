package org.example;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {

            Map<String, String> columns = new HashMap<>();
            columns.put("id", "SERIAL PRIMARY KEY");
            columns.put("emri", "VARCHAR(100) NOT NULL");
            columns.put("mbiemri", "VARCHAR(100) NOT NULL");
            columns.put("email", "VARCHAR(100) UNIQUE NOT NULL");
            DBOperations.createTable("student_test", columns);

            // Shtimi i studenteve nga input-i
            boolean vazhdo = true;
            while (vazhdo) {
                System.out.println("Jep emrin e studentit:");
                String emri = scanner.nextLine().trim();

                System.out.println("Jep mbiemrin e studentit:");
                String mbiemri = scanner.nextLine().trim();

                System.out.println("Jep emailin e studentit:");
                String email = scanner.nextLine().trim();

                if (emri.isEmpty() || mbiemri.isEmpty() || email.isEmpty()) {
                    System.out.println("Plotesoni te gjitha fushat");
                    continue;
                }

                DBOperations.insertStudent(emri, mbiemri, email);

                System.out.println("Doni te shtoni nje student tjeter? (po/jo)");
                String pergjigja = scanner.nextLine().trim().toLowerCase();
                if (!pergjigja.equals("po")) {
                    vazhdo = false;
                }
            }

            // Printo lista e studenteve
            System.out.println("\nLista e studenteve:");
            DBOperations.getAllStudents();


            System.out.println("\nJep ID-në e studentit qe deshironi te printoni:");
            int idGjetjes = Integer.parseInt(scanner.nextLine().trim());
            DBOperations.getStudentById(idGjetjes);


            System.out.println("\nJep ID e studentit qe doni te update-oni:");
            int idUpdate = Integer.parseInt(scanner.nextLine().trim());

            System.out.println("Jep emrin e ri te studentit:");
            String emriUpdate = scanner.nextLine().trim();

            System.out.println("Jep mbiemrin e ri te studentit:");
            String mbiemriUpdate = scanner.nextLine().trim();

            System.out.println("Jep emailin e ri te studentit:");
            String emailUpdate = scanner.nextLine().trim();

            if (emriUpdate.isEmpty() || mbiemriUpdate.isEmpty() || emailUpdate.isEmpty()) {
                System.out.println("Duhet te plotesohen te gjitha fushat per update.");
            } else {
                DBOperations.updateStudent(idUpdate, emriUpdate, mbiemriUpdate, emailUpdate);
                DBOperations.getStudentById(idUpdate);
            }


            System.out.println("\nJep ID e studentit qe deshironi te fshini:");
            int idDelete = Integer.parseInt(scanner.nextLine().trim());
            DBOperations.deleteStudent(idDelete);


            System.out.println("\nLista e studenteve pas fshirjes:");
            DBOperations.getAllStudents();


            System.out.println("\nJep ID e studentit per te printuar kurset dhe notat:");
            int idCourses = Integer.parseInt(scanner.nextLine().trim());
            DBOperations.getStudentCourses(idCourses);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection();
            scanner.close();
        }
    }
}
