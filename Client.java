import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) {

        try{

            Socket socket = new Socket("localhost",5500);

            BufferedReader keyboard = new BufferedReader(
                    new InputStreamReader(System.in));

            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            while(true){

                System.out.println("\nEnter Request:");
                System.out.println("TOTAL | FEMALE | MALE | ABOVE20 | BELOW20 | EXIT");

                String request = keyboard.readLine();

                if(request.equalsIgnoreCase("EXIT")){
                    System.out.println("Client Closed.");
                    break;
                }

                out.println(request);

                String response;

                System.out.println("\n----- Result -----");

                while(!(response = in.readLine()).equals("END")){
                    System.out.println(response);
                }

                System.out.println("------------------");
            }

            socket.close();

        }catch(Exception e){
            System.out.println(e);
        }
    }
}