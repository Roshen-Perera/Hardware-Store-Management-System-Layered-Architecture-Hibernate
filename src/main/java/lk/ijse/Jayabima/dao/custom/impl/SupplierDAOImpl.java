package lk.ijse.Jayabima.dao.custom.impl;

import lk.ijse.Jayabima.dao.SQLUtil;
import lk.ijse.Jayabima.dao.custom.SupplierDAO;
import lk.ijse.Jayabima.entity.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierDAOImpl implements SupplierDAO {
    @Override
    public ArrayList<Supplier> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * from supplier");
        ArrayList<Supplier> supplierArrayList = new ArrayList<>();

        while (rst.next()) {
            Supplier entity = new Supplier(
                    rst.getString("sup_id"),
                    rst.getString("sup_name"),
                    rst.getString("sup_description"),
                    rst.getString("sup_contact"));
            supplierArrayList.add(entity);
        }
        return supplierArrayList;
    }

    @Override
    public String generateID() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT sup_id FROM Supplier ORDER BY sup_id DESC LIMIT 1;");

        if (rst.next()) {
            String id = rst.getString("sup_id");
            int newSupplierId = Integer.parseInt(id.replace("S", "")) + 1;
            return String.format("S%03d", newSupplierId);
        } else {
            return "S001";
        }
    }

    @Override
    public boolean save(Supplier entity) throws SQLException {
        return SQLUtil.execute("insert into supplier values (?, ?, ?, ?)", entity.getSupId(), entity.getSupName(), entity.getSupDesc(), entity.getSupMobile());
    }

    @Override
    public boolean update(Supplier entity) throws SQLException {
        return SQLUtil.execute("update supplier set sup_name = ?, sup_description = ?, sup_contact = ? where sup_id = ?", entity.getSupName(), entity.getSupDesc(), entity.getSupMobile(), entity.getSupId());
    }

    @Override
    public void delete(String id) throws SQLException, ClassNotFoundException {
        SQLUtil.execute("delete from supplier where sup_id = ?",id);
    }

    @Override
    public boolean exist(String id) throws SQLException {
        ResultSet rst=SQLUtil.execute("SELECT sup_id FROM supplier WHERE sup_id=?",id);
        return rst.next();
    }

    @Override
    public Supplier search(String id) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Supplier WHERE sup_id=?",id);
        rst.next();
        return new Supplier(
                id + "",
                rst.getString("sup_name"),
                rst.getString("sup_description"),
                rst.getString("sup_contact")
        );
    }
}
