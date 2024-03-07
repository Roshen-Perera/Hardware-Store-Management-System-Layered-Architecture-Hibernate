package lk.ijse.Jayabima.dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T> extends SuperDAO {
    ArrayList<T> getAll() throws SQLException;

    String generateID() throws SQLException;

    boolean save(T entity) throws SQLException;

    boolean update(T entity) throws SQLException;

    void delete(String id) throws SQLException, ClassNotFoundException;

    boolean exist(String id) throws SQLException;
    T search(String id) throws SQLException;
}
