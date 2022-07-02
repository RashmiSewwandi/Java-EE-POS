package bo.custom;

import bo.SuperBo;
import dto.OrderDto;

import javax.json.JsonArrayBuilder;
import java.sql.SQLException;

public interface OrderBO extends SuperBo {
    boolean placeOrder(OrderDto dto)throws SQLException, ClassNotFoundException;
    JsonArrayBuilder getAllOrder() throws SQLException;
}
