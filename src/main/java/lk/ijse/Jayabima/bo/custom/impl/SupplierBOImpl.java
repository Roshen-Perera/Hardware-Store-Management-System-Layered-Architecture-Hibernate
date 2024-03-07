package lk.ijse.Jayabima.bo.custom.impl;

import lk.ijse.Jayabima.bo.custom.SupplierBO;
import lk.ijse.Jayabima.dao.DAOFactory;
import lk.ijse.Jayabima.dao.custom.SupplierDAO;
import lk.ijse.Jayabima.dto.SupplierDto;
import lk.ijse.Jayabima.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierBOImpl implements SupplierBO {
    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER);
    @Override
    public ArrayList<SupplierDto> getAllSupplier() throws SQLException {
        ArrayList<Supplier> suppliers = supplierDAO.getAll();
        ArrayList<SupplierDto> supplierDtos = new ArrayList<>();
        for (Supplier supplier:suppliers){
            supplierDtos.add(new SupplierDto(supplier.getSupId(), supplier.getSupName(), supplier.getSupDesc(), supplier.getSupMobile()));
        }
        return supplierDtos;
    }

    @Override
    public boolean saveSupplier(SupplierDto dto) throws SQLException {
        return supplierDAO.save(new Supplier(dto.getSupId(), dto.getSupName(), dto.getSupDesc(), dto.getSupMobile()));
    }

    @Override
    public boolean updateSupplier(SupplierDto dto) throws SQLException {
        return supplierDAO.update(new Supplier(dto.getSupId(), dto.getSupName(), dto.getSupDesc(), dto.getSupMobile()));
    }

    @Override
    public boolean existSupplier(String id) throws SQLException {
        return supplierDAO.exist(id);
    }

    @Override
    public boolean deleteSupplier(String id) throws SQLException, ClassNotFoundException {
        supplierDAO.delete(id);
        return true;
    }

    @Override
    public String generateSupplierID() throws SQLException {
        return supplierDAO.generateID();
    }

    @Override
    public SupplierDto searchSupplier(String id) throws SQLException {
        Supplier supplier = supplierDAO.search(id);
        SupplierDto supplierDto = new SupplierDto(supplier.getSupId(), supplier.getSupName(), supplier.getSupDesc(), supplier.getSupMobile());
        return supplierDto;
    }
}
