package lk.ijse.Jayabima.dao.custom.impl;

import lk.ijse.Jayabima.dao.SQLUtil;
import lk.ijse.Jayabima.dao.custom.StockOrderDetailDAO;
import lk.ijse.Jayabima.dto.tm.StockCartTm;

import java.sql.SQLException;
import java.util.List;

public class StockOrderDetailDAOImpl implements StockOrderDetailDAO {
    public boolean saveStockOrderDetails(String orderId, List<StockCartTm> stockCartTmList) throws SQLException {
        for(StockCartTm entity : stockCartTmList) {
            if(!saveStockOrderDetails(orderId, entity)) {
                return false;
            }
        }
        return true;
    }

    public boolean saveStockOrderDetails(String orderId, StockCartTm entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO stockOrder_detail  VALUES(?, ?, ?, ?, ?, ?)", orderId, entity.getCode(), entity.getName(), entity.getDescription(), entity.getQty(), entity.getSupName());
    }
}
