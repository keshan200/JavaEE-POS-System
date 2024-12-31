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

@WebServlet(urlPatterns = "/orderDetails")
public class PlaceOrderServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "Ijse@123");

                PreparedStatement pstm = connection.prepareStatement("select description from item");
                PreparedStatement customerStmt = connection.prepareStatement("SELECT id FROM customer");

                ResultSet customerSet = customerStmt.executeQuery();
                JsonArrayBuilder customerArray = Json.createArrayBuilder();

                ResultSet itemSet = pstm.executeQuery();
                JsonArrayBuilder itemArray = Json.createArrayBuilder();


               while (itemSet.next()) {
                    String desc = itemSet.getString("description");
                    JsonObjectBuilder itemObj = Json.createObjectBuilder();
                    itemObj.add("description", desc);

                    itemArray.add(itemObj);
                }


                while (customerSet.next()) {
                    String id = customerSet.getString("id");
                    JsonObjectBuilder customerObj = Json.createObjectBuilder();
                    customerObj.add("id", id);

                    customerArray.add(customerObj);
                }



            JsonObjectBuilder finalObj = Json.createObjectBuilder();
            finalObj.add("items", itemArray);
            finalObj.add("customers", customerArray);


            resp.setContentType("application/json");
            resp.getWriter().write(finalObj.build().toString());

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("error>>>>>"+e.getMessage());
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderID = req.getParameter("oid");
        String itemCode = req.getParameter("itemCode");



        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "Ijse@123");
            PreparedStatement pstm = connection.prepareStatement("insert into orderdetails values(?,?)");

            pstm.setString(1, orderID);
            pstm.setString(2, itemCode);

            boolean isExecute = pstm.executeUpdate() > 0;

            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("itemCode", itemCode);
            objectBuilder.add("orderID", orderID);

            if (isExecute) {
                objectBuilder.add("status", "success");
            }else {
                objectBuilder.add("status", "fail");
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
        String qtyOnHand = req.getParameter("qtyOnHand");


        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "Ijse@123");
            PreparedStatement pstm = connection.prepareStatement("update item set qtyOnHand = qtyOnHand-? where code = ?");
            pstm.setString(1, qtyOnHand);
            pstm.setString(2, code);

            boolean isExecute = pstm.executeUpdate() > 0;
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("qtyOnHand", qtyOnHand);
            objectBuilder.add("code", code);

            if (isExecute) {
                objectBuilder.add("status", "success");
            }else {
                objectBuilder.add("status", "fail");
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







