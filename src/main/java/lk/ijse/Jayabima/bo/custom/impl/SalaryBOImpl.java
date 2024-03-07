package lk.ijse.Jayabima.bo.custom.impl;

import lk.ijse.Jayabima.bo.custom.SalaryBO;
import lk.ijse.Jayabima.dao.DAOFactory;
import lk.ijse.Jayabima.dao.custom.SalaryDAO;
import lk.ijse.Jayabima.dao.custom.impl.SalaryDAOImpl;
import lk.ijse.Jayabima.dto.SalaryDto;
import lk.ijse.Jayabima.entity.Salary;

import java.sql.SQLException;
import java.util.ArrayList;

public class SalaryBOImpl implements SalaryBO {

    SalaryDAO salaryDAO = (SalaryDAOImpl) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SALARY);

    @Override
    public ArrayList<SalaryDto> getAllSalary() throws SQLException {
        ArrayList<Salary> salaries = salaryDAO.getAllSalary();
        ArrayList<SalaryDto> salaryDtos = new ArrayList<>();
        for (Salary salary:salaries) {
            salaryDtos.add(new SalaryDto(salary.getId(),salary.getSalary(),salary.getStatus()));
        }
        return salaryDtos;
    }
    @Override
    public boolean saveSalary(SalaryDto dto) throws SQLException {
        return salaryDAO.saveSalary(new Salary(dto.getId(), dto.getSalary(), dto.getStatus()));
    }

    @Override
    public boolean updateSalary(SalaryDto dto) throws SQLException {
        return salaryDAO.updateSalary(new Salary(dto.getId(), dto.getSalary(), dto.getStatus()));
    }

    @Override
    public boolean deleteSalary(String id) throws SQLException {
        salaryDAO.deleteSalary(id);
        return true;
    }
}
