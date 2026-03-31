import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestMySQLConnection {
    public static void main(String[] args) {
        // Connection details for your database
        String url = "jdbc:mysql://localhost:3306/Student?useSSL=false&serverTimezone=UTC";
        String username = "root";
        String password = "";   // Default XAMPP password is empty

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("✅ Successfully connected to XAMPP MySQL Database: 'student'");

            // Query the students table
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM students");

            System.out.println("\n📋 Data from 'students' table:");
            System.out.println("---------------------------------------------------");

            boolean hasData = false;
            while (resultSet.next()) {
                hasData = true;
                System.out.println("ID       : " + resultSet.getInt("id"));
                System.out.println("Name     : " + resultSet.getString("name"));
                System.out.println("Email    : " + resultSet.getString("email"));
                System.out.println("---------------------------------------------------");
            }

            if (!hasData) {
                System.out.println("The 'students' table is empty.");
            }

            System.out.println("\n🎉 Test completed successfully!");

        } catch (Exception e) {
            System.out.println("❌ Error occurred!");
            e.printStackTrace();
        } finally {
            // Clean up
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
                System.out.println("🔒 Connection closed.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}