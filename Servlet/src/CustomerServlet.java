import com.mysql.jdbc.Driver;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.io.IOException;
import java.sql.*;


@WebServlet(urlPatterns = "/cus")
public class CustomerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "Ijse@123");
            PreparedStatement pstm = connection.prepareStatement("select * from customer");
            ResultSet resultSet = pstm.executeQuery();

            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

            while (resultSet.next()){
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");

                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("id",id);
                objectBuilder.add("name",name);
                objectBuilder.add("address",address);

                arrayBuilder.add(objectBuilder);
            }

            resp.setContentType("application/json");
            resp.getWriter().write(arrayBuilder.build().toString());

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Error>>>>>"+e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String address = req.getParameter("address");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "Ijse@123");
            PreparedStatement pstm = connection.prepareStatement("insert into customer(id,name,address) values(?,?,?)");
            pstm.setString(1,id);
            pstm.setString(2,name);
            pstm.setString(3,address);

            boolean isExecute = pstm.executeUpdate() > 0;
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("id",id);
            objectBuilder.add("name",name);
            objectBuilder.add("address",address);

            if (isExecute){
                objectBuilder.add("status","success");
            }else {
                objectBuilder.add("status","fail");
            }

            resp.setContentType("application/json");
            resp.getWriter().write(objectBuilder.build().toString());

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Error>>>>>"+e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "Ijse@123");
            PreparedStatement pstm = connection.prepareStatement("delete from customer where id=?");
            pstm.setString(1,id);

            boolean isExecute = pstm.executeUpdate() > 0;
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("id",id);

            if (isExecute){
                objectBuilder.add("status","success");
            }else {
                objectBuilder.add("status","fail");
            }

            resp.setContentType("application/json");
            resp.getWriter().write(objectBuilder.build().toString());

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Error>>>>>"+e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String address = req.getParameter("address");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "Ijse@123");
            PreparedStatement pstm = connection.prepareStatement("update customer set name=?,address=? where id=?");
            pstm.setString(1,name);
            pstm.setString(2,address);
            pstm.setString(3,id);

            boolean isExecute = pstm.executeUpdate()>0;
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("id",id);
            objectBuilder.add("name",name);
            objectBuilder.add("address",address);

            if (isExecute){
                objectBuilder.add("status","success");
            }else {
                objectBuilder.add("status","fail");
            }

            resp.setContentType("application/json");
            resp.getWriter().write(objectBuilder.build().toString());
        } catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
            System.out.println("Error>>>>>"+e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
