package lk.ijse.Jayabima.bo.custom;

import lk.ijse.Jayabima.bo.SuperBO;
import lk.ijse.Jayabima.dto.SupplierDto;
import lk.ijse.Jayabima.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierBO extends SuperBO {
    ArrayList<SupplierDto> getAllSupplier() throws SQLException;

    boolean saveSupplier(SupplierDto dto) throws SQLException;

    boolean updateSupplier(SupplierDto dto) throws SQLException;

    boolean existSupplier(String id) throws SQLException;

    boolean deleteSupplier(String id) throws SQLException, ClassNotFoundException;

    String generateSupplierID() throws SQLException;

    SupplierDto searchSupplier(String id) throws SQLException;
}
