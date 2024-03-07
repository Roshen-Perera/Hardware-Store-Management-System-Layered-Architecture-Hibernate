package lk.ijse.Jayabima.dao.custom.impl;

import lk.ijse.Jayabima.dao.SQLUtil;
import lk.ijse.Jayabima.dao.custom.StockOrderDAO;
import java.sql.*;
import java.time.LocalDate;

public class StockOrderDAOImpl implements StockOrderDAO {

    public String generateID() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT stockOrder_id FROM stock_order ORDER BY stockOrder_id DESC LIMIT 1");

        if (rst.next()) {
            String id = rst.getString("stockOrder_id");
            int newStockOrderId = Integer.parseInt(id.replace("SO", "")) + 1;
            return String.format("SO%03d", newStockOrderId);
        } else {
            return "SO001";
        }
    }

    public boolean saveStockOrder(String order_id, String sup_id, LocalDate date) throws SQLException {
        return SQLUtil.execute("INSERT INTO stock_order VALUES(?, ?, ?)", order_id, sup_id, date);
    }
}
