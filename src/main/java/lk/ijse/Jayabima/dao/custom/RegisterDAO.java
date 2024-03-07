package lk.ijse.Jayabima.dao.custom;

import lk.ijse.Jayabima.dao.SuperDAO;
import lk.ijse.Jayabima.entity.Register;

import java.sql.SQLException;

public interface RegisterDAO extends SuperDAO {
    boolean saveUser(Register entity) throws SQLException;
}
