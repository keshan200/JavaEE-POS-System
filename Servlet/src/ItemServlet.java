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

@WebServlet(urlPatterns = "/item")
public class ItemServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "Ijse@123");
            PreparedStatement pstm = connection.prepareStatement("select * from item");

            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

            ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()){
                String code = resultSet.getString("code");
                String desc = resultSet.getString("description");
                String qty = resultSet.getString("qtyOnHand");
                String Price = resultSet.getString("unitPrice");

                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("code",code);
                objectBuilder.add("description",desc);
                objectBuilder.add("qtyOnHand",qty);
                objectBuilder.add("unitPrice",Price);

                arrayBuilder.add(objectBuilder);
            }

            resp.setContentType("application/json");
            resp.getWriter().write(arrayBuilder.build().toString());

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("error>>>"+e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String code = req.getParameter("code");
        String desc = req.getParameter("description");
        String qty = req.getParameter("qtyOnHand");
        String Price = req.getParameter("unitPrice");


        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "Ijse@123");
            PreparedStatement pstm = connection.prepareStatement("insert into item(code,description,qtyOnHand,unitPrice)values(?,?,?,?)");

            pstm.setString(1,code);
            pstm.setString(2,desc);
            pstm.setString(3,qty);
            pstm.setString(4,Price);

            boolean isExecute = pstm.executeUpdate() > 0;

            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("code",code);
            objectBuilder.add("description",desc);
            objectBuilder.add("qtyOnHand",qty);
            objectBuilder.add("unitPrice",Price);

            if(isExecute){
                objectBuilder.add("status","complete");
            }else {
                objectBuilder.add("status","incomplete");
            }

            resp.setContentType("application/json");
            resp.getWriter().write(objectBuilder.build().toString());

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("error>>>>>"+e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String code = req.getParameter("code");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "Ijse@123");
            PreparedStatement pstm = connection.prepareStatement("delete from item where code=?");
            pstm.setString(1,code);

            boolean isExecute = pstm.executeUpdate() > 0;
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("code",code);

            if (isExecute) {
                objectBuilder.add("status","complete");
            }else {
                objectBuilder.add("status","incomplete");
            }

            resp.setContentType("application/json");
            resp.getWriter().write(objectBuilder.build().toString());


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("error>>>>>"+e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        String desc = req.getParameter("description");
        String qty = req.getParameter("qtyOnHand");
        String price = req.getParameter("unitPrice");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "Ijse@123");
            PreparedStatement pstm = connection.prepareStatement("update item set description=?,qtyOnHand=?,unitPrice=? where code=?");

            pstm.setString(1,desc);
            pstm.setString(2,qty);
            pstm.setString(3,price);
            pstm.setString(4,code);

            boolean isExecute = pstm.executeUpdate() > 0;
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("code",code);
            objectBuilder.add("description",desc);
            objectBuilder.add("qtyOnHand",qty);
            objectBuilder.add("unitPrice",price);

            if (isExecute) {
                objectBuilder.add("status","complete");
            }else {
                objectBuilder.add("status","incomplete");
            }

            resp.setContentType("application/json");
            resp.getWriter().write(objectBuilder.build().toString());

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("error>>>>>"+e.getMessage());
            throw new RuntimeException(e);
        }

    }
}
