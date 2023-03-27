package system.backend.dao;

import java.util.List;

public interface DAO<T, V> {
    void save(T object);
    void delete(T object);
    T findByID(Class<T> c, Long id);
    void deleteByID(Class<T> c, Long id);
    void update(T object);
    List<T> selectAll(Class<T> c);
    T findBy2Values(Class<T> c, String column1, String column2, V value1, V value2);
    T findBy1Value(Class<T> c, String column1, V value);
    T findBy1ValueExcept(Class<T> c, String column1, V value, long ignoreThisID);
    void updateColumn(Class<T> c, String column, V newValue, V oldValue);
}
