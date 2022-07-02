package bo.custom.impl;

import bo.custom.OrderDetailsBO;
import dao.DAOFactory;
import dao.custom.impl.OrderDetailsDAOImpl;

import javax.json.JsonArrayBuilder;
import java.sql.SQLException;

public class OrderDetailsBoImpl implements OrderDetailsBO {

    OrderDetailsDAOImpl orderDetailsDAO = (OrderDetailsDAOImpl) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAILS);


    @Override
    public JsonArrayBuilder getAllOrderDetails() throws SQLException {
        return orderDetailsDAO.getAll();
    }
}
