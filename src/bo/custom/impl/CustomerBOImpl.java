package bo.custom.impl;

import bo.custom.CustomerBO;
import dao.DAOFactory;
import dao.custom.impl.CustomerDAOImpl;
import dto.CustomerDTO;
import entity.Customer;

import javax.json.JsonArrayBuilder;
import java.sql.SQLException;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAOImpl customerDAO = (CustomerDAOImpl) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    @Override
    public boolean addCustomer(CustomerDTO c) throws SQLException, ClassNotFoundException {
        Customer customer = new Customer(c.getCustID(),c.getCustName(),c.getCustAddress(),c.getSalary());
        return customerDAO.add(customer);
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id);
    }

    @Override
    public boolean updateCustomer(CustomerDTO c) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(c.getCustID(), c.getCustName(), c.getCustAddress(), c.getSalary()));
    }

    @Override
    public JsonArrayBuilder getAllCustomer() throws SQLException {
        return customerDAO.getAll();
    }

    @Override
    public CustomerDTO getCustomer(String id) throws SQLException {
       Customer customer = customerDAO.getCode(id);
       return new CustomerDTO(customer.getCustID(),customer.getCustName(),customer.getCustAddress(),customer.getSalary());

    }


}
