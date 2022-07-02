package bo.custom;

import bo.SuperBo;
import dto.CustomerDTO;

import javax.json.JsonArrayBuilder;
import java.sql.SQLException;

public interface CustomerBO extends SuperBo {

    boolean addCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;
    boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;
    boolean updateCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;
    JsonArrayBuilder getAllCustomer() throws SQLException;
    public CustomerDTO getCustomer(String id) throws SQLException;
}
