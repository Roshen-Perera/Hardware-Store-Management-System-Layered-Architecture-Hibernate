package lk.ijse.Jayabima.bo.custom.impl;

import lk.ijse.Jayabima.bo.custom.ItemBO;
import lk.ijse.Jayabima.dao.DAOFactory;
import lk.ijse.Jayabima.dao.custom.ItemDAO;
import lk.ijse.Jayabima.dto.ItemDto;
import lk.ijse.Jayabima.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    @Override
    public ArrayList<ItemDto> getAllItem() throws SQLException {
        ArrayList<Item> items = itemDAO.getAll();
        ArrayList<ItemDto> itemDtos = new ArrayList<>();
        for (Item item : items){
            itemDtos.add(new ItemDto(item.getItemCode(), item.getItemName(), item.getItemDesc(), item.getItemQty(), item.getItemUnitPrice(), item.getSupplierId()));
        }
        return itemDtos;
    }

    @Override
    public boolean saveItem(ItemDto dto) throws SQLException {
        return itemDAO.save(new Item(dto.getItemCode(), dto.getItemName(), dto.getItemDesc(), dto.getItemQty(), dto.getItemUnitPrice(), dto.getSupplierId()));
    }

    @Override
    public boolean updateItem(ItemDto dto) throws SQLException {
        return itemDAO.update(new Item(dto.getItemCode(), dto.getItemName(), dto.getItemDesc(), dto.getItemQty(), dto.getItemUnitPrice(), dto.getSupplierId()));
    }

    @Override
    public boolean existItem(String id) throws SQLException {
        return itemDAO.exist(id);
    }

    @Override
    public boolean deleteItem(String id) throws SQLException, ClassNotFoundException {
        itemDAO.delete(id);
        return true;
    }

    @Override
    public String generateItemID() throws SQLException {
        return itemDAO.generateID();
    }

    @Override
    public ItemDto searchItem(String id) throws SQLException {
        Item item = itemDAO.search(id);
        ItemDto itemDto = new ItemDto(item.getItemCode(), item.getItemName(), item.getItemDesc(), item.getItemQty(), item.getItemUnitPrice(), item.getSupplierId());
        return itemDto;
    }
}