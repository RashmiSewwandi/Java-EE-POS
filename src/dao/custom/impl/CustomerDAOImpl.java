package dao.custom.impl;

import Entity.Customer;
import com.sun.xml.internal.bind.v2.model.core.ID;
import dao.custom.CustomerDAO;
import servlet.CustomerServlet;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAOImpl implements CustomerDAO {


    @Override
    public boolean add(Customer customer) throws SQLException, ClassNotFoundException {
        Connection connection = CustomerServlet.dataSource.getConnection();
        PreparedStatement pstm = connection.prepareStatement("INSERT into customer values(?,?,?,?)");

        pstm.setObject(1, customer.getId());
        pstm.setObject(2, customer.getName());
        pstm.setObject(3, customer.getAddress());
        pstm.setObject(4, customer.getSalary());

        int executeUpdate = pstm.executeUpdate();
        connection.close();

        return executeUpdate > 0;

    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        Connection connection = CustomerServlet.dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("Delete from customer where id=?");
        preparedStatement.setObject(1, id);

        int excuteupdate = preparedStatement.executeUpdate();
        connection.close();
        return excuteupdate > 0;
    }



    @Override
    public boolean update(Customer c) throws SQLException, ClassNotFoundException {
        Connection connection = CustomerServlet.dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE customer SET name=?,address=?,salary=? WHERE id=?");
        preparedStatement.setObject(1, c.getName());
        preparedStatement.setObject(2, c.getAddress());
        preparedStatement.setObject(3, c.getSalary());
        preparedStatement.setObject(4, c.getId());

        int executeUpdate = preparedStatement.executeUpdate();
        connection.close();

        return executeUpdate > 0;
    }

    @Override
    public JsonObjectBuilder search(ID id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public JsonArrayBuilder getAll() throws SQLException {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

        Connection connection = CustomerServlet.dataSource.getConnection();
        PreparedStatement pts  = connection.prepareStatement("select * from customer");
        ResultSet rst = pts.executeQuery();
        while (rst.next()){
            String id = rst.getString(1);
            String name = rst.getString(2);
            String address = rst.getString(3);
            double salary = rst.getDouble(4);

            objectBuilder.add("id", id);
            objectBuilder.add("name", name);
            objectBuilder.add("address", address);
            objectBuilder.add("salary", salary);
            arrayBuilder.add(objectBuilder.build());
        }
        connection.close();
        return arrayBuilder;
    }

    @Override
    public Customer getCode(String id) throws SQLException {
        Connection connection = CustomerServlet.dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from customer where id=?");
        preparedStatement.setObject(1,id);

        ResultSet resultSet = preparedStatement.executeQuery();

        Customer customer = null;

        if (resultSet.next()){
            customer = new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4)
            );

        }
        return customer;
    }
}