package lk.ijse.Jayabima.dao.custom.impl;

import lk.ijse.Jayabima.dao.SQLUtil;
import lk.ijse.Jayabima.dao.custom.DashboardDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardDAOImpl implements DashboardDAO {
    public int countCustomer() throws SQLException{
        ResultSet rs = SQLUtil.execute( "SELECT count(*) from customer");
        rs.next();
        return rs.getInt(1);

    }

    public int countEmployee() throws SQLException {
        ResultSet rs = SQLUtil.execute("SELECT count(*) from employee");
        rs.next();
        return rs.getInt(1);
    }

    public int countSupplier() throws SQLException {
        ResultSet rs = SQLUtil.execute("SELECT count(*) from supplier");
        rs.next();
        return rs.getInt(1);
    }

    public int countItem() throws SQLException {
        ResultSet rs = SQLUtil.execute("SELECT count(*) from item");
        rs.next();
        return rs.getInt(1);
    }

    public int countStockOrder() throws SQLException {
        ResultSet rs = SQLUtil.execute("SELECT count(*) from stock_order");
        rs.next();
        return rs.getInt(1);
    }

    public int countItemOrder() throws SQLException {
        ResultSet rs = SQLUtil.execute("SELECT count(*) from orders");
        rs.next();
        return rs.getInt(1);
    }
}
