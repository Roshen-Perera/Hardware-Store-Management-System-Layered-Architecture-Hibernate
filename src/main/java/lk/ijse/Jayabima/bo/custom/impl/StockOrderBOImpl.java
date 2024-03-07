package lk.ijse.Jayabima.bo.custom.impl;

import lk.ijse.Jayabima.bo.custom.StockOrderBO;
import lk.ijse.Jayabima.dao.DAOFactory;
import lk.ijse.Jayabima.dao.custom.ItemDAO;
import lk.ijse.Jayabima.dao.custom.StockOrderDAO;
import lk.ijse.Jayabima.dao.custom.StockOrderDetailDAO;
import lk.ijse.Jayabima.db.DbConnection;
import lk.ijse.Jayabima.dto.PlaceStockOrderDto;
import lk.ijse.Jayabima.entity.PlaceStockOrder;
import lk.ijse.Jayabima.entity.StockCart;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class StockOrderBOImpl implements StockOrderBO {
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    StockOrderDAO stockOrderDAO = (StockOrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STOCK_ORDER);
    StockOrderDetailDAO stockOrderDetailDAO = (StockOrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STOCK_ORDER_DETAIL);


    public String generateStockOrderID() throws SQLException {
        return stockOrderDAO.generateID();
    }
    @Override
    public boolean placeStockOrder(PlaceStockOrderDto placeStockOrderDto) throws SQLException {
        System.out.println(placeStockOrderDto);

        String stockOrderId = placeStockOrderDto.getStockOrder_id();
        String supplierId = placeStockOrderDto.getSup_id();
        LocalDate date = placeStockOrderDto.getSupOrder_date();

        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isStockOrderSaved = stockOrderDAO.saveStockOrder(stockOrderId, supplierId, date);
            if (isStockOrderSaved) {
                boolean isUpdated = itemDAO.updateItem2(placeStockOrderDto.getStockCartTmList());
                if (isUpdated) {
                    boolean isStockOrderDetailSaved = stockOrderDetailDAO.saveStockOrderDetails(placeStockOrderDto.getStockOrder_id(), placeStockOrderDto.getStockCartTmList());
                    if (isStockOrderDetailSaved) {
                        connection.commit();
                    }
                }
            }
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
        return true;
    }
}
