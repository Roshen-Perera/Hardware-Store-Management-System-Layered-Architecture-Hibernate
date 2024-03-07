package lk.ijse.Jayabima.bo.custom;

import lk.ijse.Jayabima.bo.SuperBO;
import lk.ijse.Jayabima.dto.EmployeeDto;
import lk.ijse.Jayabima.dto.SalaryDto;
import lk.ijse.Jayabima.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeBO extends SuperBO {
    ArrayList<EmployeeDto> getAllEmployee() throws SQLException;
    boolean saveEmployee(EmployeeDto dto) throws SQLException;

    boolean updateEmployee(EmployeeDto dto) throws SQLException;

    boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException;

    boolean existEmployee(String id) throws SQLException;

    String generateEmployeeID() throws SQLException;
    EmployeeDto searchEmployee(String id) throws SQLException;

}
