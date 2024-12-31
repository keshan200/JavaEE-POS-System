import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@WebServlet(urlPatterns = "/orders")
public class OrderServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String oId = req.getParameter("oid");
        String date = req.getParameter("date");
        String customerId = req.getParameter("customerID");


        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "Ijse@123");
            PreparedStatement pstm = connection.prepareStatement("insert into orders(oid,date,customerID)values(?,?,?)");
            pstm.setString(1, oId);
            pstm.setString(2, date);
            pstm.setString(3, customerId);

            boolean isExecute = pstm.executeUpdate() > 0;
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("OrderID", oId);
            objectBuilder.add("Date", date);
            objectBuilder.add("CustomerID", customerId);

            if (isExecute) {
                objectBuilder.add("status", "success");
            }else {
                objectBuilder.add("status", "fail");
            }

            resp.setContentType("application/json");
            resp.getWriter().write(objectBuilder.build().toString());

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("error>>>>"+e.getMessage());
            throw new RuntimeException(e);
        }

    }
}
