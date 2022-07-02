package bo.custom;

import bo.SuperBo;
import dto.ItemDTO;

import javax.json.JsonArrayBuilder;
import java.sql.SQLException;

public interface ItemBo extends SuperBo {
    boolean addItem(ItemDTO item) throws SQLException, ClassNotFoundException;
    boolean deleteItem(String id) throws SQLException, ClassNotFoundException;
    boolean updateItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException;
    JsonArrayBuilder getAllItem() throws SQLException;
}
