package bo.custom.impl;

import Entity.Item;
import bo.custom.ItemBo;
import dao.DAOFactory;
import dao.custom.impl.ItemDAOImpl;
import dto.ItemDTO;


import javax.json.JsonArrayBuilder;
import java.sql.SQLException;

public class ItemBoImpl implements ItemBo {

    ItemDAOImpl itemDAO = (ItemDAOImpl) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);


    @Override
    public boolean addItem(ItemDTO i) throws SQLException, ClassNotFoundException {
        Item item1 = new Item(i.getCode(),i.getDescription(),i.getQtyOnHand(),i.getUnitPrice());
        return itemDAO.add(item1);
    }

    @Override
    public boolean deleteItem(String id) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(id);
    }

    @Override
    public boolean updateItem(ItemDTO i) throws SQLException, ClassNotFoundException {
        return itemDAO.update(new Item(i.getCode(),i.getDescription(),i.getQtyOnHand(),i.getUnitPrice()));
    }

    @Override
    public JsonArrayBuilder getAllItem() throws SQLException {
        return itemDAO.getAll();
    }
}
