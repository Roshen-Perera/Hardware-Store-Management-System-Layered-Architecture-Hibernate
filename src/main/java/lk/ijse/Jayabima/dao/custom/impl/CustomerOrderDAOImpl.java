package lk.ijse.Jayabima.dao.custom.impl;

import lk.ijse.Jayabima.dao.SQLUtil;
import lk.ijse.Jayabima.dao.custom.CustomerOrderDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class CustomerOrderDAOImpl implements CustomerOrderDAO {
    public String generateID() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1");

        if (rst.next()) {
            String id = rst.getString("order_id");
            int newCustomerOrderId = Integer.parseInt(id.replace("CO", "")) + 1;
            return String.format("CO%03d", newCustomerOrderId);
        } else {
            return "CO001";
        }
    }

    public boolean saveCustomerOrder(String order_id, String cus_id, String cus_name, double totalPrice, LocalDate date) throws SQLException {
        return SQLUtil.execute("INSERT INTO orders VALUES(?, ?, ?, ?, ?)", order_id, cus_id, cus_name, totalPrice, date);
    }
}
