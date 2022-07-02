package bo.custom.impl;

import bo.custom.OrderBO;
import dao.DAOFactory;
import dao.custom.impl.OrderDAOImpl;
import dao.custom.impl.OrderDetailsDAOImpl;
import dto.OrderDto;

import javax.json.JsonArrayBuilder;
import java.sql.SQLException;

public class OrderBOImpl implements OrderBO {

    OrderDAOImpl orderDAO = (OrderDAOImpl) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    OrderDetailsDAOImpl orderDetailsDAO = (OrderDetailsDAOImpl) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAILS);

    @Override
    public boolean placeOrder(OrderDto dto) throws SQLException, ClassNotFoundException {
//        Connection connection = OrderServlet.dataSource.getConnection();
//        connection.setAutoCommit(false);
//        if(orderDAO.add(new Order(dto.getOrderID(),dto.getOrderDate(),dto.getOrderTime(),dto.getCustID()))){
//            for()
//        }
        return false;

    }

    @Override
    public JsonArrayBuilder getAllOrder() throws SQLException {
        return orderDAO.getAll();
    }
}
