package lk.ijse.Jayabima.bo.custom.impl;

import lk.ijse.Jayabima.bo.custom.RegisterBO;
import lk.ijse.Jayabima.dao.DAOFactory;
import lk.ijse.Jayabima.dao.custom.RegisterDAO;
import lk.ijse.Jayabima.dto.RegisterDto;
import lk.ijse.Jayabima.entity.Register;

import java.sql.SQLException;

public class RegisterBOImpl implements RegisterBO {

    RegisterDAO registerDAO = (RegisterDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.REGISTER);
    public boolean saveUser(RegisterDto dto) throws SQLException {
        return registerDAO.saveUser(new Register(dto.getUsername(), dto.getMobile(), dto.getPassword(), dto.getRepeatPassword()));
    }
}
