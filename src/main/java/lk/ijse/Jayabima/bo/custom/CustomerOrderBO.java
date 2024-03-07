package lk.ijse.Jayabima.bo.custom;

import lk.ijse.Jayabima.bo.SuperBO;
import lk.ijse.Jayabima.dto.PlaceCustomerOrderDto;

import java.sql.SQLException;

public interface CustomerOrderBO extends SuperBO {
    String generateCustomerOrderID() throws SQLException;
    boolean placeOrder(PlaceCustomerOrderDto placeItemOrderDto) throws SQLException;
}
