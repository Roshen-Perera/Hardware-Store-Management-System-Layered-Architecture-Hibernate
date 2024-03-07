package lk.ijse.Jayabima.bo.custom;

import lk.ijse.Jayabima.bo.SuperBO;
import lk.ijse.Jayabima.dto.PlaceStockOrderDto;

import java.sql.SQLException;

public interface StockOrderBO extends SuperBO {
    String generateStockOrderID() throws SQLException;
    boolean placeStockOrder(PlaceStockOrderDto entity) throws SQLException;
}
