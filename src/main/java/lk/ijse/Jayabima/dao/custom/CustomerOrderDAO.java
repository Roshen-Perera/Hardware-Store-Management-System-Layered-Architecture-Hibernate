package lk.ijse.Jayabima.dao.custom;

import lk.ijse.Jayabima.dao.SuperDAO;

import java.sql.SQLException;
import java.time.LocalDate;

public interface CustomerOrderDAO extends SuperDAO {

    String generateID() throws SQLException;
    boolean saveCustomerOrder(String order_id, String cus_id, String cus_name, double totalPrice, LocalDate date) throws SQLException;
}
