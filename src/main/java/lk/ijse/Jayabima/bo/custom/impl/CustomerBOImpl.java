package lk.ijse.Jayabima.bo.custom.impl;

import lk.ijse.Jayabima.bo.custom.CustomerBO;
import lk.ijse.Jayabima.dao.DAOFactory;
import lk.ijse.Jayabima.dao.custom.CustomerDAO;
import lk.ijse.Jayabima.dto.CustomerDto;
import lk.ijse.Jayabima.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public ArrayList<CustomerDto> getAllCustomer() throws SQLException {
        ArrayList<Customer> customers = customerDAO.getAll();
        ArrayList<CustomerDto> customerDTOS = new ArrayList<>();
        for (Customer customer:customers) {
            customerDTOS.add(new CustomerDto(customer.getId(),customer.getName(),customer.getAddress(), customer.getMobile()));
        }
        return customerDTOS;
    }

    @Override
    public boolean saveCustomer(CustomerDto dto) throws SQLException {
        //customer business logic example
        return customerDAO.save(new Customer(dto.getId(),dto.getName(),dto.getAddress(), dto.getMobile()));
    }

    @Override
    public boolean updateCustomer(CustomerDto dto) throws SQLException {
        return customerDAO.update(new Customer(dto.getId(),dto.getName(),dto.getAddress(),dto.getMobile()));
    }

    @Override
    public boolean existCustomer(String id) throws SQLException {
        return customerDAO.exist(id);
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        customerDAO.delete(id);
        return true;
    }

    @Override
    public String generateCustomerID() throws SQLException {
        return customerDAO.generateID();
    }

    @Override
    public CustomerDto searchCustomer(String id) throws SQLException {
        Customer customer = customerDAO.search(id);
        CustomerDto customerDto = new CustomerDto(customer.getId(),customer.getName(),customer.getAddress(), customer.getMobile());
        return customerDto;
    }
}
