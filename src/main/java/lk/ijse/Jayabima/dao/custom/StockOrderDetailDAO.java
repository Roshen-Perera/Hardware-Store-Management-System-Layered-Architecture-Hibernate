package lk.ijse.Jayabima.dao.custom;

import lk.ijse.Jayabima.dao.SuperDAO;
import lk.ijse.Jayabima.dto.tm.StockCartTm;
import lk.ijse.Jayabima.entity.StockCart;

import java.sql.SQLException;
import java.util.List;

public interface StockOrderDetailDAO extends SuperDAO {
    boolean saveStockOrderDetails(String orderId, List<StockCartTm> stockCartTmList) throws SQLException;

    boolean saveStockOrderDetails(String orderId, StockCartTm entity) throws SQLException;
}
