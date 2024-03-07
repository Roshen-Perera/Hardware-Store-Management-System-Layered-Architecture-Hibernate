package lk.ijse.Jayabima.bo;

import lk.ijse.Jayabima.bo.custom.impl.*;
import lk.ijse.Jayabima.dao.custom.StockOrderDAO;
import lk.ijse.Jayabima.dao.custom.impl.SalaryDAOImpl;
import lk.ijse.Jayabima.dao.custom.impl.StockOrderDAOImpl;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){

    }
    public static BOFactory getBoFactory(){
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;

    }
    public enum BOTypes{
        REGISTER, LOGIN,CUSTOMER, SUPPLIER, ITEM, EMPLOYEE, SALARY, STOCK_ORDER, CUSTOMER_ORDER
    }
    public SuperBO getBO(BOTypes boTypes){
        switch (boTypes){
            case REGISTER:
                return new RegisterBOImpl();
            case LOGIN:
                return new LoginBOImpl();
            case CUSTOMER:
                return new CustomerBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case SALARY:
                return new SalaryBOImpl();
            case STOCK_ORDER:
                return new StockOrderBOImpl();
            case CUSTOMER_ORDER:
                return new CustomerOrderBOImpl();
            default:
                return null;
        }
    }
}
