package servlet;


import javax.annotation.Resource;
import javax.json.*;
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
import java.sql.ResultSet;
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

        System.out.println(id + "," + name + "," + address + "," + salary);

        PrintWriter writer = resp.getWriter(); //responser eka print karanna

        Connection connection = null;

        try {
            connection = ds.getConnection();
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO customer VALUES (?,?,?,?)");

            pstm.setObject(1, id);
            pstm.setObject(2, name);
            pstm.setObject(3, address);
            pstm.setObject(4, salary);

            if (pstm.executeUpdate() > 0) {
                JsonObjectBuilder response = Json.createObjectBuilder();
                resp.setStatus(HttpServletResponse.SC_CREATED);//response eka create we.
                response.add("status", 200);
                response.add("message", "successfully added");
                response.add("data", "");
                writer.print(response.build());
            }


        } catch (SQLException throwables) {
            JsonObjectBuilder response = Json.createObjectBuilder();
            resp.setStatus(HttpServletResponse.SC_OK);
            response.add("status", 400);
            response.add("message", "Error");
            response.add("data", throwables.getLocalizedMessage());
            writer.print(response.build());
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        JsonObjectBuilder response = Json.createObjectBuilder();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        PrintWriter writer = resp.getWriter();


        Connection connection = null;
        try {
            String option = req.getParameter("option");
            connection = ds.getConnection();

            switch (option) {
                case "GETALL":
                    ResultSet rst = connection.prepareStatement("SELECT * FROM customer").executeQuery();
                    while (rst.next()) {
                        String id = rst.getString(1);
                        String name = rst.getString(2);
                        String address = rst.getString(3);
                        String salary = rst.getString(4);

                        objectBuilder.add("id", id);
                        objectBuilder.add("name", name);
                        objectBuilder.add("address", address);
                        objectBuilder.add("salary", salary);

                        arrayBuilder.add(objectBuilder.build());
                    }
                    response.add("status", 200);
                    response.add("massage", "Done");
                    response.add("data", arrayBuilder.build());
                    writer.print(response.build());

                    break;

                case "GENID":
                    ResultSet rst1 = connection.prepareStatement("SELECT id FROM customer ORDER BY id DESC LIMIT 1").executeQuery();

                    if(rst1.next()){
                        int temp = Integer.parseInt(rst1.getString(1).split("-")[1]);
                        temp+=1;
                        if (temp<10){
                            objectBuilder.add("id", "C00-00" + temp);
                        } else if (temp < 100) {
                            objectBuilder.add("id", "C00-0" + temp);
                        } else if (temp < 1000) {
                            objectBuilder.add("id", "C00-" + temp);

                        }
                    }else{
                        objectBuilder.add("id", "C00-001");
                    }
                    response.add("data", objectBuilder.build());
                    response.add("massage", "Done");
                    response.add("status", 200);
                    writer.print(response.build());

                    break;

                case "SEARCH":
                    String id = req.getParameter("id");
                    PreparedStatement pstm = connection.prepareStatement("SELECT * FROM customer WHERE id LIKE ?");
                    pstm.setObject(1,"%"+id+"%");
                    ResultSet resultSet = pstm.executeQuery();
                    while (resultSet.next()){
                        String custId = resultSet.getString(1);
                        String custName = resultSet.getString(2);
                        String custAddress = resultSet.getString(3);
                        String custSalary = resultSet.getString(4);

                        resp.setStatus(HttpServletResponse.SC_OK);

                        objectBuilder.add("id",custId);
                        objectBuilder.add("name",custName);
                        objectBuilder.add("address",custAddress);
                        objectBuilder.add("salary",custSalary);

                        arrayBuilder.add(objectBuilder.build());

                    }
                    response.add("data",arrayBuilder.build());
                    response.add("message","Done");
                    response.add("status",200);
                    writer.print(response.build());

                    break;




            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }



    }

    //update
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();

        String id = jsonObject.getString("id");
        String name = jsonObject.getString("name");
        String address = jsonObject.getString("address");
        String salary = jsonObject.getString("salary");

        PrintWriter writer = resp.getWriter();

        Connection connection =null;
        try {

            connection = ds.getConnection();
            PreparedStatement pstm = connection.prepareStatement("UPDATE customer SET name=?,address=?,salary=? WHERE id=?");
            pstm.setObject(1,name);
            pstm.setObject(2,address);
            pstm.setObject(3,salary);
            pstm.setObject(4,id);

            if(pstm.executeUpdate()>0){
                JsonObjectBuilder response = Json.createObjectBuilder();//{"data":[],"massage":"Done","status":200}
                resp.setStatus(HttpServletResponse.SC_CREATED);
                response.add("status",200);
                response.add("message","successfully updated");
                response.add("data","");

                writer.print(response.build());
            }

        } catch (SQLException throwables) {
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status",400);
            response.add("message","error");
            response.add("data",throwables.getLocalizedMessage());

            writer.print(response.build());
            resp.setStatus(HttpServletResponse.SC_OK);

        }
        finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }


    }
}
