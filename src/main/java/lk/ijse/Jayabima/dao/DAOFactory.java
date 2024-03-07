package lk.ijse.Jayabima.dao;

import lk.ijse.Jayabima.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory(){}

    public static DAOFactory getDaoFactory(){
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes{
        REGISTER, LOGIN, DASHBOARD, CUSTOMER, SUPPLIER, ITEM, EMPLOYEE, SALARY, STOCK_ORDER, STOCK_ORDER_DETAIL, CUSTOMER_ORDER, CUSTOMER_ORDER_DETAIL
    }

    public SuperDAO getDAO(DAOTypes daoTypes){
        switch (daoTypes){
            case REGISTER:
                return new RegisterDAOImpl();
            case LOGIN:
                return new LoginDAOImpl();
            case DASHBOARD:
                return new DashboardDAOImpl();
            case CUSTOMER:
                return new CustomerDAOImpl();
            case SUPPLIER:
                return new SupplierDAOImpl();
            case ITEM:
                return new ItemDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case SALARY:
                return new SalaryDAOImpl();
            case STOCK_ORDER:
                return new StockOrderDAOImpl();
            case STOCK_ORDER_DETAIL:
                return new StockOrderDetailDAOImpl();
            case CUSTOMER_ORDER:
                return new CustomerOrderDAOImpl();
            case CUSTOMER_ORDER_DETAIL:
                return new CustomerOrderDetailDAOImpl();
            default:
                return null;
        }
    }
}
