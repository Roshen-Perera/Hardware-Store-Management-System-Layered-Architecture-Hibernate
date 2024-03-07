package lk.ijse.Jayabima.bo.custom;

import lk.ijse.Jayabima.bo.SuperBO;
import lk.ijse.Jayabima.dto.RegisterDto;

import java.sql.SQLException;

public interface RegisterBO extends SuperBO {
    boolean saveUser(RegisterDto dto) throws SQLException;
}
