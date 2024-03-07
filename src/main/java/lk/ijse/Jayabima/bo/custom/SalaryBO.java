package lk.ijse.Jayabima.bo.custom;

import lk.ijse.Jayabima.bo.SuperBO;
import lk.ijse.Jayabima.dto.SalaryDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SalaryBO extends SuperBO {

    public ArrayList<SalaryDto> getAllSalary() throws SQLException;
    public boolean saveSalary(SalaryDto dto) throws SQLException;

    public boolean updateSalary(SalaryDto dto) throws SQLException;

    public boolean deleteSalary(String id) throws SQLException;
}
