package lk.ijse.Jayabima.dao.custom;

import lk.ijse.Jayabima.dao.SuperDAO;
import lk.ijse.Jayabima.entity.Salary;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SalaryDAO extends SuperDAO {
    public ArrayList<Salary> getAllSalary() throws SQLException;
    boolean saveSalary(Salary entity) throws SQLException;

    boolean updateSalary(Salary entity) throws SQLException;

    void deleteSalary(String id) throws SQLException;
}
