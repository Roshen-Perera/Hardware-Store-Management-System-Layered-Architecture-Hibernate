package lk.ijse.Jayabima.dao.custom.impl;

import lk.ijse.Jayabima.dao.SQLUtil;
import lk.ijse.Jayabima.dao.custom.RegisterDAO;
import lk.ijse.Jayabima.entity.Register;

import java.sql.SQLException;

public class RegisterDAOImpl implements RegisterDAO {

    @Override
    public boolean saveUser(Register entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO user VALUES(?, ?, ?, ?)", entity.getUsername(), entity.getMobile(), entity.getPassword(), entity.getRepeatPassword());
    }
}
