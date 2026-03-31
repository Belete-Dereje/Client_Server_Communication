import java.net.Socket;
import java.io.*;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        String serverIP = "localhost";
        int port = 12345;

        try {
            // connect to server
            Socket socket = new Socket(serverIP, port);
            System.out.println("Connected to server!");

            // input/output
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\n1. All Students");
                System.out.println("2. By Gender");
                System.out.println("3. By Grade");
                System.out.println("4. Above Age");
                System.out.println("5. Stats");
                System.out.println("6. Exit");

                System.out.print("Enter choice: ");
                String choice = scanner.nextLine();

                String request = "";

                if (choice.equals("1")) {
                    request = "GET_ALL_STUDENTS";

                } else if (choice.equals("2")) {
                    System.out.print("Enter gender: ");
                    String gender = scanner.nextLine();
                    request = "GET_STUDENTS_BY_GENDER:" + gender;

                } else if (choice.equals("3")) {
                    System.out.print("Enter grade: ");
                    String grade = scanner.nextLine();
                    request = "GET_STUDENTS_BY_GRADE:" + grade;

                } else if (choice.equals("4")) {
                    System.out.print("Enter age: ");
                    String age = scanner.nextLine();
                    request = "GET_STUDENTS_ABOVE_AGE:" + age;

                } else if (choice.equals("5")) {
                    request = "GET_STATS";

                } else if (choice.equals("6")) {
                    request = "EXIT";
                } else {
                    System.out.println("Invalid choice!");
                    continue;
                }

                out.println(request);

                if (request.equals("EXIT")) {
                    System.out.println(in.readLine());
                    break;
                }

                String response;
                while ((response = in.readLine()) != null) {
                    if (response.equals("END_OF_RESPONSE")) {
                        break;
                    }
                    System.out.println(response);
                }
            }

            // close everything
            socket.close();
            scanner.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}