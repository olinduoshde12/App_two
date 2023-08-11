
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(urlPatterns = "/Customer")
public class CustomerServlet extends HttpServlet {

    //GET -Query String
    //DELETE-Query String
    //POST-Query String(x-www-form-urlencoded)
    //put-query String

    //this method can use get All Customer Details
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            //the media type of the content of the Response
            resp.setContentType("application/json");
            //content type eka use karnne content architecture hadnna


            //met data from the response
            resp.addHeader("institute","IJSE");
            resp.addHeader("course","GDSE");
            //initialize the connection
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "1234");
            ResultSet rest = connection.prepareStatement("select * from Customer").executeQuery();
            String allRecords="";

            //access the data and generate json object
            while (rest.next()){
                String id=rest.getString(1);
                String name=rest.getString(2);
                String address=rest.getString(3);
                double salary=rest.getDouble(4);

                System.out.println(id+" "+name+" "+address+" "+salary);

                String customer="{\"id\":\""+id+"\",\"name\":\""+name+"\",\"address\":\""+address+"\",\"salary\":"+salary+"},";
                allRecords= allRecords + customer;

            }
            //last customer object should be removed
            String finalJson= "["+ allRecords.substring(0,allRecords.length()-1) +"]";

            //then print it is as response
            PrintWriter writer = resp.getWriter();
            writer.write(finalJson);//possible response type =>xml // json // html // text

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //This method can be use Add Customer
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id= req.getParameter("Customer_Id");
        String name=req.getParameter("Customer_Name");
        String address=req.getParameter("Customer_Address");
        String salary=req.getParameter("Customer_Salary");

        System.out.println(id+"  "+name+"  "+address+"  "+salary);


        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "1234");
            PreparedStatement pstm = connection.prepareStatement("insert into Customer values (?,?,?,?)");
            pstm.setObject(1,id);
            pstm.setObject(2,name);
            pstm.setObject(3,address);
            pstm.setObject(4,salary);

            Boolean a=pstm.executeUpdate()> 0;
            PrintWriter writer = resp.getWriter();
            if(a){
                writer.write("Customer is Added...");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    //this method can be use Delete Customer
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


       String id= req.getParameter("Cust_id");

        System.out.println(id);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "1234");
            PreparedStatement statement = connection.prepareStatement("delete from Customer where id=?");
            statement.setObject(1,id);

            boolean conform=statement.executeUpdate()>0;
            PrintWriter writer = resp.getWriter();
            if(conform){
                writer.write("Customer Deleted...");
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
//This method can use Update Customer
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id=req.getParameter("Customer_Id");
        String name=req.getParameter("Customer_Name");
        String address=req.getParameter("Customer_Address");
        String salary=req.getParameter("Customer_Salary");


        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "1234");
             PreparedStatement statement = connection.prepareStatement("update Customer set name=?,address=?,salary=? where id=?");
            statement.setObject(1,name);
            statement.setObject(2,address);
            statement.setObject(3, salary);
            statement.setObject(4, id);

            boolean b = statement.executeUpdate() > 0;
            PrintWriter writer = resp.getWriter();
            if(b){
                writer.write("Customer has Been Updated...!!");
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
