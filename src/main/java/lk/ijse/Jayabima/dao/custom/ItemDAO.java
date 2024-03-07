package lk.ijse.Jayabima.dao.custom;

import lk.ijse.Jayabima.dao.CrudDAO;
import lk.ijse.Jayabima.dto.tm.CustomerCartTm;
import lk.ijse.Jayabima.dto.tm.StockCartTm;
import lk.ijse.Jayabima.entity.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemDAO extends CrudDAO<Item> {

    boolean updateItem(List<CustomerCartTm> customerCartTmList) throws SQLException;

    boolean updateQty(String code, int qty) throws SQLException;

    boolean updateItem2(List<StockCartTm> stockCartTmList) throws SQLException;

    boolean updateQty2(String code, int qty) throws SQLException;
}
