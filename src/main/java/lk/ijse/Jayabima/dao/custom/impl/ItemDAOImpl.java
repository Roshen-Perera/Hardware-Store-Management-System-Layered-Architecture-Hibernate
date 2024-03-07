package lk.ijse.Jayabima.dao.custom.impl;

import lk.ijse.Jayabima.dao.SQLUtil;
import lk.ijse.Jayabima.dao.custom.ItemDAO;
import lk.ijse.Jayabima.dto.tm.CustomerCartTm;
import lk.ijse.Jayabima.dto.tm.StockCartTm;
import lk.ijse.Jayabima.entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public ArrayList<Item> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * from item");
        ArrayList<Item> itemArrayList = new ArrayList<>();

        while (rst.next()) {
            Item entity = new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4),
                    rst.getDouble(5),
                    rst.getString(6));
            itemArrayList.add(entity);
        }
        return itemArrayList;
    }

    @Override
    public String generateID() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT item_id FROM item ORDER BY item_id DESC LIMIT 1;");

        if (rst.next()) {
            String id = rst.getString("item_id");
            int newItemId = Integer.parseInt(id.replace("I", "")) + 1;
            return String.format("I%03d", newItemId);
        } else {
            return "I001";
        }
    }

    @Override
    public boolean save(Item entity) throws SQLException {
        return SQLUtil.execute("insert into item values (?, ?, ?, ?, ?, ?)", entity.getItemCode(), entity.getItemName(), entity.getItemDesc(), entity.getItemQty(), entity.getItemUnitPrice(), entity.getSupplierId());
    }

    @Override
    public boolean update(Item entity) throws SQLException {
        return SQLUtil.execute("update item set item_name = ?, item_desc = ?, item_qty = ?, item_unitPrice = ?, sup_id = ? where item_id = ?", entity.getItemName(), entity.getItemDesc(), entity.getItemQty(), entity.getItemUnitPrice(), entity.getSupplierId(), entity.getItemCode());
    }

    @Override
    public void delete(String id) throws SQLException, ClassNotFoundException {
        SQLUtil.execute("delete from item where item_id = ?",id);
    }

    @Override
    public boolean exist(String id) throws SQLException {
        ResultSet rst=SQLUtil.execute("SELECT item_id FROM item WHERE item_id=?",id);
        return rst.next();
    }

    @Override
    public Item search(String id) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * from item where item_id = ?", id);
        rst.next();
        return new Item(
                id + "",
                rst.getString("item_name"),
                rst.getString("item_desc"),
                rst.getInt("item_qty"),
                rst.getDouble("item_unitPrice"),
                rst.getString("sup_id")
        );
    }

    public boolean updateItem(List<CustomerCartTm> customerCartTmList) throws SQLException {
        for(CustomerCartTm tm : customerCartTmList) {
            System.out.println("Item: " + tm);
            if(!updateQty(tm.getCode(), tm.getQty())) {
                return false;
            }
        }
        return true;
    }

    public boolean updateQty(String code, int qty) throws SQLException {
        return SQLUtil.execute("UPDATE item SET item_qty = item_qty - ? WHERE item_id = ?", qty, code);
    }

    public boolean updateItem2(List<StockCartTm> stockCartTmList) throws SQLException {
        for(StockCartTm tm : stockCartTmList) {
            System.out.println("Item: " + tm);
            if(!updateQty2(tm.getCode(), tm.getQty())) {
                return false;
            }
        }
        return true;
    }

    public boolean updateQty2(String code, int qty) throws SQLException {
        return SQLUtil.execute("UPDATE item SET item_qty = item_qty + ? WHERE item_id = ?", qty, code);
    }
}
