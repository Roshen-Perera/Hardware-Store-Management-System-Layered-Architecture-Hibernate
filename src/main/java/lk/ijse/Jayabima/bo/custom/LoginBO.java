package lk.ijse.Jayabima.bo.custom;

import lk.ijse.Jayabima.bo.SuperBO;

import java.sql.SQLException;

public interface LoginBO extends SuperBO {
    public boolean login(String username, String password) throws SQLException;
}
