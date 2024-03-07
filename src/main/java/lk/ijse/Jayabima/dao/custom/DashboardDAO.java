package lk.ijse.Jayabima.dao.custom;

import lk.ijse.Jayabima.dao.SQLUtil;
import lk.ijse.Jayabima.dao.SuperDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DashboardDAO extends SuperDAO {
    int countCustomer() throws SQLException;
    int countEmployee() throws SQLException;
    int countSupplier() throws SQLException;
    int countItem() throws SQLException;
    int countStockOrder() throws SQLException;
    int countItemOrder() throws SQLException;
}
