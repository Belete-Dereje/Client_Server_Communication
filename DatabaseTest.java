import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseTest {
  public static void main(String[] args){
    try{
      Connection conn=DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/student","root","");
        System.out.println("Dtabase Connected Successfully");
    }catch(Exception e){
        System.out.println(e);
    }

  }
  
}
