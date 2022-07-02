package dao;

import com.sun.xml.internal.bind.v2.model.core.ID;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.sql.SQLException;

public interface CrudDAO<T,Id> extends SuperDAO {

    boolean add(T t ) throws SQLException, ClassNotFoundException;
    boolean delete(String id) throws SQLException, ClassNotFoundException;
    boolean update(T t) throws SQLException, ClassNotFoundException;
    JsonObjectBuilder search(ID id) throws SQLException, ClassNotFoundException;
    JsonArrayBuilder getAll() throws SQLException;
    Object getCode(String id) throws SQLException;
}

