package lk.ijse.Jayabima.bo.custom.impl;

import lk.ijse.Jayabima.bo.custom.CustomerOrderBO;
import lk.ijse.Jayabima.dao.DAOFactory;
import lk.ijse.Jayabima.dao.custom.CustomerOrderDAO;
import lk.ijse.Jayabima.dao.custom.CustomerOrderDetailDAO;
import lk.ijse.Jayabima.dao.custom.ItemDAO;
import lk.ijse.Jayabima.db.DbConnection;
import lk.ijse.Jayabima.dto.PlaceCustomerOrderDto;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class CustomerOrderBOImpl implements CustomerOrderBO {

    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    CustomerOrderDAO customerOrderDAO = (CustomerOrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER_ORDER);
    CustomerOrderDetailDAO customerOrderDetailDAO = (CustomerOrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER_ORDER_DETAIL);

    public String generateCustomerOrderID() throws SQLException {
        return customerOrderDAO.generateID();
    }

    public boolean placeOrder(PlaceCustomerOrderDto placeItemOrderDto) throws SQLException {
        System.out.println(placeItemOrderDto);

        String orderId = placeItemOrderDto.getOrderId();
        String customerId = placeItemOrderDto.getCustomerId();
        String customerName = placeItemOrderDto.getCustomerName();
        double totalPrice = placeItemOrderDto.getTotalPrice();
        LocalDate date = placeItemOrderDto.getDate();

        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isOrderSaved = customerOrderDAO.saveCustomerOrder(orderId, customerId, customerName, totalPrice, date);
            if (isOrderSaved) {
                boolean isUpdated = itemDAO.updateItem(placeItemOrderDto.getCustomerCartTmList());
                if (isUpdated) {
                    boolean isOrderDetailSaved = customerOrderDetailDAO.saveCustomerOrderDetails(placeItemOrderDto.getOrderId(), placeItemOrderDto.getCustomerCartTmList());
                    if (isOrderDetailSaved) {
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
