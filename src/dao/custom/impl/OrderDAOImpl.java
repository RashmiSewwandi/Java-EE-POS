package dao.custom.impl;

import com.sun.xml.internal.bind.v2.model.core.ID;
import dao.custom.OrderDAO;
import entity.Order;
import servlet.OrderServlet;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public boolean add(Order order) throws SQLException, ClassNotFoundException {
        Connection connection = OrderServlet.dataSource.getConnection();
        PreparedStatement pst = connection.prepareStatement("INSERT into `Order` values (?,?,?,?)");
        pst.setObject(1,order.getOrderID());
        pst.setObject(2,order.getOrderDate());
        pst.setObject(3,order.getOrderTime());
        pst.setObject(4,order.getCustID());

        int ex = pst.executeUpdate();
        connection.close();

        return  ex>0;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }



    @Override
    public boolean update(Order order) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public JsonObjectBuilder search(ID id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public JsonArrayBuilder getAll() throws SQLException {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

        Connection connection = OrderServlet.dataSource.getConnection();
        PreparedStatement pts  = connection.prepareStatement("select * from `Order`");
        ResultSet rst = pts.executeQuery();
        while (rst.next()){
            String  OrderID = rst.getString(1);
            String  OrderDate = rst.getString(2);
            String OrderTime = rst.getString(3);
            String CustID = rst.getString(4);

            objectBuilder.add("OrderID", OrderID);
            objectBuilder.add("OrderDate", OrderDate);
            objectBuilder.add("OrderTime", OrderTime);
            objectBuilder.add("CustID", CustID);
            arrayBuilder.add(objectBuilder.build());
        }
        connection.close();
        return arrayBuilder;
    }

    @Override
    public Object getCode(String id) throws SQLException {
        return null;
    }
}
