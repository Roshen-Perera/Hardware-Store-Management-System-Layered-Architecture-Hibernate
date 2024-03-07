package lk.ijse.Jayabima.dao.custom.impl;

import lk.ijse.Jayabima.dao.SQLUtil;
import lk.ijse.Jayabima.dao.custom.EmployeeDAO;
import lk.ijse.Jayabima.entity.Employee;
import lk.ijse.Jayabima.entity.Salary;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {

    @Override
    public ArrayList<Employee> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * from employee");
        ArrayList<Employee> employeeArrayList = new ArrayList<>();

        while (rst.next()) {
            Employee entity = new Employee(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6));
            employeeArrayList.add(entity);
        }
        return employeeArrayList;
    }

    @Override
    public String generateID() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT emp_id FROM employee ORDER BY emp_id DESC LIMIT 1;");

        if (rst.next()) {
            String id = rst.getString("emp_id");
            int newEmployeeId = Integer.parseInt(id.replace("E", "")) + 1;
            return String.format("E%03d", newEmployeeId);
        } else {
            return "E001";
        }
    }

    @Override
    public boolean save(Employee entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO Employee VALUES(?, ?, ?, ?, ?, ?)", entity.getId(), entity.getName(), entity.getRole(), entity.getAddress(), entity.getSalary(), entity.getMobile());
    }

    @Override
    public boolean update(Employee entity) throws SQLException {
        return SQLUtil.execute("update Employee set emp_name = ?, emp_role = ?, emp_address = ?, emp_salary = ?, emp_mobile = ? where emp_id = ?", entity.getName(), entity.getRole(), entity.getAddress(), entity.getSalary(), entity.getMobile(), entity.getId());
    }

    @Override
    public void delete(String id) throws SQLException, ClassNotFoundException {
        SQLUtil.execute("delete from Employee where emp_id = ?", id);
    }

    @Override
    public boolean exist(String id) throws SQLException {
        ResultSet rst=SQLUtil.execute("SELECT emp_id FROM employee WHERE emp_id=?",id);
        return rst.next();
    }

    @Override
    public Employee search(String id) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM employee WHERE emp_id=?",id);
        rst.next();
        return new Employee(
                id + "",
                rst.getString(2),
                rst.getString(3),
                rst.getString(4),
                rst.getString(5),
                rst.getString(6)
        );
    }
}
