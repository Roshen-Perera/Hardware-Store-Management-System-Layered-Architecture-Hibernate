package lk.ijse.Jayabima.dao.custom.impl;

import lk.ijse.Jayabima.dao.SQLUtil;
import lk.ijse.Jayabima.dao.custom.LoginDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAOImpl implements LoginDAO {
    public boolean login(String username, String password) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM user WHERE name = ? AND password = ?", username, password);
        return rst.next();
    }
}
