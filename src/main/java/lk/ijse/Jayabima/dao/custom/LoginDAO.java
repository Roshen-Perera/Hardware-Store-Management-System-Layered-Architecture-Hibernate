package lk.ijse.Jayabima.dao.custom;

import lk.ijse.Jayabima.dao.SuperDAO;

import java.sql.SQLException;

public interface LoginDAO extends SuperDAO {
    boolean login(String username, String password) throws SQLException;
}
