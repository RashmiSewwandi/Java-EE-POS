package dao.custom.impl;

import com.sun.xml.internal.bind.v2.model.core.ID;
import dao.custom.OrderDetailsDAO;
import entity.Order_Details;
import servlet.OrderDetailsServlet;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDetailsDAOImpl  implements OrderDetailsDAO {
    @Override
    public boolean add(Order_Details os) throws SQLException, ClassNotFoundException {
        Connection connection = OrderDetailsServlet.dataSource.getConnection();
        PreparedStatement pst = connection.prepareStatement("INSERT into `Order Details` values (?,?,?,?,?)");
        pst.setObject(1,os.getOrderID());
        pst.setObject(2,os.getItemCode());
        pst.setObject(3,os.getOrderqty());
        pst.setObject(4,os.getDiscount());
        pst.setObject(5,os.getBalance());

        int ex = pst.executeUpdate();
        connection.close();

        return  ex>0;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }



    @Override
    public boolean update(Order_Details order_details) throws SQLException, ClassNotFoundException {
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

        Connection connection = OrderDetailsServlet.dataSource.getConnection();
        PreparedStatement pts  = connection.prepareStatement("select * from `Order Details`");
        ResultSet rst = pts.executeQuery();
        while (rst.next()){
            String  orderID = rst.getString(1);
            String  itemCode = rst.getString(2);
            Integer orderqty = rst.getInt(3);
            Double discount = rst.getDouble(4);
            Double balance = rst.getDouble(5);

            objectBuilder.add("orderID", orderID);
            objectBuilder.add("itemCode", itemCode);
            objectBuilder.add("orderqty", orderqty);
            objectBuilder.add("discount", discount);
            objectBuilder.add("balance", balance);
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
