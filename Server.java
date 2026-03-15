import java.io.*;
import java.net.*;
import java.sql.*;

public class Server {

    public static void main(String[] args) {

        try {

            ServerSocket server = new ServerSocket(5500);
            System.out.println("Server Started... Waiting for client");

            Socket socket = server.accept();
            System.out.println("Client Connected");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/student",
                    "root",
                    ""
            );

            Statement st = conn.createStatement();

            while(true){

                String request = in.readLine();

                if(request == null){
                    break;
                }

                ResultSet rs;

                if(request.equalsIgnoreCase("TOTAL")){
                    rs = st.executeQuery("SELECT * FROM students");
                }

                else if(request.equalsIgnoreCase("FEMALE")){
                    rs = st.executeQuery(
                    "SELECT * FROM students WHERE gender='Female'");
                }

                else if(request.equalsIgnoreCase("MALE")){
                    rs = st.executeQuery(
                    "SELECT * FROM students WHERE gender='Male'");
                }

                else if(request.equalsIgnoreCase("ABOVE20")){
                    rs = st.executeQuery(
                    "SELECT * FROM students WHERE age > 20");
                }

                else if(request.equalsIgnoreCase("BELOW20")){
                    rs = st.executeQuery(
                    "SELECT * FROM students WHERE age < 20");
                }

                else{
                    out.println("Invalid Request");
                    out.println("END");
                    continue;
                }

                while(rs.next()){

                    String student =
                            rs.getInt("id") + " | " +
                            rs.getString("name") + " | " +
                            rs.getString("gender") + " | " +
                            rs.getInt("age") + " | " +
                            rs.getString("department");

                    out.println(student);
                }

                out.println("END");
            }

            socket.close();
            conn.close();
            server.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}