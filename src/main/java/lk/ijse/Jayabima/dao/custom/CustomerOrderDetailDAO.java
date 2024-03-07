package lk.ijse.Jayabima.dao.custom;

import lk.ijse.Jayabima.dao.SuperDAO;
import lk.ijse.Jayabima.dto.tm.CustomerCartTm;

import java.sql.SQLException;
import java.util.List;

public interface CustomerOrderDetailDAO extends SuperDAO {
    boolean saveCustomerOrderDetails(String orderId, List<CustomerCartTm> customerCartTmList) throws SQLException;
    boolean saveCustomerOrderDetails(String orderId, CustomerCartTm tm) throws SQLException;
}
