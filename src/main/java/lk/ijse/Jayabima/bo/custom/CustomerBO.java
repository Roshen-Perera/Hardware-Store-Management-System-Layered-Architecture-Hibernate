package lk.ijse.Jayabima.bo.custom;

import lk.ijse.Jayabima.bo.SuperBO;
import lk.ijse.Jayabima.dto.CustomerDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {
    ArrayList<CustomerDto> getAllCustomer() throws SQLException, ClassNotFoundException;

    boolean saveCustomer(CustomerDto dto) throws SQLException;

    boolean updateCustomer(CustomerDto dto) throws SQLException;

    boolean existCustomer(String id) throws SQLException;
    boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;

    String generateCustomerID() throws SQLException;

    CustomerDto searchCustomer(String id) throws SQLException;
}
