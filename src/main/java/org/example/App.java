package org.example;

import org.apache.commons.lang3.StringUtils;

import java.sql.*;

public class App {

    public static void main(String[] args) {
        // Lidhja me DataBazen
        String url = "jdbc:postgresql://localhost:5432/JavaIntern";
        String user = "postgres";
        String password = "2";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            Statement stat = connection.createStatement();

            // Printimi i listes se tabelave.
            ResultSet rs = stat.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema = 'public'");
            System.out.println("Tabelat e JavaIntern:");
            while (rs.next()) {
                System.out.println("- " + rs.getString("table_name"));
            }
            rs.close();
            System.out.println();

            // Printimi i te gjitha rrreshtave te tabeles student
            System.out.println("Studentet:");
            ResultSet rs1 = stat.executeQuery("SELECT * FROM student");
            while (rs1.next()) {
                System.out.println(rs1.getString("emri") + " - " + rs1.getString("email") + " - " + rs1.getString("birth_date") + " - " + rs1.getString("phone") + " - " + rs1.getString("pike") + " - " + rs1.getString("student_key"));
            }
            rs1.close();

            // Shtimi i nje studenti duke perdorur PreparedStatement
            /*
            String insertSQL = "INSERT INTO student (emri, email, birth_date, phone, pike) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(insertSQL)) {
                ps.setString(1, "Anna");
                ps.setString(2, "anna@example.com");
                ps.setDate(3, Date.valueOf("2000-05-10"));
                ps.setString(4, "0681234567");
                ps.setDouble(5, 98.5);


                int rowsInserted = ps.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Studenti u shtua me sukses!");
                }
            } catch (SQLException e) {
                System.out.println("Gabim: " + e.getMessage());
            }
*/



            //Shtimi i nje studenti duke perdorur commons-lang3

                   String rawName = "  MaRiA  ";
            String rawEmail = "  MaRiA@ExAmPle.com  ";
            String phone = "0681234567";
            Date birthDate = Date.valueOf("2000-05-10");
            double pike = 98.5;


            if (StringUtils.isBlank(rawName) || StringUtils.isBlank(rawEmail)) {
                System.out.println("Emri dhe emaili nuk mund te jene empty.");
            } else {

                String formattedName = StringUtils.capitalize(rawName.trim().toLowerCase());
                String formattedEmail = StringUtils.lowerCase(rawEmail.trim());

                String insertSQL = "INSERT INTO student (emri, email, birth_date, phone, pike) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement ps = connection.prepareStatement(insertSQL)) {
                    ps.setString(1, formattedName);
                    ps.setString(2, formattedEmail);
                    ps.setDate(3, birthDate);
                    ps.setString(4, phone);
                    ps.setDouble(5, pike);

                    int rowsInserted = ps.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("Studenti u shtua me sukses.");
                    }
                } catch (SQLException e) {
                    System.out.println("Gabim gjate shtimit: " + e.getMessage());
                }
            }


            // Modifikimi i pikeve
            String updateStudentSQL = "UPDATE student SET pike = ? WHERE email = ?";
            try (PreparedStatement psUpdate = connection.prepareStatement(updateStudentSQL)) {
                psUpdate.setDouble(1, 95.0);
                psUpdate.setString(2, "morush@gmail.com");

                int rowsUpdated = psUpdate.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Piket u modifikuan.");
                } else {
                    System.out.println("Nuk u modifikuan piket.");
                }
            }

            // Fshirja e studentit
            String deleteStudentSQL = "DELETE FROM student WHERE email = ?";
            try (PreparedStatement psDelete = connection.prepareStatement(deleteStudentSQL)) {
                psDelete.setString(1, "daniel@gmail.com");

                int rowsDeleted = psDelete.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Studenti u fshi.");
                } else {
                    System.out.println("Nuk u gjet student me kete email.");
                }
            }

            //  Tabela 'student' pas ndryshimeve (modifikimit dhe fshirjes)

            System.out.println("\nStudentet pas modifikimit dhe fshirjes:");
            ResultSet rs2 = stat.executeQuery("SELECT * FROM student");
            while (rs2.next()) {
                System.out.println(rs2.getString("emri") + " - " + rs2.getString("email") + " - " + rs2.getString("birth_date") + " - " + rs2.getString("phone") + " - " + rs2.getString("pike") + " - " + rs2.getString("student_key"));
            }
            rs2.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
