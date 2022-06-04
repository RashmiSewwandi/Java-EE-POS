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



@WebServlet(urlPatterns = "/item")
public class itemServlet extends HttpServlet {
    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource ds;


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String code = req.getParameter("code");
        String description = req.getParameter("description");
        String qtyOnHand = req.getParameter("qtyOnHand");
        String unitPrice = req.getParameter("unitPrice");

        System.out.println(code + "," + description + "," + qtyOnHand + "," + unitPrice);
        PrintWriter writer = resp.getWriter();

        Connection connection = null;

        try {
            connection = ds.getConnection();
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO item VALUES (?,?,?,?)");

            pstm.setObject(1, code);
            pstm.setObject(2, description);
            pstm.setObject(3, qtyOnHand);
            pstm.setObject(4, unitPrice);

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
        }
        finally {
            {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
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
                    ResultSet rst = connection.prepareStatement("SELECT * FROM item").executeQuery();
                    while (rst.next()) {
                        String code = rst.getString(1);
                        String description = rst.getString(2);
                        int qtyOnHand = rst.getInt(3);
                        double unitPrice = rst.getDouble(4);

                        objectBuilder.add("code", code);
                        objectBuilder.add("description", description);
                        objectBuilder.add("qtyOnHand", qtyOnHand);
                        objectBuilder.add("unitPrice", unitPrice);

                        arrayBuilder.add(objectBuilder.build());
                    }
                    response.add("status", 200);
                    response.add("massage", "Done");
                    response.add("data", arrayBuilder.build());
                    writer.print(response.build());

                    break;

                case "GENID":
                    ResultSet rst1 = connection.prepareStatement("SELECT code FROM item ORDER BY code DESC LIMIT 1").executeQuery();

                    if(rst1.next()){
                        int temp = Integer.parseInt(rst1.getString(1).split("-")[1]);
                        temp+=1;
                        if (temp<10){
                            objectBuilder.add("code", "I00-00" + temp);
                        } else if (temp < 100) {
                            objectBuilder.add("code", "I00-0" + temp);
                        } else if (temp < 1000) {
                            objectBuilder.add("code", "I00-" + temp);

                        }
                    }else{
                        objectBuilder.add("code", "I00-001");
                    }
                    response.add("data", objectBuilder.build());
                    response.add("massage", "Done");
                    response.add("status", 200);
                    writer.print(response.build());

                    break;

                case "SEARCH":
                    String code = req.getParameter("code");
                    PreparedStatement pstm = connection.prepareStatement("SELECT * FROM item WHERE code LIKE ?");
                    pstm.setObject(1,"%"+code+"%");
                    ResultSet resultSet = pstm.executeQuery();
                    while (resultSet.next()){
                        String ItemCode = resultSet.getString(1);
                        String ItemName = resultSet.getString(2);
                        String ItemPrice = resultSet.getString(3);
                        String ItemQTYOnHand = resultSet.getString(4);

                        resp.setStatus(HttpServletResponse.SC_OK);

                        objectBuilder.add("code",ItemCode);
                        objectBuilder.add("description",ItemName);
                        objectBuilder.add("qtyOnHand",ItemPrice);
                        objectBuilder.add("unitPrice",ItemQTYOnHand);

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

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();


        String code = jsonObject.getString("code");
        String description = jsonObject.getString("description");
        String qtyOnHand = jsonObject.getString("qtyOnHand");
        String unitPrice = jsonObject.getString("unitPrice");

        PrintWriter writer = resp.getWriter();

        Connection connection =null;
        try {

            connection = ds.getConnection();
            PreparedStatement pstm = connection.prepareStatement("UPDATE item SET description=?,qtyOnHand=?,unitPrice=? WHERE code=?");
            pstm.setObject(1,description);
            pstm.setObject(2,qtyOnHand);
            pstm.setObject(3,unitPrice);
            pstm.setObject(4,code);

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

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String code = req.getParameter("code");
        JsonObjectBuilder dataMsgBuilder = Json.createObjectBuilder();
        PrintWriter writer = resp.getWriter();

        Connection connection =null;
        try {
            connection = ds.getConnection();
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM item WHERE code=?");
            pstm.setObject(1,code);
            if (pstm.executeUpdate()>0){
                resp.setStatus(HttpServletResponse.SC_OK); //200
                dataMsgBuilder.add("data", "");
                dataMsgBuilder.add("massage", "Customer Deleted");
                dataMsgBuilder.add("status", "200");
                writer.print(dataMsgBuilder.build());
            }
        } catch (SQLException throwables) {

            dataMsgBuilder.add("status", 400);
            dataMsgBuilder.add("message", "Error");
            dataMsgBuilder.add("data", throwables.getLocalizedMessage());
            writer.print(dataMsgBuilder.build());
            resp.setStatus(HttpServletResponse.SC_OK); //200

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
