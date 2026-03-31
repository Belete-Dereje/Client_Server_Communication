import java.net.*;
import java.io.*;
import java.sql.*;

public class Server {

    public static void main(String[] args) {

        int port = 12345;
        String url = "jdbc:mysql://localhost:3306/Student";
        String user = "root";
        String pass = "";

        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");

            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected");

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                Connection conn = DriverManager.getConnection(url, user, pass);

                String request;

                while ((request = in.readLine()) != null) {

                    // ==============================
                    // 1. ALL STUDENTS
                    // ==============================
                    if (request.equals("GET_ALL_STUDENTS")) {

                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery("SELECT * FROM students");

                        printHeader(out);

                        while (rs.next()) {
                            printRow(out, rs);
                        }

                    // ==============================
                    // 2. BY GENDER
                    // ==============================
                    } else if (request.startsWith("GET_STUDENTS_BY_GENDER:")) {

                        String gender = request.split(":")[1];

                        PreparedStatement ps = conn.prepareStatement(
                                "SELECT * FROM students WHERE gender=?");
                        ps.setString(1, gender);

                        ResultSet rs = ps.executeQuery();

                        printHeader(out);

                        while (rs.next()) {
                            printRow(out, rs);
                        }

                    // ==============================
                    // 3. BY GRADE
                    // ==============================
                    } else if (request.startsWith("GET_STUDENTS_BY_GRADE:")) {

                        String grade = request.split(":")[1];

                        PreparedStatement ps = conn.prepareStatement(
                                "SELECT * FROM students WHERE grade=?");
                        ps.setString(1, grade);

                        ResultSet rs = ps.executeQuery();

                        printHeader(out);

                        while (rs.next()) {
                            printRow(out, rs);
                        }

                    // ==============================
                    // 4. ABOVE AGE
                    // ==============================
                    } else if (request.startsWith("GET_STUDENTS_ABOVE_AGE:")) {

                        int age = Integer.parseInt(request.split(":")[1]);

                        PreparedStatement ps = conn.prepareStatement(
                                "SELECT * FROM students WHERE age > ?");
                        ps.setInt(1, age);

                        ResultSet rs = ps.executeQuery();

                        printHeader(out);

                        while (rs.next()) {
                            printRow(out, rs);
                        }

                    // ==============================
                    // 5. STATISTICS
                    // ==============================
                    } else if (request.equals("GET_STATS")) {

                        Statement stmt = conn.createStatement();

                        out.println("===== STATISTICS =====");

                        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM students");
                        if (rs.next()) out.println("Total Students : " + rs.getInt(1));

                        rs = stmt.executeQuery("SELECT COUNT(*) FROM students WHERE gender='male'");
                        if (rs.next()) out.println("Male Students  : " + rs.getInt(1));

                        rs = stmt.executeQuery("SELECT COUNT(*) FROM students WHERE gender='female'");
                        if (rs.next()) out.println("Female Students: " + rs.getInt(1));

                    // ==============================
                    // 6. EXIT
                    // ==============================
                    } else if (request.equals("EXIT")) {

                        out.println("Goodbye!");
                        break;

                    } else {
                        out.println("Unknown command");
                    }

                    out.println("END_OF_RESPONSE");
                }

                conn.close();
                socket.close();
                System.out.println("Client disconnected\n");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    public static void printHeader(PrintWriter out) {
        out.printf("%-5s %-20s %-10s %-5s %-5s%n",
                "ID", "Name", "Gender", "Age", "Grade");
        out.println("--------------------------------------------------------");
    }

    public static void printRow(PrintWriter out, ResultSet rs) throws SQLException {
        out.printf("%-5d %-20s %-10s %-5d %-5s%n",
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("gender"),
                rs.getInt("age"),
                rs.getString("grade"));
    }
}