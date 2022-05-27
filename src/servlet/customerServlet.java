package servlet;


import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/customer")
public class customerServlet extends HttpServlet {
   @Resource(name = "java:comp/env/jdbc/pool")
    DataSource ds;

   //save the customers
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        String salary = req.getParameter("salary");

        System.out.println(id+"," +name+","+address+","+salary);

        PrintWriter writer = resp.getWriter(); //responser eka print karanna

        Connection connection=null;

        try {
            connection = ds.getConnection();
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO customer VALUES (?,?,?,?)");

            pstm.setObject(1,id);
            pstm.setObject(2,name);
            pstm.setObject(3,address);
            pstm.setObject(4,salary);

            if(pstm.executeUpdate()>0){
                JsonObjectBuilder response = Json.createObjectBuilder();
                resp.setStatus(HttpServletResponse.SC_CREATED);//response eka create we.
                response.add("status",200);
                response.add("message","successfully added");
                response.add("data","");
                writer.print(response.build());
            }


        } catch (SQLException throwables) {
            JsonObjectBuilder response = Json.createObjectBuilder();
            resp.setStatus(HttpServletResponse.SC_OK);
            response.add("status",400);
            response.add("message","Error");
            response.add("data",throwables.getLocalizedMessage());
            writer.print(response.build());
        }
        finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("run");
    }

}
