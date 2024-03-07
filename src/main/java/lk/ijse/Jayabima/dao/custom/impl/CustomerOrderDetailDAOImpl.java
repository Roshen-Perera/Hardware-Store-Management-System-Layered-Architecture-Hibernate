package lk.ijse.Jayabima.dao.custom.impl;

import lk.ijse.Jayabima.dao.SQLUtil;
import lk.ijse.Jayabima.dao.custom.CustomerOrderDetailDAO;
import lk.ijse.Jayabima.dto.tm.CustomerCartTm;
import java.sql.SQLException;
import java.util.List;

public class CustomerOrderDetailDAOImpl implements CustomerOrderDetailDAO {
    public boolean saveCustomerOrderDetails(String orderId, List<CustomerCartTm> customerCartTmList) throws SQLException {
        for(CustomerCartTm tm : customerCartTmList) {
            if(!saveCustomerOrderDetails(orderId, tm)) {
                return false;
            }
        }
        return true;
    }

    public boolean saveCustomerOrderDetails(String orderId, CustomerCartTm tm) throws SQLException {
        return SQLUtil.execute("INSERT INTO itemorder_detail VALUES(?, ?, ?, ?, ?, ?, ?)", orderId, tm.getCode(), tm.getName(), tm.getDescription(), tm.getQty(), tm.getUnitPrice(), tm.getTot());
    }
}
