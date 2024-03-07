package lk.ijse.Jayabima.dao.custom;

import lk.ijse.Jayabima.dao.SuperDAO;
import lk.ijse.Jayabima.entity.PlaceStockOrder;

import java.sql.SQLException;
import java.time.LocalDate;

public interface StockOrderDAO extends SuperDAO{
     String generateID() throws SQLException;
     boolean saveStockOrder(String order_id, String cus_id, LocalDate date) throws SQLException;
}
