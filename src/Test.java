import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
//import java.util.concurrent.locks.StampedLock;
import java.util.regex.*;

class student{
    int rollno;
    String name;
    int marks;
    public student(int r,String n,int m){
        rollno=r;
        name=n;
        marks=m;
    }
}
public class Test {
    public static void insert(ArrayList<student> arr1,Connection con1) throws SQLException {
        PreparedStatement s = null;
        Pattern p=Pattern.compile("j$");
        for (int k = 0; k < 3; k++) {
            Matcher m=p.matcher(arr1.get(k).name);
            try {
                if (m.find()) {
                    s = con1.prepareStatement("insert into student values (?,?,?)");
                    s.setInt(1, arr1.get(k).rollno);
                    s.setString(2, arr1.get(k).name);
                    s.setInt(3, arr1.get(k).marks);
                    s.executeUpdate();
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    public static void delete(Connection con) throws SQLException {
        Statement s=con.createStatement();
        int delete_from_student = s.executeUpdate("delete from student");
    }
    public static void show(Connection con) throws SQLException {
        Statement s=con.createStatement();
        ResultSet rs = s.executeQuery("select * from student");
        while (rs.next()) {
            System.out.println(rs.getInt(1));
            System.out.println(rs.getString(2));
            System.out.println(rs.getInt(3));
        }
    }
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ATest","root","")) {
            ArrayList<student> arr = new ArrayList<>();
            Scanner sc = new Scanner(System.in);
            student[] stu = new student[10];
            for (int i = 0; i < 3; i++) {
                System.out.print("enter rollno : ");
                int a = sc.nextInt();
                sc.nextLine();
                System.out.print("enter name : ");
                String b = sc.nextLine();
                System.out.print("enter marks : ");
                int c = sc.nextInt();
                stu[i] = new student(a, b, c);
            }
            for (int j = 0; j < 3; j++)
                arr.add(j, stu[j]);
            Test.insert(arr,con);
            Test.show(con);
            Test.delete(con);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
