package lk.ijse.Jayabima.bo.custom.impl;

import lk.ijse.Jayabima.bo.custom.EmployeeBO;
import lk.ijse.Jayabima.dao.DAOFactory;
import lk.ijse.Jayabima.dao.custom.EmployeeDAO;
import lk.ijse.Jayabima.dto.EmployeeDto;
import lk.ijse.Jayabima.dto.SalaryDto;
import lk.ijse.Jayabima.entity.Employee;
import lk.ijse.Jayabima.entity.Salary;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeBOImpl implements EmployeeBO {
    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);

    @Override
    public ArrayList<EmployeeDto> getAllEmployee() throws SQLException {
        ArrayList<Employee> employees = employeeDAO.getAll();
        ArrayList<EmployeeDto> employeeDtos = new ArrayList<>();
        for (Employee employee:employees){
            employeeDtos.add(new EmployeeDto(employee.getId(), employee.getName(), employee.getRole(), employee.getAddress(), employee.getSalary(), employee.getMobile()));
        }
        return employeeDtos;
    }

    @Override
    public boolean saveEmployee(EmployeeDto dto) throws SQLException {
        return employeeDAO.save(new Employee(dto.getId(), dto.getName(), dto.getRole(), dto.getAddress(), dto.getSalary(), dto.getMobile()));
    }

    @Override
    public boolean updateEmployee(EmployeeDto dto) throws SQLException {
        return employeeDAO.update(new Employee(dto.getId(), dto.getName(), dto.getRole(), dto.getAddress(), dto.getSalary(), dto.getMobile()));
    }

    @Override
    public boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException {
        employeeDAO.delete(id);
        return true;
    }

    @Override
    public boolean existEmployee(String id) throws SQLException {
        return employeeDAO.exist(id);
    }

    @Override
    public String generateEmployeeID() throws SQLException {
        return employeeDAO.generateID();
    }

    @Override
    public EmployeeDto searchEmployee(String id) throws SQLException {
        Employee employee = employeeDAO.search(id);
        EmployeeDto employeeDto = new EmployeeDto(employee.getId(), employee.getName(), employee.getRole(), employee.getAddress(), employee.getSalary(), employee.getMobile());
        return employeeDto;
    }
}
