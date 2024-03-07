package lk.ijse.Jayabima.dao.custom.impl;

import lk.ijse.Jayabima.dao.SQLUtil;
import lk.ijse.Jayabima.dao.custom.CustomerDAO;
import lk.ijse.Jayabima.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    public ArrayList<Customer> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * from customer");
        ArrayList<Customer> customerArrayList = new ArrayList<>();

        while (rst.next()) {
            Customer entity = new Customer(
                    rst.getString("cus_id"),
                    rst.getString("cus_name"),
                    rst.getString("cus_address"),
                    rst.getString("cus_mobile"));
            customerArrayList.add(entity);
        }
        return customerArrayList;
    }

    public String generateID() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT cus_id FROM Customer ORDER BY cus_id DESC LIMIT 1;");

        if (rst.next()) {
            String id = rst.getString("cus_id");
            int newCustomerId = Integer.parseInt(id.replace("C", "")) + 1;
            return String.format("C%03d", newCustomerId);
        } else {
            return "C001";
        }
    }

    public boolean save(Customer entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO customer VALUES(?, ?, ?, ?)", entity.getId(), entity.getName(), entity.getAddress(), entity.getMobile());
    }

    public boolean update(Customer entity) throws SQLException {
        return SQLUtil.execute("UPDATE customer set cus_name = ?, cus_address = ?, cus_mobile = ? where cus_id = ?", entity.getName(), entity.getAddress(), entity.getMobile(), entity.getId());
    }

    public void delete(String id) throws SQLException, ClassNotFoundException {
        SQLUtil.execute("DELETE FROM Customer WHERE cus_id=?",id);
    }

    public boolean exist(String id) throws SQLException {
        ResultSet rst=SQLUtil.execute("SELECT cus_id FROM Customer WHERE cus_id=?",id);
        return rst.next();

    }

    public Customer search(String id) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Customer WHERE cus_id=?",id);
        rst.next();
        return new Customer(
                id + "",
                rst.getString("cus_name"),
                rst.getString("cus_address"),
                rst.getString("cus_mobile")
        );
    }
}
