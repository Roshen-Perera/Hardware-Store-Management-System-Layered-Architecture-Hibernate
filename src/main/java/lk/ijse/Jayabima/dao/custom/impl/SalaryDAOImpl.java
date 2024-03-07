package lk.ijse.Jayabima.dao.custom.impl;

import lk.ijse.Jayabima.dao.SQLUtil;
import lk.ijse.Jayabima.dao.custom.SalaryDAO;
import lk.ijse.Jayabima.entity.Salary;
import lk.ijse.Jayabima.entity.Salary;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SalaryDAOImpl implements SalaryDAO {

    public ArrayList<Salary> getAllSalary() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * from salary");
        ArrayList<Salary> salaryArrayList = new ArrayList<>();

        while (rst.next()) {
            Salary entity = new Salary(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3));
            salaryArrayList.add(entity);
        }
        return salaryArrayList;
    }
    @Override
    public boolean saveSalary(Salary entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO salary VALUES(?, ?, ?)", entity.getId(), entity.getSalary(), entity.getStatus());
    }

    @Override
    public boolean updateSalary(Salary entity) throws SQLException {
        return SQLUtil.execute("update salary set salary_amount = ?, salary_status = ? where emp_id = ?", entity.getSalary(), entity.getStatus(), entity.getId());
    }

    @Override
    public void deleteSalary(String id) throws SQLException {
        SQLUtil.execute("delete from salary where emp_id = ?", id);
    }
}
