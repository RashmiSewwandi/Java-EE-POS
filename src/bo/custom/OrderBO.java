package bo.custom;

import bo.SuperBo;
import dto.OrderDTO;


import javax.json.JsonArrayBuilder;
import java.sql.SQLException;

public interface OrderBO extends SuperBo {
    boolean placeOrder(OrderDTO dto)throws SQLException, ClassNotFoundException;
    JsonArrayBuilder getAllOrder() throws SQLException;
}
